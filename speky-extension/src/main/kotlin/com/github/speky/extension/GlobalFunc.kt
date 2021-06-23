package com.github.speky.extension

import com.github.speky.core.specification.Source
import com.github.speky.core.specification.Specification

/**
 * Global version of [Specification.from] for simplicity.
 */
@JvmOverloads
inline fun <reified T> from(alias: String = T::class.simpleName!!.lowercase()): Source.Single<T> =
  Specification.from(alias)
