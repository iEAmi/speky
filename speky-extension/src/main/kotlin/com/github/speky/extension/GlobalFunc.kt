package com.github.speky.extension

import com.github.speky.core.specification.Sink
import com.github.speky.core.specification.Source
import com.github.speky.core.specification.Specification
import com.github.speky.core.specification.Value

/**
 * Global version of [Specification.from] for simplicity.
 */
inline fun <reified T> from(alias: String = T::class.simpleName!!.lowercase()): Source.Single<T> =
  Specification.from(alias)

/**
 * Global version of [Specification.insertOn] for simplicity.
 */
inline fun <reified T> insertOn(vararg values: Value<T, *>): Sink.Insert<T> =
  Specification.insertOn(*values)

/**
 * Global version of [Specification.updateOn] for simplicity.
 */
inline fun <reified T> updateOn(vararg values: Value<T, *>): Sink.Update<T> =
  Specification.updateOn(*values)
