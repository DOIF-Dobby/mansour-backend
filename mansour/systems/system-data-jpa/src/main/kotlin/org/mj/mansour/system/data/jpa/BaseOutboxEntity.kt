package org.mj.mansour.system.data.jpa

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@MappedSuperclass
abstract class BaseOutboxEntity(
    @Column(name = "aggregatetype", nullable = false)
    val aggregateType: String,

    @Column(name = "aggregateid", nullable = false)
    val aggregateId: String,

    @Column(name = "eventtype", nullable = false)
    val eventType: String,

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "JSONB", nullable = false)
    val payload: String,
) : BaseEntity()
