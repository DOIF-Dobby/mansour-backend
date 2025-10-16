package org.mj.mansour.markethistory.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.kstream.Grouped
import org.apache.kafka.streams.kstream.Materialized
import org.apache.kafka.streams.kstream.Produced
import org.apache.kafka.streams.kstream.Suppressed
import org.apache.kafka.streams.kstream.TimeWindows
import org.apache.kafka.streams.processor.api.ProcessorSupplier
import org.mj.mansour.contract.marketdata.StockPriceUpdatedEvent
import org.mj.mansour.markethistory.model.OneMinuteCandle
import org.mj.mansour.markethistory.model.OneMinuteCandleAccumulator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafkaStreams
import org.springframework.kafka.support.serializer.JsonSerde
import java.time.Duration
import java.time.Instant

@Configuration
@EnableKafkaStreams
class MarketDataStreamConfig {

    @Bean
    fun kStreamTopology(streamsBuilder: StreamsBuilder, objectMapper: ObjectMapper): StreamsBuilder {
        // 사용할 Serde(직렬화/역직렬화기)를 명시적으로 정의합니다.
        val stringSerde = Serdes.String()
        val tickPayloadSerde = JsonSerde(StockPriceUpdatedEvent.Payload::class.java)
        val accumulatorSerde = JsonSerde(OneMinuteCandleAccumulator::class.java)
        val finalCandleSerde = JsonSerde(OneMinuteCandle::class.java)


        // 1. 소스(Source): 실시간 체결가 토픽에서 데이터를 읽습니다.
        streamsBuilder.stream(
            StockPriceUpdatedEvent.TOPIC,
            Consumed.with(stringSerde, stringSerde)
        ).process(ProcessorSupplier { TickDataProcessor(objectMapper) })
            // 2. 그룹화(Group): 종목 코드(symbol)를 기준으로 데이터를 묶습니다.
            .groupBy(
                { key, value -> value.symbol },
                Grouped.with(stringSerde, tickPayloadSerde)
            )
            // 3. 윈도우(Window): 1분 단위의 겹치지 않는 시간 창으로 그룹을 나눕니다. 5초의 유예 기간을 설정하여, 윈도우가 닫힌 후에도 5초 동안 추가 데이터가 들어올 수 있도록 합니다.
            .windowedBy(TimeWindows.ofSizeAndGrace(Duration.ofMinutes(1), Duration.ofSeconds(5)))
            // 4. 집계(Aggregate): 각 윈도우 안의 데이터를 OHLCV 캔들로 집계합니다.
            .aggregate(
                // Initializer: 윈도우의 첫 데이터가 들어왔을 때, 초기 캔들 객체를 생성합니다.
                { OneMinuteCandleAccumulator() },
                // Aggregator: 윈도우에 새로운 체결 데이터(tick)가 들어올 때마다 캔들 상태를 업데이트합니다.
                { symbol, tick, candle ->
                    if (candle.volume == 0L) {
                        // 첫 데이터가 들어왔을 때, 캔들의 open, high, low, close를 모두 tick의 close로 설정합니다.
                        candle.symbol = symbol
                        candle.open = tick.close
                        candle.high = tick.close
                        candle.low = tick.close
                        candle.close = tick.close
                        candle.volume = tick.tradeVolume
                    } else {
                        // 이후 데이터가 들어올 때, 캔들의 high, low, close를 업데이트합니다.
                        candle.high = maxOf(candle.high, tick.close)
                        candle.low = minOf(candle.low, tick.close)
                        candle.close = tick.close
                        candle.volume += tick.tradeVolume
                    }

                    candle
                },
                // 집계 중간 결과를 저장할 상태 저장소(State Store)를 설정합니다.
                Materialized.with(stringSerde, accumulatorSerde)
            )
            // 👇 윈도우가 닫힐 때까지 중간 결과 방출을 억제하고, 최종 결과만 내보냅니다.
            .suppress(Suppressed.untilWindowCloses(Suppressed.BufferConfig.unbounded()))
            // 5. 스트림 변환: KTable을 다시 KStream으로 변환하고, Key를 Windowed<String>에서 String으로 복원합니다.
            .toStream()
            .map { windowedKey, accumulator ->
                val finalCandle = OneMinuteCandle(
                    symbol = accumulator.symbol,
                    open = accumulator.open,
                    high = accumulator.high,
                    low = accumulator.low,
                    close = accumulator.close,
                    volume = accumulator.volume,
                    windowStartTime = Instant.ofEpochMilli(windowedKey.window().start()),
                    windowEndTime = Instant.ofEpochMilli(windowedKey.window().end())
                )
                KeyValue(windowedKey.key(), finalCandle)
            }
            .to(
                CANDLE_TOPIC,
                Produced.with(stringSerde, finalCandleSerde)
            )

        return streamsBuilder

    }

    companion object {
        const val CANDLE_TOPIC = "marketdata.candles.1m"
    }
}
