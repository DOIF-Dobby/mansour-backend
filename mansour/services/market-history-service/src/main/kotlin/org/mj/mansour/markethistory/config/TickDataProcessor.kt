package org.mj.mansour.markethistory.config

import org.apache.kafka.streams.processor.api.Processor
import org.apache.kafka.streams.processor.api.ProcessorContext
import org.apache.kafka.streams.processor.api.Record
import org.mj.mansour.contract.marketdata.StockPriceUpdatedEvent
import org.mj.mansour.system.json.DebeziumMessageParser
import org.mj.mansour.system.json.parsePayload

class TickDataProcessor(
    private val parser: DebeziumMessageParser
) : Processor<String, String, String, StockPriceUpdatedEvent.Payload> {

    private lateinit var context: ProcessorContext<String, StockPriceUpdatedEvent.Payload>

    override fun init(context: ProcessorContext<String, StockPriceUpdatedEvent.Payload>) {
        this.context = context
    }

    override fun process(record: Record<String, String>) {
        // 1. 이중 포장된 JSON 문자열을 가져옵니다.
        val doublyEncodedJson = record.value()

        // 2. DebeziumMessageParser를 사용해 Payload 객체로 파싱합니다.
        val payload = parser.parsePayload<StockPriceUpdatedEvent.Payload>(doublyEncodedJson)

        // 3. 파싱된 객체에서 실제 이벤트 시간을 추출합니다.
        val eventTime = payload.timestamp.toEpochMilli()

        // 4. 새로운 Record를 생성합니다.
        //    - Key: 기존과 동일
        //    - Value: 파싱된 Payload 객체
        //    - Timestamp: 페이로드에서 추출한 실제 이벤트 시간으로 재지정!
        val newRecord = Record(record.key(), payload, eventTime)

        // 5. 수정된 Record를 다음 프로세서(downstream)로 전달합니다.
        context.forward(newRecord)
    }
}
