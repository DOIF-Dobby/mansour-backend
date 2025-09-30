package org.mj.mansour.marketdata.kis.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

/**
 * KIS 실시간 주식 체결가(H0STCNT0) 데이터 DTO
 */
data class KisRealtimePriceData(
    /** 유가증권 단축 종목코드 */
    @param:JsonProperty("MKSC_SHRN_ISCD")
    val symbol: String,

    /** 주식 체결 시간 (HHMMSS) */
    @param:JsonProperty("STCK_CNTG_HOUR")
    val executionTime: String,

    /** 주식 현재가 (체결가격) */
    @param:JsonProperty("STCK_PRPR")
    val currentPrice: BigDecimal,

    /** 전일 대비 부호 (1:상한, 2:상승, 3:보합, 4:하한, 5:하락) */
    @param:JsonProperty("PRDY_VRSS_SIGN")
    val changeSign: String,

    /** 전일 대비 */
    @param:JsonProperty("PRDY_VRSS")
    val changeFromPreviousDay: BigDecimal,

    /** 전일 대비율 */
    @param:JsonProperty("PRDY_CTRT")
    val changeRate: BigDecimal,

    /** 가중 평균 주식 가격 */
    @param:JsonProperty("WGHN_AVRG_STCK_PRC")
    val weightedAveragePrice: BigDecimal,

    /** 주식 시가 */
    @param:JsonProperty("STCK_OPRC")
    val openingPrice: BigDecimal,

    /** 주식 최고가 */
    @param:JsonProperty("STCK_HGPR")
    val highestPrice: BigDecimal,

    /** 주식 최저가 */
    @param:JsonProperty("STCK_LWPR")
    val lowestPrice: BigDecimal,

    /** 매도호가1 */
    @param:JsonProperty("ASKP1")
    val askPrice1: BigDecimal,

    /** 매수호가1 */
    @param:JsonProperty("BIDP1")
    val bidPrice1: BigDecimal,

    /** 체결 거래량 */
    @param:JsonProperty("CNTG_VOL")
    val tradeVolume: Long,

    /** 누적 거래량 */
    @param:JsonProperty("ACML_VOL")
    val accumulatedVolume: BigDecimal,

    /** 누적 거래 대금 */
    @param:JsonProperty("ACML_TR_PBMN")
    val accumulatedTradeValue: BigDecimal,

    /** 매도 체결 건수 */
    @param:JsonProperty("SELN_CNTG_CSNU")
    val sellExecutionCount: BigDecimal,

    /** 매수 체결 건수 */
    @param:JsonProperty("SHNU_CNTG_CSNU")
    val buyExecutionCount: BigDecimal,

    /** 순매수 체결 건수 */
    @param:JsonProperty("NTBY_CNTG_CSNU")
    val netBuyExecutionCount: BigDecimal,

    /** 체결강도 */
    @param:JsonProperty("CTTR")
    val tradeStrength: BigDecimal,

    /** 총 매도 수량 */
    @param:JsonProperty("SELN_CNTG_SMTN")
    val totalSellVolume: BigDecimal,

    /** 총 매수 수량 */
    @param:JsonProperty("SHNU_CNTG_SMTN")
    val totalBuyVolume: BigDecimal,

    /** 체결구분 (1:매수(+), 3:장전, 5:매도(-)) */
    @param:JsonProperty("CCLD_DVSN")
    val executionType: String,

    /** 매수비율 */
    @param:JsonProperty("SHNU_RATE")
    val buyRatio: BigDecimal,

    /** 전일 거래량 대비 등락율 */
    @param:JsonProperty("PRDY_VOL_VRSS_ACML_VOL_RATE")
    val volumeChangeRateFromPreviousDay: BigDecimal,

    /** 시가 시간 */
    @param:JsonProperty("OPRC_HOUR")
    val openingTime: String,

    /** 시가대비구분 */
    @param:JsonProperty("OPRC_VRSS_PRPR_SIGN")
    val signFromOpen: String,

    /** 시가대비 */
    @param:JsonProperty("OPRC_VRSS_PRPR")
    val changeFromOpen: BigDecimal,

    /** 최고가 시간 */
    @param:JsonProperty("HGPR_HOUR")
    val highestTime: String,

    /** 고가대비구분 */
    @param:JsonProperty("HGPR_VRSS_PRPR_SIGN")
    val signFromHigh: String,

    /** 고가대비 */
    @param:JsonProperty("HGPR_VRSS_PRPR")
    val changeFromHigh: BigDecimal,

    /** 최저가 시간 */
    @param:JsonProperty("LWPR_HOUR")
    val lowestTime: String,

    /** 저가대비구분 */
    @param:JsonProperty("LWPR_VRSS_PRPR_SIGN")
    val signFromLow: String,

    /** 저가대비 */
    @param:JsonProperty("LWPR_VRSS_PRPR")
    val changeFromLow: BigDecimal,

    /** 영업 일자 */
    @param:JsonProperty("BSOP_DATE")
    val businessDate: String,

    /** 신 장운영 구분 코드 */
    @param:JsonProperty("NEW_MKOP_CLS_CODE")
    val newMarketOperationCode: String,

    /** 거래정지 여부 (Y/N) */
    @param:JsonProperty("TRHT_YN")
    val tradeHaltYn: String,

    /** 매도호가 잔량1 */
    @param:JsonProperty("ASKP_RSQN1")
    val askRemainingVolume1: BigDecimal,

    /** 매수호가 잔량1 */
    @param:JsonProperty("BIDP_RSQN1")
    val bidRemainingVolume1: BigDecimal,

    /** 총 매도호가 잔량 */
    @param:JsonProperty("TOTAL_ASKP_RSQN")
    val totalAskRemainingVolume: BigDecimal,

    /** 총 매수호가 잔량 */
    @param:JsonProperty("TOTAL_BIDP_RSQN")
    val totalBidRemainingVolume: BigDecimal,

    /** 거래량 회전율 */
    @param:JsonProperty("VOL_TNRT")
    val volumeTurnoverRate: BigDecimal,

    /** 전일 동시간 누적 거래량 */
    @param:JsonProperty("PRDY_SMNS_HOUR_ACML_VOL")
    val previousDaySameTimeVolume: BigDecimal,

    /** 전일 동시간 누적 거래량 비율 */
    @param:JsonProperty("PRDY_SMNS_HOUR_ACML_VOL_RATE")
    val previousDaySameTimeVolumeRate: BigDecimal,

    /** 시간 구분 코드 */
    @param:JsonProperty("HOUR_CLS_CODE")
    val hourClassCode: String,

    /** 임의종료구분코드 */
    @param:JsonProperty("MRKT_TRTM_CLS_CODE")
    val marketTerminationCode: String,

    /** 정적VI발동기준가 */
    @param:JsonProperty("VI_STND_PRC")
    val staticViStandardPrice: BigDecimal
)
