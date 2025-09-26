package org.mj.mansour.asset.api

import org.mj.mansour.asset.service.AssetService
import org.mj.mansour.contract.asset.AssetResponse
import org.mj.mansour.system.web.response.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/internal/api/assets")
class AssetInternalController(
    private val assetService: AssetService
) {

    @GetMapping("/{assetId}")
    fun getAssetById(@PathVariable assetId: Long): ApiResponse<AssetResponse> {
        val assetResponse = assetService.getAssetById(assetId)
        return ApiResponse.ok(data = assetResponse)
    }
}
