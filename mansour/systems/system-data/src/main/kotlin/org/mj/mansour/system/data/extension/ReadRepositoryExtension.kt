package org.mj.mansour.system.data.extension

import org.mj.mansour.system.data.repository.ReadRepository

fun <T, ID : Any> ReadRepository<T, ID>.findByIdOrNull(id: ID): T? = findById(id).orElse(null)
