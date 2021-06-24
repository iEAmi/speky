package com.github.speky.table

import com.github.speky.core.Lens

/**
 * Presents a table in relational databases.
 *
 * @property tableName name of the table in database.
 *
 * @param T type of the class which it belong to
 */
abstract class Table<T>(private val tableName: String) {
  private val columns: MutableSet<Column<T, *>> = mutableSetOf()

  protected abstract fun foo()

  /**
   * Registers new [Column] of type [DBType.Int].
   *
   * @throws [IllegalArgumentException] if [columnName] registered before
   */
  protected fun int(columnName: String, lens: Lens<Int, T>): Column<T, Int> =
    Column(columnName, this, lens, DBType.Int).apply { registerColumn(this) }

  /**
   * Registers new [Column] of type [DBType.Bigint].
   *
   * @throws [IllegalArgumentException] if [columnName] registered before
   */
  protected fun bigint(columnName: String, lens: Lens<Long, T>): Column<T, Long> =
    Column(columnName, this, lens, DBType.Bigint).apply { registerColumn(this) }

  private fun registerColumn(column: Column<T, *>) {
    require(columns.any { it.name == column.name }) { "Duplicate column '${column.name}'" }

    columns.add(column)
  }

  override fun toString(): String = "Table(tableName='$tableName')"
}
