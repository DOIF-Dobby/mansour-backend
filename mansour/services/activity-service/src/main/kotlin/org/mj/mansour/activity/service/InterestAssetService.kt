package org.mj.mansour.activity.service

import org.mj.mansour.activity.client.AssetServiceClient
import org.mj.mansour.activity.domain.InterestAssetGroup
import org.mj.mansour.activity.domain.InterestAssetGroupRepository
import org.mj.mansour.activity.domain.InterestAssetRepository
import org.mj.mansour.activity.dto.AddInterestAssetRequest
import org.mj.mansour.activity.dto.CreateInterestAssetGroupRequest
import org.mj.mansour.activity.dto.RemoveInterestAssetRequest
import org.mj.mansour.activity.exception.InterestAssetNotFoundException
import org.mj.mansour.activity.exception.InterestGroupNotFoundException
import org.mj.mansour.activity.exception.InvalidRequestAssetDataException
import org.mj.mansour.contract.activity.InterestAssetAddedEvent
import org.mj.mansour.contract.activity.InterestAssetRemovedEvent
import org.mj.mansour.contract.asset.StockResponse
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

        // 관심 자산 그룹 조회
        val interestAssetGroup = interestAssetGroupRepository.findByIdOrNull(request.groupId)
            ?: throw InterestGroupNotFoundException()

        // 관심 자산 그룹에 자산을 추가
        interestAssetGroup.addInterestAsset(request.assetId)
        val addedInterestAsset = interestAssetRepository.findByGroupIdAndAssetId(
            groupId = interestAssetGroup.id,
            assetId = request.assetId
        ) ?: throw InterestAssetNotFoundException()

        // outbox 레코드 생성
        when (assetResponse) {
            is StockResponse -> {
                outboxService.saveOutboxRecord(
                    aggregateId = addedInterestAsset.id.toString(),
                    aggregateType = InterestAssetAddedEvent.AGGREGATE_TYPE,
                    eventType = InterestAssetAddedEvent.EVENT_TYPE,
                    payload = InterestAssetAddedEvent.Payload(
                        groupId = interestAssetGroup.id,
                        assetId = addedInterestAsset.assetId,
                        assetSymbol = assetResponse.symbol,
                        market = assetResponse.market,
                        userId = interestAssetGroup.userId
                    )
                )
            }
        }
    }

    @Transactional
    fun removeInterestAsset(request: RemoveInterestAssetRequest) {
        // 자산 정보를 외부 서비스에서 조회
        val assetResponse = feignClientExecutor.run { assetServiceClient.getAssetById(request.assetId) }.data
            ?: throw InvalidRequestAssetDataException()

        // 관심 자산 그룹 조회
        val interestAssetGroup = interestAssetGroupRepository.findByIdOrNull(request.groupId)
            ?: throw InterestGroupNotFoundException()

        // 삭제할 관심 자산(InterestAsset)이 조회
        val interestAssetToRemove = interestAssetRepository.findByGroupIdAndAssetId(
            groupId = interestAssetGroup.id,
            assetId = request.assetId
        ) ?: throw InterestAssetNotFoundException()

        // 관심 자산 그룹에 자산을 제거
        interestAssetGroup.removeInterestAsset(interestAssetToRemove)

        // outbox 레코드 생성
        when (assetResponse) {
            is StockResponse -> {
                outboxService.saveOutboxRecord(
                    aggregateId = interestAssetToRemove.id.toString(),
                    aggregateType = InterestAssetRemovedEvent.AGGREGATE_TYPE,
                    eventType = InterestAssetRemovedEvent.EVENT_TYPE,
                    payload = InterestAssetRemovedEvent.Payload(
                        groupId = interestAssetGroup.id,
                        assetId = request.assetId,
                        assetSymbol = assetResponse.symbol,
                        market = assetResponse.market,
                        userId = interestAssetGroup.userId
                    )
                )
            }
        }
    }
}
