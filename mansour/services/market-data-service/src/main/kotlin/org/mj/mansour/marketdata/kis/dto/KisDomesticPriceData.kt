package org.mj.mansour.marketdata.kis.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import org.mj.mansour.system.json.StrictStringToBigDecimalDeserializer
import java.math.BigDecimal

data class KisDomesticPriceData(
    /** 종목 상태 구분 코드 */
    @param:JsonProperty("iscd_stat_cls_code")
    val stockStatusClassCode: String,

    /** 증거금 비율 */
    @param:JsonProperty("marg_rate")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val marginRate: BigDecimal,

    /** 대표 시장 한글 명 */
    @param:JsonProperty("rprs_mrkt_kor_name")
    val representativeMarketKoreanName: String,

    /** 신 고가 저가 구분 코드 */
    @param:JsonProperty("new_hgpr_lwpr_cls_code")
    val newHighLowClassCode: String,

    /** 업종 한글 종목명 */
    @param:JsonProperty("bstp_kor_isnm")
    val businessTypeKoreanIndustryName: String,

    /** 임시 정지 여부 (Y/N) */
    @param:JsonProperty("temp_stop_yn")
    val isTemporaryStop: String,

    /** 시가 범위 연장 여부 (Y/N) */
    @param:JsonProperty("oprc_rang_cont_yn")
    val isOpeningPriceRangeContinuous: String,

    /** 종가 범위 연장 여부 (Y/N) */
    @param:JsonProperty("clpr_rang_cont_yn")
    val isClosingPriceRangeContinuous: String,

    /** 신용 가능 여부 (Y/N) */
    @param:JsonProperty("crdt_able_yn")
    val isCreditAvailable: String,

    /** 보증금 비율 구분 코드 */
    @param:JsonProperty("grmn_rate_cls_code")
    val guaranteeRateClassCode: String,

    /** ELW 발행 여부 (Y/N) */
    @param:JsonProperty("elw_pblc_yn")
    val isElwPublic: String,

    /** 주식 현재가 */
    @param:JsonProperty("stck_prpr")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val stockPrice: BigDecimal,

    /** 전일 대비 */
    @param:JsonProperty("prdy_vrss")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val changeFromPreviousDay: BigDecimal,

    /** 전일 대비 부호 (1:상한, 2:상승, 3:보합, 4:하한, 5:하락) */
    @param:JsonProperty("prdy_vrss_sign")
    val changeSign: String,

    /** 전일 대비율 */
    @param:JsonProperty("prdy_ctrt")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val changeRate: BigDecimal,

    /** 누적 거래 대금 */
    @param:JsonProperty("acml_tr_pbmn")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val accumulatedTradeValue: BigDecimal,

    /** 누적 거래량 */
    @param:JsonProperty("acml_vol")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val accumulatedVolume: BigDecimal,

    /** 전일 대비 거래량 비율 */
    @param:JsonProperty("prdy_vrss_vol_rate")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val volumeChangeRateFromPreviousDay: BigDecimal,

    /** 주식 시가 */
    @param:JsonProperty("stck_oprc")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val openingPrice: BigDecimal,

    /** 주식 최고가 */
    @param:JsonProperty("stck_hgpr")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val highestPrice: BigDecimal,

    /** 주식 최저가 */
    @param:JsonProperty("stck_lwpr")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val lowestPrice: BigDecimal,

    /** 주식 상한가 */
    @param:JsonProperty("stck_mxpr")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val upperLimitPrice: BigDecimal,

    /** 주식 하한가 */
    @param:JsonProperty("stck_llam")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val lowerLimitPrice: BigDecimal,

    /** 주식 기준가 */
    @param:JsonProperty("stck_sdpr")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val standardPrice: BigDecimal,

    /** 가중 평균 주식 가격 */
    @param:JsonProperty("wghn_avrg_stck_prc")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val weightedAverageStockPrice: BigDecimal,

    /** HTS 외국인 소진율 */
    @param:JsonProperty("hts_frgn_ehrt")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val htsForeignExhaustionRate: BigDecimal,

    /** 외국인 순매수 수량 */
    @param:JsonProperty("frgn_ntby_qty")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val foreignNetBuyQuantity: BigDecimal,

    /** 프로그램매매 순매수 수량 */
    @param:JsonProperty("pgtr_ntby_qty")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val programNetBuyQuantity: BigDecimal,

    /** 피벗 2차 저항 가격 */
    @param:JsonProperty("pvt_scnd_dmrs_prc")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val pivotSecondResistancePrice: BigDecimal,

    /** 피벗 1차 저항 가격 */
    @param:JsonProperty("pvt_frst_dmrs_prc")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val pivotFirstResistancePrice: BigDecimal,

    /** 피벗 포인트 값 */
    @param:JsonProperty("pvt_pont_val")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val pivotPointValue: BigDecimal,

    /** 피벗 1차 지지 가격 */
    @param:JsonProperty("pvt_frst_dmsp_prc")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val pivotFirstSupportPrice: BigDecimal,

    /** 피벗 2차 지지 가격 */
    @param:JsonProperty("pvt_scnd_dmsp_prc")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val pivotSecondSupportPrice: BigDecimal,

    /** 저항 값 */
    @param:JsonProperty("dmrs_val")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val resistanceValue: BigDecimal,

    /** 지지 값 */
    @param:JsonProperty("dmsp_val")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val supportValue: BigDecimal,

    /** 자본금 */
    @param:JsonProperty("cpfn")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val capitalization: BigDecimal,

    /** 제한 폭 가격 */
    @param:JsonProperty("rstc_wdth_prc")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val restrictionWidthPrice: BigDecimal,

    /** 주식 액면가 */
    @param:JsonProperty("stck_fcam")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val faceValue: BigDecimal,

    /** 주식 대용가 */
    @param:JsonProperty("stck_sspr")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val substitutePrice: BigDecimal,

    /** 호가단위 */
    @param:JsonProperty("aspr_unit")
    val askPriceUnit: String,

    /** HTS 매매 수량 단위 값 */
    @param:JsonProperty("hts_deal_qty_unit_val")
    val htsDealQuantityUnitValue: String,

    /** 상장 주수 */
    @param:JsonProperty("lstn_stcn")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val listedStockCount: BigDecimal,

    /** HTS 시가총액 */
    @param:JsonProperty("hts_avls")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val htsMarketCap: BigDecimal,

    /** PER */
    @param:JsonProperty("per")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val per: BigDecimal,

    /** PBR */
    @param:JsonProperty("pbr")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val pbr: BigDecimal,

    /** 결산 월 */
    @param:JsonProperty("stac_month")
    val statementMonth: String,

    /** 거래량 회전율 */
    @param:JsonProperty("vol_tnrt")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val volumeTurnoverRate: BigDecimal,

    /** EPS */
    @param:JsonProperty("eps")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val eps: BigDecimal,

    /** BPS */
    @param:JsonProperty("bps")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val bps: BigDecimal,

    /** 250일 최고가 */
    @param:JsonProperty("d250_hgpr")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val d250HighestPrice: BigDecimal,

    /** 250일 최고가 일자 */
    @param:JsonProperty("d250_hgpr_date")
    val d250HighestPriceDate: String,

    /** 250일 최고가 대비 현재가 비율 */
    @param:JsonProperty("d250_hgpr_vrss_prpr_rate")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val d250HighestPriceVsCurrentPriceRate: BigDecimal,

    /** 250일 최저가 */
    @param:JsonProperty("d250_lwpr")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val d250LowestPrice: BigDecimal,

    /** 250일 최저가 일자 */
    @param:JsonProperty("d250_lwpr_date")
    val d250LowestPriceDate: String,

    /** 250일 최저가 대비 현재가 비율 */
    @param:JsonProperty("d250_lwpr_vrss_prpr_rate")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val d250LowestPriceVsCurrentPriceRate: BigDecimal,

    /** 주식 연중 최고가 */
    @param:JsonProperty("stck_dryy_hgpr")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val yearlyHighestPrice: BigDecimal,

    /** 연중 최고가 대비 현재가 비율 */
    @param:JsonProperty("dryy_hgpr_vrss_prpr_rate")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val yearlyHighestPriceVsCurrentPriceRate: BigDecimal,

    /** 연중 최고가 일자 */
    @param:JsonProperty("dryy_hgpr_date")
    val yearlyHighestPriceDate: String,

    /** 주식 연중 최저가 */
    @param:JsonProperty("stck_dryy_lwpr")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val yearlyLowestPrice: BigDecimal,

    /** 연중 최저가 대비 현재가 비율 */
    @param:JsonProperty("dryy_lwpr_vrss_prpr_rate")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val yearlyLowestPriceVsCurrentPriceRate: BigDecimal,

    /** 연중 최저가 일자 */
    @param:JsonProperty("dryy_lwpr_date")
    val yearlyLowestPriceDate: String,

    /** 52주일 최고가 */
    @param:JsonProperty("w52_hgpr")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val w52HighestPrice: BigDecimal,

    /** 52주일 최고가 대비 현재가 대비 */
    @param:JsonProperty("w52_hgpr_vrss_prpr_ctrt")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val w52HighestPriceVsCurrentPriceRate: BigDecimal,

    /** 52주일 최고가 일자 */
    @param:JsonProperty("w52_hgpr_date")
    val w52HighestPriceDate: String,

    /** 52주일 최저가 */
    @param:JsonProperty("w52_lwpr")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val w52LowestPrice: BigDecimal,

    /** 52주일 최저가 대비 현재가 대비 */
    @param:JsonProperty("w52_lwpr_vrss_prpr_ctrt")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val w52LowestPriceVsCurrentPriceRate: BigDecimal,

    /** 52주일 최저가 일자 */
    @param:JsonProperty("w52_lwpr_date")
    val w52LowestPriceDate: String,

    /** 전체 융자 잔고 비율 */
    @param:JsonProperty("whol_loan_rmnd_rate")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val wholeLoanRemainingRate: BigDecimal,

    /** 공매도가능여부 (Y/N) */
    @param:JsonProperty("ssts_yn")
    val isShortSellingAvailable: String,

    /** 주식 단축 종목코드 */
    @param:JsonProperty("stck_shrn_iscd")
    val stockShortenedCode: String,

    /** 액면가 통화명 */
    @param:JsonProperty("fcam_cnnm")
    val faceValueCurrencyName: String,

    /** 자본금 통화명 */
    @param:JsonProperty("cpfn_cnnm")
    val capitalizationCurrencyName: String,

    /** 외국인 보유 수량 */
    @param:JsonProperty("frgn_hldn_qty")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val foreignHoldingQuantity: BigDecimal,

    /** VI적용구분코드 */
    @param:JsonProperty("vi_cls_code")
    val viClassCode: String,

    /** 시간외단일가VI적용구분코드 */
    @param:JsonProperty("ovtm_vi_cls_code")
    val overtimeViClassCode: String,

    /** 최종 공매도 체결 수량 */
    @param:JsonProperty("last_ssts_cntg_qty")
    @param:JsonDeserialize(using = StrictStringToBigDecimalDeserializer::class)
    val lastShortSellingContingentQuantity: BigDecimal,

    /** 투자유의여부 (Y/N) */
    @param:JsonProperty("invt_caful_yn")
    val isInvestmentCaution: String,

    /** 시장경고코드 (00:없음,01:투자주의,02:투자경고,03:투자위험) */
    @param:JsonProperty("mrkt_warn_cls_code")
    val marketWarningCode: String,

    /** 단기과열여부 (Y/N) */
    @param:JsonProperty("short_over_yn")
    val isShortOverheated: String,

    /** 정리매매여부 (Y/N) */
    @param:JsonProperty("sltr_yn")
    val isDelistingTrade: String,

    /** 관리종목여부 (Y/N) */
    @param:JsonProperty("mang_issu_cls_code")
    val isManagementIssue: String
)
