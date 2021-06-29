package com.github.speky.core.table

import com.github.speky.core.PropertyRef

/**
 * Converts a [PropertyRef] to corresponding column.
 */
interface ColumnResolver {

  sealed class Result {
    data class SingleColumn(val column: Column<*, *, *>) : Result()
    data class EmbeddedColumns(val columns: List<Column<*, *, *>>) : Result()
  }

  /**
   * Converts [prop] to corresponding columns name.
   */
  fun resolveColumns(prop: PropertyRef<*>): List<Column<*, *, *>>

  /**
   * Extension-function for [PropertyRef].
   *
   * @receiver [PropertyRef]
   *
   * @see [resolveColumns]
   */
  fun PropertyRef<*>.columnsName(): List<String> {
    val columns = resolveColumns(this)

    if (columns.isEmpty())
      throw NoSuchElementException(
        "No column found for '${this.declaringClassRef.qualifiedName}.${this.name}'. " +
            "Also ne embedded type with columns found."
      )

    return columns.map { it.name }
  }
}
