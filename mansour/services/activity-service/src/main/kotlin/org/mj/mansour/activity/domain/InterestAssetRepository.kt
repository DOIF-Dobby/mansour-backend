package org.mj.mansour.activity.domain

import org.mj.mansour.system.data.repository.ReadRepository

interface InterestAssetRepository : ReadRepository<InterestAsset, Long> {

    fun findByGroupIdAndAssetId(groupId: Long, assetId: Long): InterestAsset?
}
