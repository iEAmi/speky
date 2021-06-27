package com.github.speky.compiler.pg.resolve

import com.github.speky.core.PropertyRef

/**
 * Converts a [PropertyRef] to corresponding column.
 */
interface ColumnResolver {

  /**
   * Converts [prop] to corresponding column name.
   */
  fun resolveColumnName(prop: PropertyRef<*>): String

  /**
   * Extension-function for [PropertyRef].
   *
   * @receiver [PropertyRef]
   *
   * @see [resolveColumnName]
   */
  fun PropertyRef<*>.columnName(): String = resolveColumnName(this)
}
