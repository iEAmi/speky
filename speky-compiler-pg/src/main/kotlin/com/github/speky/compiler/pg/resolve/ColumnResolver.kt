package com.github.speky.compiler.pg.resolve

import com.github.speky.core.PropertyRef

interface ColumnResolver {
  fun resolveColumnName(prop: PropertyRef<*>): String

  fun PropertyRef<*>.columnName(): String = resolveColumnName(this)
}
