package org.mj.mansour.strategy.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import org.mj.mansour.system.data.jpa.BaseDeletedEntity

@Entity
@Table(
    name = "strategy_parameter",
    uniqueConstraints = [
        UniqueConstraint(
            name = "uk_strategy_parameter_name",
            columnNames = ["trading_strategy_id", "parameter_name"]
        )
    ]
)
class StrategyParameter(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trading_strategy_id", nullable = false)
    val strategy: TradingStrategy,

    @Column(nullable = false, name = "parameter_name")
    val parameterName: String,

    @Column(nullable = false)
    val displayName: String,

    @Column(nullable = false)
    val description: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val parameterType: ParameterType,

    @Column(nullable = false)
    val required: Boolean = false,

    @Column
    val minValue: String? = null,

    @Column
    val maxValue: String? = null,
) : BaseDeletedEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "strategy_parameter_id")
    val id: Long = 0L
}
