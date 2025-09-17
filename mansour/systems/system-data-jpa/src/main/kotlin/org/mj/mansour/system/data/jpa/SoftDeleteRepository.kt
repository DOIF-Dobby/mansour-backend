package org.mj.mansour.system.data.jpa

import org.springframework.data.jpa.repository.JpaRepository

interface SoftDeleteRepository<T : BaseDeletedEntity, ID> : JpaRepository<T, ID> {

    override fun delete(entity: T) {
        throw UnsupportedOperationException("Hard delete is not allowed. Use soft delete instead.")
    }

    override fun deleteAll() {
        throw UnsupportedOperationException("Hard delete is not allowed. Use soft delete instead.")
    }

    override fun deleteAll(entities: Iterable<T?>) {
        throw UnsupportedOperationException("Hard delete is not allowed. Use soft delete instead.")
    }

    override fun deleteById(id: ID & Any) {
        throw UnsupportedOperationException("Hard delete is not allowed. Use soft delete instead.")
    }

    override fun deleteAllById(ids: Iterable<ID?>) {
        throw UnsupportedOperationException("Hard delete is not allowed. Use soft delete instead.")
    }

    override fun deleteAllInBatch(entities: Iterable<T?>) {
        throw UnsupportedOperationException("Hard delete is not allowed. Use soft delete instead.")
    }

    override fun deleteAllInBatch() {
        throw UnsupportedOperationException("Hard delete is not allowed. Use soft delete instead.")
    }

    override fun deleteAllByIdInBatch(ids: Iterable<ID?>) {
        throw UnsupportedOperationException("Hard delete is not allowed. Use soft delete instead.")
    }
}
