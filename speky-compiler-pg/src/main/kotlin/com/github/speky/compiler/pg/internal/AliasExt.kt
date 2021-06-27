package com.github.speky.compiler.pg.internal

import com.github.speky.core.PropertyRef
import com.github.speky.core.specification.Alias

internal fun Alias<*>.find(prop: PropertyRef<*>): Alias.Single<*> =
  flatten().single { it.classRef == prop.declaringClassRef }
