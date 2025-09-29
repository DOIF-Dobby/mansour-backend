package org.mj.mansour.marketdata.kis.service.domestic

import org.mj.mansour.marketdata.kis.dto.KisDomesticPriceData
import org.mj.mansour.marketdata.kis.dto.KisResponse
import org.mj.mansour.marketdata.kis.service.KisAuthService
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient

@Service
class KisDomesticPriceService(
    private val kisRestClient: RestClient,
    private val kisAuthService: KisAuthService,
) {

    fun fetchDomesticPrice(symbol: String): KisDomesticPriceData {
        val token = kisAuthService.getApiToken()

        val responseBody = kisRestClient.get()
            .uri {
                it.path("/uapi/domestic-stock/v1/quotations/inquire-price")
                    .queryParam("fid_cond_mrkt_div_code", "J")
                    .queryParam("fid_input_iscd", symbol)
                    .build()
            }
            .headers {
                it.set("authorization", "Bearer $token")
                it.set("tr_id", "FHKST01010100")
            }
            .retrieve()
            .body(object : ParameterizedTypeReference<KisResponse<KisDomesticPriceData>>() {})

        return responseBody?.output ?: throw RuntimeException("Failed to fetch domestic price for symbol: $symbol")
    }
}
