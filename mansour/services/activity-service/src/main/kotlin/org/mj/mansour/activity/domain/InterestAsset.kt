package org.mj.mansour.activity.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.mj.mansour.system.data.jpa.BaseEntity

@Entity
class InterestAsset internal constructor(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interest_asset_group_id")
    val group: InterestAssetGroup,

    @Column(nullable = false)
    val assetId: Long,
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interest_asset_id")
    val id: Long = 0L
}
