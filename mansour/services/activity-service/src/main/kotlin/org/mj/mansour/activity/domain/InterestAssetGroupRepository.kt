package org.mj.mansour.activity.domain

import org.springframework.data.jpa.repository.JpaRepository

interface InterestAssetGroupRepository : JpaRepository<InterestAssetGroup, Long> {
    
    fun findByIdAndUserId(id: Long, userId: Long): InterestAssetGroup?
}
