package org.mj.mansour.activity.service

import org.mj.mansour.activity.client.AssetServiceClient
import org.mj.mansour.activity.domain.InterestAssetGroup
import org.mj.mansour.activity.domain.InterestAssetGroupRepository
import org.mj.mansour.activity.dto.AddInterestAssetRequest
import org.mj.mansour.activity.dto.CreateInterestAssetGroupRequest
import org.mj.mansour.activity.exception.InterestGroupNotFoundException
import org.mj.mansour.activity.exception.InvalidRequestAssetDataException
import org.mj.mansour.system.feign.FeignClientExecutor
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class InterestAssetService(
    private val interestAssetGroupRepository: InterestAssetGroupRepository,
    private val assetServiceClient: AssetServiceClient,
    private val feignClientExecutor: FeignClientExecutor
) {

    @Transactional
    fun createInterestAssetGroup(userId: Long, request: CreateInterestAssetGroupRequest) {
        val interestAssetGroup = InterestAssetGroup(
            userId = userId,
            name = request.name
        )

        interestAssetGroupRepository.save(interestAssetGroup)
    }

    @Transactional
    fun addInterestAsset(request: AddInterestAssetRequest) {
        val assetResponse = feignClientExecutor.run { assetServiceClient.getAssetById(request.assetId) }.data
            ?: throw InvalidRequestAssetDataException()


        val interestAssetGroup = interestAssetGroupRepository.findByIdOrNull(request.groupId)
            ?: throw InterestGroupNotFoundException()

        interestAssetGroup.addInterestAsset(request.assetId)

        // TODO: 트랜잭션 아웃박스 패턴 구현, Debezium 적용
    }
}
