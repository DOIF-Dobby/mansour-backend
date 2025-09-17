package org.mj.mansour.system.core.message

import org.springframework.context.support.MessageSourceAccessor
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.core.io.PathResource
import org.springframework.core.io.support.PathMatchingResourcePatternResolver

/**
 * 메세지 소스
 */
class BaseResourceMessageSource : ResourceBundleMessageSource() {
    init {
        val messageBundle = getMessageBundle()
        setBasenames(*messageBundle)
        defaultEncoding = "UTF-8"
    }

    companion object {
        fun getAccessor(): MessageSourceAccessor {
            return MessageSourceAccessor(BaseResourceMessageSource())
        }

        /**
         * 클래스패스 하위 messages 디렉토리에 있는 모든 properties 파일을 가져와서 basename으로 변환 후 반환
         */
        fun getMessageBundle(): Array<String> {
            val resolver = PathMatchingResourcePatternResolver()
            val resources = resolver.getResources("classpath*:messages/*.properties")

            return resources.map { resource ->

                val resourcePath = resource.uri.toString()
                val messageResource = if (resourcePath.startsWith("jar:")) {
                    PathResource(resourcePath.drop(4))
                } else {
                    resource
                }

                val nameWithoutExtension = messageResource.file.nameWithoutExtension
                val basename = nameWithoutExtension.take(nameWithoutExtension.lastIndexOf("_"))
                "messages/$basename"
            }.toSet().toTypedArray()
        }
    }
}

fun getBundleMessage(code: String, args: Array<Any>? = null, defaultMessage: String = ""): String {
    return BaseResourceMessageSource
        .getAccessor()
        .getMessage(code, args, defaultMessage)
}
