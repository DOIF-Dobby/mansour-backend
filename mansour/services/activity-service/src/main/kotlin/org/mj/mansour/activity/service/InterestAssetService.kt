package org.mj.mansour.activity.service

import org.mj.mansour.activity.client.AssetServiceClient
import org.mj.mansour.activity.domain.InterestAssetGroup
import org.mj.mansour.activity.domain.InterestAssetGroupRepository
import org.mj.mansour.activity.domain.InterestAssetRepository
import org.mj.mansour.activity.dto.AddInterestAssetRequest
import org.mj.mansour.activity.dto.CreateInterestAssetGroupRequest
import org.mj.mansour.activity.exception.InterestAssetNotFoundException
import org.mj.mansour.activity.exception.InterestGroupNotFoundException
import org.mj.mansour.activity.exception.InvalidRequestAssetDataException
import org.mj.mansour.contract.activity.InterestAssetAddedEvent
import org.mj.mansour.system.feign.FeignClientExecutor
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class InterestAssetService(
    private val interestAssetGroupRepository: InterestAssetGroupRepository,
    private val interestAssetRepository: InterestAssetRepository,
    private val assetServiceClient: AssetServiceClient,
    private val feignClientExecutor: FeignClientExecutor,
    private val outboxService: OutboxService
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
        // 자산 정보를 외부 서비스에서 조회
        val assetResponse = feignClientExecutor.run { assetServiceClient.getAssetById(request.assetId) }.data
            ?: throw InvalidRequestAssetDataException()

        // 관심 자산 그룹이 존재하는지 확인
        val interestAssetGroup = interestAssetGroupRepository.findByIdOrNull(request.groupId)
            ?: throw InterestGroupNotFoundException()

        // 관심 자산 그룹에 자산을 추가
        interestAssetGroup.addInterestAsset(request.assetId)
        val addedInterestAsset = interestAssetRepository.findByGroupIdAndAssetId(
            groupId = interestAssetGroup.id,
            assetId = request.assetId
        ) ?: throw InterestAssetNotFoundException()

        // outbox 레코드 생성
        outboxService.saveOutboxRecord(
            aggregateId = addedInterestAsset.id.toString(),
            aggregateType = InterestAssetAddedEvent.AGGREGATE_TYPE,
            eventType = InterestAssetAddedEvent.EVENT_TYPE,
            payload = InterestAssetAddedEvent.Payload(
                interestAssetId = addedInterestAsset.id,
                assetId = addedInterestAsset.assetId,
                assetSymbol = assetResponse.symbol,
            )
        )
    }
}
