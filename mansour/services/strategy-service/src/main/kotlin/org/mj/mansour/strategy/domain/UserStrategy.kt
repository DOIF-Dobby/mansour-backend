package org.mj.mansour.strategy.domain

import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import org.mj.mansour.system.data.jpa.BaseDeletedEntity
import org.mj.mansour.system.data.jpa.converter.JsonConverter
import java.math.BigDecimal

@Entity
class UserStrategy(

    @Column(nullable = false, name = "user_id")
    val userId: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trading_strategy_id", nullable = false)
    val strategy: TradingStrategy,

    @Column(nullable = false, name = "symbol")
    val symbol: String,

    @JdbcTypeCode(SqlTypes.JSON)
    @Convert(converter = JsonConverter::class)
    @Column(columnDefinition = "JSONB", nullable = false)
    val parameters: Map<String, String>,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val positionSizingType: PositionSizingType,

    @Column(nullable = false)
    val positionSizingValue: BigDecimal,
) : BaseDeletedEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_strategy_id")
    val id: Long = 0L

    var active: Boolean = false

    fun activate() {
        active = true
    }

    fun deactivate() {
        active = false
    }
}
