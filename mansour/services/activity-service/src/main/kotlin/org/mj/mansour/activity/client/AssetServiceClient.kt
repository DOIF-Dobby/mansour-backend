package org.mj.mansour.activity.client

import org.mj.mansour.contract.asset.AssetResponse
import org.mj.mansour.system.web.response.ApiResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "asset-service")
interface AssetServiceClient {

    @GetMapping("/internal/api/assets/{assetId}")
    fun getAssetById(@PathVariable assetId: Long): ApiResponse<AssetResponse>
}
