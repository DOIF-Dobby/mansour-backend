package org.mj.mansour.asset.controller

import org.mj.mansour.asset.dto.RegisterStockRequest
import org.mj.mansour.asset.service.AssetService
import org.mj.mansour.contract.asset.AssetResponse
import org.mj.mansour.system.web.response.ApiResponse
import org.mj.mansour.system.web.response.UnitApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AssetController(
    private val assetService: AssetService
) {

    @PostMapping("/stocks")
    fun registerStock(@RequestBody request: RegisterStockRequest): UnitApiResponse {
        assetService.registerStock(request)
        return ApiResponse.ok()
    }

    @GetMapping("/{symbol}")
    fun getAssetBySymbol(@PathVariable symbol: String): ApiResponse<AssetResponse> {
        val stockBySymbol = assetService.getAssetBySymbol(symbol)
        return ApiResponse.ok(data = stockBySymbol)
    }
}
