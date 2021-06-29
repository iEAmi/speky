package com.github.speky.postgres.compiler.internal

import com.github.speky.core.PropertyRef
import com.github.speky.core.specification.Alias
import com.github.speky.core.specification.Alias.JustClassRef

/**
 * Find for [Alias] related to [PropertyRef].
 *
 * @receiver [Alias]
 *
 * @throws [UnsupportedOperationException] if this alias be [JustClassRef]
 */
@Throws(UnsupportedOperationException::class)
internal fun Alias<*>.find(prop: PropertyRef<*>): Alias.Single<*> =
  flatten().first { it.classRef == prop.declaringClassRef }
