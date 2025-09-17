package org.mj.mansour.system.data.repository

import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.Repository

@NoRepositoryBean
interface ReadRepository<T, ID> : Repository<T, ID> {

    fun findById(id: ID): T?

    fun findAll(): Iterable<T>

    fun existsById(id: ID): Boolean

    fun count(): Long

    fun findAllById(ids: Iterable<ID>): Iterable<T>
}
