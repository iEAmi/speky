package com.github.speky.core.table

import com.github.speky.core.PropertyRef

/**
 * Converts a [PropertyRef] to corresponding column.
 */
interface ColumnResolver {

  /**
   * Converts [prop] to corresponding column name.
   */
  fun resolveColumnName(prop: PropertyRef<*>): Column<*, *, *>?

  /**
   * Extension-function for [PropertyRef].
   *
   * @receiver [PropertyRef]
   *
   * @see [resolveColumnName]
   */
  fun PropertyRef<*>.columnName(): String =
    resolveColumnName(this)?.name ?: throw NoSuchElementException()
}
