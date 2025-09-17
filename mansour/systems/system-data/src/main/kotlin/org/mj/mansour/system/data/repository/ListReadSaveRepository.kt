package org.mj.mansour.system.data.repository

import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface ListReadSaveRepository<T, ID> : ReadRepository<T, ID>, SaveRepository<T, ID> {

    override fun findAll(): List<T>

    override fun findAllById(ids: Iterable<ID>): List<T>

    override fun <S : T> saveAll(entities: Iterable<S>): List<S>
}
