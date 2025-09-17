package org.mj.mansour.system.data.repository

import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.Repository

@NoRepositoryBean
interface SaveRepository<T, ID> : Repository<T, ID> {

    fun <S : T> save(entity: S): S

    fun <S : T> saveAll(entities: Iterable<S>): Iterable<S>

}
