package org.mj.mansour.strategy.domain

import org.mj.mansour.system.data.jpa.repository.NoDeleteJpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UserStrategyRepository : NoDeleteJpaRepository<UserStrategy, Long> {

    @Query(
        """
        SELECT us
        FROM UserStrategy us
        WHERE us.id = :id
        AND us.userId = :userId
        """
    )
    fun findByIdAndUserId(
        @Param("id") id: Long,
        @Param("userId") userId: Long,
    ): UserStrategy?

    @Query(
        """
        SELECT COUNT(us) > 0
        FROM UserStrategy us
        WHERE us.userId = :userId
        AND us.symbol = :symbol
        AND us.active = true
    """
    )
    fun hasActiveStrategyForSymbol(
        @Param("userId") userId: Long,
        @Param("symbol") symbol: String,
    ): Boolean

    @Query(
        """
        SELECT COUNT(us) > 0
        FROM UserStrategy us
        WHERE us.userId = :userId
        AND us.symbol = :symbol
        AND us.id <> :id
        AND us.active = true
    """
    )
    fun hasActiveStrategyForSymbol(
        @Param("userId") userId: Long,
        @Param("symbol") symbol: String,
        @Param("id") id: Long,
    ): Boolean
}
