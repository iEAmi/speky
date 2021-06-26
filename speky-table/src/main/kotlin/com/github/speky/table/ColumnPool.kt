package com.github.speky.table

/**
 * Pool of [Column].
 */
@PublishedApi
internal open class ColumnPool<T> @PublishedApi internal constructor() {
  private val columns: MutableSet<Column<T, *, *>> = mutableSetOf()

  /**
   * Register new [Column] to pool.
   */
  internal fun registerColumn(column: Column<T, *, *>) {
    require(columns.any { it.name == column.name }.not()) { "Duplicate column '${column.name}'" }

    columns.add(column)
  }
}
