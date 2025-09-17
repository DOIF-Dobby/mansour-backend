package org.mj.mansour.system.data.jpa

import jakarta.persistence.MappedSuperclass
import java.time.LocalDateTime

@MappedSuperclass
abstract class BaseDeletedEntity : BaseEntity() {

    var deleted: Boolean = false
        protected set

    var deletedAt: LocalDateTime? = null
        protected set

    fun delete() {
        this.deleted = true
        this.deletedAt = LocalDateTime.now()
    }
}
