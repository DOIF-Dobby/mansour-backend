package org.mj.mansour.activity.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import org.mj.mansour.system.data.jpa.BaseEntity

@Entity
class InterestAssetGroup(
    @Column(nullable = false)
    val userId: Long,

    @Column(nullable = false)
    var name: String,
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interest_asset_group_id")
    val id: Long = 0L

    @OneToMany(mappedBy = "group", cascade = [CascadeType.ALL], orphanRemoval = true)
    private val _assets: MutableList<InterestAsset> = mutableListOf()

    val assets: List<InterestAsset>
        get() = _assets.toList()

    /**
     * 관심 자산을 그룹에 추가합니다.
     */
    fun addInterestAsset(assetId: Long) {
        val asset = InterestAsset(
            group = this,
            assetId = assetId
        )
        _assets.add(asset)
    }
}
