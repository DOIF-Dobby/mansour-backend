package org.mj.mansour.system.web.response

interface Content<T> {

    fun getContent(): List<T>

    companion object {
        fun <T> of(content: List<T>): Content<T> = object : Content<T> {
            override fun getContent(): List<T> {
                return content
            }
        }

        fun empty(): Content<Unit> = of(emptyList())
    }

}

