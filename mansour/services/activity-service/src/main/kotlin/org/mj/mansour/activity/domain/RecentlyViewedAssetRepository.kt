package org.mj.mansour.activity.domain

import org.springframework.data.jpa.repository.JpaRepository

interface RecentlyViewedAssetRepository : JpaRepository<RecentlyViewedAsset, Long> {

}
