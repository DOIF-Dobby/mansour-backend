package org.mj.mansour.activity.api

import org.mj.mansour.activity.dto.AddInterestAssetRequest
import org.mj.mansour.activity.dto.CreateInterestAssetGroupRequest
import org.mj.mansour.activity.dto.RemoveInterestAssetRequest
import org.mj.mansour.activity.service.InterestAssetService
import org.mj.mansour.system.web.response.ApiResponse
import org.mj.mansour.system.web.response.UnitApiResponse
import org.mj.mansour.system.webmvc.resolver.UserId
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ActivityController(
    private val assetService: InterestAssetService,
) {

    @PostMapping("/interest/groups")
    fun createInterestAssetGroup(@UserId userId: Long, @RequestBody request: CreateInterestAssetGroupRequest): UnitApiResponse {
        assetService.createInterestAssetGroup(userId = userId, request = request)
        return ApiResponse.ok()
    }

    @PostMapping("/interest/assets")
    fun addInterestAsset(@UserId userId: Long, @RequestBody request: AddInterestAssetRequest): UnitApiResponse {
        assetService.addInterestAsset(
            groupId = request.groupId,
            assetId = request.assetId,
            userId = userId
        )
        return ApiResponse.ok()
    }

    @DeleteMapping("/interest/assets/{assetId}")
    fun removeInterestAsset(
        @UserId userId: Long,
        @PathVariable assetId: Long,
        @RequestBody request: RemoveInterestAssetRequest
    ): UnitApiResponse {
        assetService.removeInterestAsset(
            groupId = request.groupId,
            assetId = assetId,
            userId = userId
        )

        return ApiResponse.ok()
    }
}
