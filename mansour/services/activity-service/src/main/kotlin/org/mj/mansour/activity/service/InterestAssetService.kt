package org.mj.mansour.activity.service

import org.mj.mansour.activity.domain.InterestAssetGroup
import org.mj.mansour.activity.domain.InterestAssetGroupRepository
import org.mj.mansour.activity.domain.InterestAssetRepository
import org.mj.mansour.activity.dto.CreateInterestAssetGroupRequest
import org.mj.mansour.activity.exception.InterestAssetNotFoundException
import org.mj.mansour.activity.exception.InterestGroupNotFoundException
import org.mj.mansour.contract.activity.InterestAssetAddedEvent
import org.mj.mansour.contract.activity.InterestAssetRemovedEvent
import org.mj.mansour.system.core.logging.log
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class InterestAssetService(
    private val interestAssetGroupRepository: InterestAssetGroupRepository,
    private val interestAssetRepository: InterestAssetRepository,
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
    fun addInterestAsset(groupId: Long, assetId: Long, userId: Long) {
        // 관심 자산 그룹 조회
        val interestAssetGroup = interestAssetGroupRepository.findByIdAndUserId(id = groupId, userId = userId)
            ?: throw InterestGroupNotFoundException()

        // 관심 자산 그룹에 자산을 추가
        interestAssetGroup.addInterestAsset(assetId)
        val addedInterestAsset = interestAssetRepository.findByGroupIdAndAssetId(
            groupId = interestAssetGroup.id,
            assetId = assetId
        ) ?: throw InterestAssetNotFoundException()

        // outbox 레코드 생성
        outboxService.saveOutboxRecord(
            aggregateId = addedInterestAsset.id.toString(),
            aggregateType = InterestAssetAddedEvent.AGGREGATE_TYPE,
            eventType = InterestAssetAddedEvent.EVENT_TYPE,
            payload = InterestAssetAddedEvent.Payload(
                groupId = interestAssetGroup.id,
                assetId = addedInterestAsset.assetId,
                userId = interestAssetGroup.userId,
                interestAssetId = addedInterestAsset.id
            )
        )
    }

    @Transactional
    fun removeInterestAsset(groupId: Long, assetId: Long, userId: Long) {
        // 관심 자산 그룹 조회
        val interestAssetGroup = interestAssetGroupRepository.findByIdAndUserId(id = groupId, userId = userId)
            ?: throw InterestGroupNotFoundException()

        // 삭제할 관심 자산(InterestAsset)이 조회
        val interestAssetToRemove = interestAssetRepository.findByGroupIdAndAssetId(
            groupId = interestAssetGroup.id,
            assetId = assetId
        ) ?: throw InterestAssetNotFoundException()

        // 관심 자산 그룹에 자산을 제거
        interestAssetGroup.removeInterestAsset(interestAssetToRemove)

        // outbox 레코드 생성
        outboxService.saveOutboxRecord(
            aggregateId = interestAssetToRemove.id.toString(),
            aggregateType = InterestAssetRemovedEvent.AGGREGATE_TYPE,
            eventType = InterestAssetRemovedEvent.EVENT_TYPE,
            payload = InterestAssetRemovedEvent.Payload(
                groupId = interestAssetGroup.id,
                assetId = assetId,
                userId = interestAssetGroup.userId
            )
        )
    }

    /**
     * 관심 자산 추가 작업을 보상합니다.
     */
    @Transactional
    fun compensateAddInterestAsset(groupId: Long, assetId: Long) {
        // 관심 자산 그룹 조회
        val interestAssetGroup = interestAssetGroupRepository.findByIdOrNull(groupId)
            ?: run {
                log.warn { "Compensation failed: InterestGroup with id $groupId not found." }
                return
            }

        // 삭제할 관심 자산 조회
        val interestAssetToCompensate = interestAssetRepository.findByGroupIdAndAssetId(
            groupId = interestAssetGroup.id,
            assetId = assetId
        ) ?: run {
            log.warn { "Compensation failed: InterestAsset for assetId $assetId in groupId $groupId not found. Already compensated." }
            return
        }

        // 관심 자산 제거
        interestAssetGroup.removeInterestAsset(interestAssetToCompensate)
        log.info { "Compensation successful for interestAssetId ${interestAssetToCompensate.id}" }
    }
}
