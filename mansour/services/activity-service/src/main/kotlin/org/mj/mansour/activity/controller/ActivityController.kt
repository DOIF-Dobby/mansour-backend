package org.mj.mansour.activity.controller

import org.mj.mansour.activity.dto.AddInterestAssetRequest
import org.mj.mansour.activity.dto.CreateInterestAssetGroupRequest
import org.mj.mansour.activity.service.InterestAssetService
import org.mj.mansour.system.web.response.ApiResponse
import org.mj.mansour.system.web.response.UnitApiResponse
import org.mj.mansour.system.webmvc.util.ApiHeaderUtils
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ActivityController(
    private val assetService: InterestAssetService,
) {

    @PostMapping("/interest/groups")
    fun createInterestAssetGroup(@RequestBody request: CreateInterestAssetGroupRequest): UnitApiResponse {
        val currentUserId = ApiHeaderUtils.getCurrentUserId()
        assetService.createInterestAssetGroup(userId = currentUserId, request = request)
        return ApiResponse.ok()
    }

    @PostMapping("/interest/assets")
    fun addInterestAsset(@RequestBody request: AddInterestAssetRequest): UnitApiResponse {
        assetService.addInterestAsset(request)
        return ApiResponse.ok()
    }
}
