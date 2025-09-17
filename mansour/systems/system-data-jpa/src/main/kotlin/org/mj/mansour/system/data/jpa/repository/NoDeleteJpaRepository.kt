package org.mj.mansour.system.data.jpa.repository

import org.mj.mansour.system.data.repository.ListReadSaveRepository
import org.springframework.data.domain.Example
import org.springframework.data.domain.Sort
import org.springframework.data.repository.ListPagingAndSortingRepository
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.query.QueryByExampleExecutor

@NoRepositoryBean
interface NoDeleteJpaRepository<T, ID>
    : ListReadSaveRepository<T, ID>, ListPagingAndSortingRepository<T, ID>, QueryByExampleExecutor<T> {

    fun flush()

    fun <S : T> saveAndFlush(entity: S): S

    fun <S : T> saveAllAndFlush(entities: Iterable<S>): List<S>

    override fun <S : T> findAll(example: Example<S>): List<S>

    override fun <S : T> findAll(example: Example<S>, sort: Sort): List<S>
}
