package org.mj.mansour.strategy.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import org.mj.mansour.system.data.jpa.BaseDeletedEntity
import org.springframework.data.domain.Persistable

@Entity
class TradingStrategy(
    id: String,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val description: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val type: TradingStrategyType,
) : BaseDeletedEntity(), Persistable<String> {

    @Id
    @Column(name = "trading_strategy_id")
    private val _id: String = id

    override fun getId() = _id

    override fun isNew(): Boolean {
        return createdAt == null
    }
}
