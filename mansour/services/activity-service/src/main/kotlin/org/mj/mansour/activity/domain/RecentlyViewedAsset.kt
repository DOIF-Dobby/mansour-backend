package org.mj.mansour.activity.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.mj.mansour.system.data.jpa.BaseEntity
import java.time.LocalDateTime

@Entity
class RecentlyViewedAsset(
    @Column(nullable = false)
    val userId: Long,

    @Column(nullable = false)
    val assetId: Long,

    @Column(nullable = false)
    var viewedAt: LocalDateTime
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recently_viewed_asset_id")
    val id: Long = 0L

}
