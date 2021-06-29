package com.github.speky.core.table

import com.github.speky.core.PropertyRef

/**
 * Pool of [Column].
 */
@PublishedApi
internal open class ColumnPool<T> @PublishedApi internal constructor() : ColumnResolver {
  private val columns: MutableSet<Column<T, *, *>> = mutableSetOf()

  /**
   * Register new [Column] to pool.
   */
  internal fun registerColumn(column: Column<T, *, *>) {
    require(columns.any { it.name == column.name }.not()) { "Duplicate column '${column.name}'" }

    columns.add(column)
  }

  internal open fun columns(): Set<Column<T, *, *>> = columns

  override fun resolveColumns(prop: PropertyRef<*>): List<Column<*, *, *>> {
    val column = columns.singleOrNull { it.lens.propertyRef == prop }

    return if (column == null) emptyList() else listOf(column)
  }
}
