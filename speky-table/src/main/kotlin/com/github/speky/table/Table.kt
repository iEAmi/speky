package com.github.speky.table

import com.github.speky.core.Lens
import java.time.LocalDateTime
import java.util.*

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

  /**
   * Registers new [Column] of type [DBType.Varchar].
   *
   * @throws [IllegalArgumentException] if [columnName] registered before
   */
  protected fun varchar(columnName: String, lens: Lens<String, T>): Column<T, String> =
    Column(columnName, this, lens, DBType.Varchar).apply { registerColumn(this) }

  /**
   * Registers new [Column] of type [DBType.Text].
   *
   * @throws [IllegalArgumentException] if [columnName] registered before
   */
  protected fun text(columnName: String, lens: Lens<String, T>): Column<T, String> =
    Column(columnName, this, lens, DBType.Text).apply { registerColumn(this) }

  /**
   * Registers new [Column] of type [DBType.Boolean].
   *
   * @throws [IllegalArgumentException] if [columnName] registered before
   */
  protected fun boolean(columnName: String, lens: Lens<Boolean, T>): Column<T, Boolean> =
    Column(columnName, this, lens, DBType.Boolean).apply { registerColumn(this) }

  /**
   * Registers new [Column] of type [DBType.UUID].
   *
   * @throws [IllegalArgumentException] if [columnName] registered before
   */
  protected fun uuid(columnName: String, lens: Lens<UUID, T>): Column<T, UUID> =
    Column(columnName, this, lens, DBType.UUID).apply { registerColumn(this) }

  /**
   * Registers new [Column] of type [DBType.Timestamp].
   *
   * @throws [IllegalArgumentException] if [columnName] registered before
   */
  protected fun timestamp(
    columnName: String,
    lens: Lens<LocalDateTime, T>
  ): Column<T, LocalDateTime> =
    Column(columnName, this, lens, DBType.Timestamp).apply { registerColumn(this) }

  /**
   * Registers new [Column] of type [DBType.Timestampz].
   *
   * @throws [IllegalArgumentException] if [columnName] registered before
   */
  protected fun timestampz(
    columnName: String,
    lens: Lens<LocalDateTime, T>
  ): Column<T, LocalDateTime> =
    Column(columnName, this, lens, DBType.Timestampz).apply { registerColumn(this) }

  private fun registerColumn(column: Column<T, *>) {
    require(columns.any { it.name == column.name }.not()) { "Duplicate column '${column.name}'" }

    columns.add(column)
  }

  override fun toString(): String = "Table(tableName='$tableName')"
}
