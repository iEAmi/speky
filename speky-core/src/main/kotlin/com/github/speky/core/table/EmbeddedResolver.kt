package com.github.speky.core.table

import com.github.speky.core.ClassRef

interface EmbeddedResolver {

  fun isEmbedded(clsRef: ClassRef<*>): Boolean

  fun <T> isEmbedded(instance: T): Boolean =
    with(instance!!::class) { isEmbedded(ClassRef<T>(simpleName!!, qualifiedName!!)) }
}
