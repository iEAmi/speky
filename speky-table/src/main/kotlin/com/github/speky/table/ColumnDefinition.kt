package com.github.speky.table

import com.github.speky.core.Lens
import java.time.LocalDateTime
import java.util.*

/**
 * Set of methods that could define [Column].
 */
@Suppress("EXPOSED_SUPER_CLASS")
abstract class ColumnDefinition<T> internal constructor() : ColumnPool<T>() {
  /**
   * Registers new [Column] of type [SqlType.Integer].
   *
   * @throws [IllegalArgumentException] if [columnName] registered before
   */
   fun int(columnName: String, lens: Lens<Int, T>): Column<T, Int> =
    Column(columnName, lens, SqlType.Integer).apply { registerColumn(this) }

  /**
   * Registers new [Column] of type [SqlType.Bigint].
   *
   * @throws [IllegalArgumentException] if [columnName] registered before
   */
   fun bigint(columnName: String, lens: Lens<Long, T>): Column<T, Long> =
    Column(columnName, lens, SqlType.Bigint).apply { registerColumn(this) }

  /**
   * Registers new [Column] of type [SqlType.Varchar].
   *
   * @throws [IllegalArgumentException] if [columnName] registered before
   */
   fun varchar(columnName: String, lens: Lens<String, T>): Column<T, String> =
    Column(columnName, lens, SqlType.Varchar).apply { registerColumn(this) }

  /**
   * Registers new [Column] of type [SqlType.LongVarchar].
   *
   * @throws [IllegalArgumentException] if [columnName] registered before
   */
   fun text(columnName: String, lens: Lens<String, T>): Column<T, String> =
    Column(columnName, lens, SqlType.LongVarchar).apply { registerColumn(this) }

  /**
   * Registers new [Column] of type [SqlType.Boolean].
   *
   * @throws [IllegalArgumentException] if [columnName] registered before
   */
   fun boolean(columnName: String, lens: Lens<Boolean, T>): Column<T, Boolean> =
    Column(columnName, lens, SqlType.Boolean).apply { registerColumn(this) }

  /**
   * Registers new [Column] of type [SqlType.Other].
   *
   * @throws [IllegalArgumentException] if [columnName] registered before
   */
   fun uuid(columnName: String, lens: Lens<UUID, T>): Column<T, UUID> =
    Column(columnName, lens, SqlType.Other).apply { registerColumn(this) }

  /**
   * Registers new [Column] of type [SqlType.Timestamp].
   *
   * @throws [IllegalArgumentException] if [columnName] registered before
   */
   fun timestamp(
    columnName: String,
    lens: Lens<LocalDateTime, T>
  ): Column<T, LocalDateTime> =
    Column(columnName, lens, SqlType.Timestamp).apply { registerColumn(this) }

  /**
   * Registers new [Column] of type [SqlType.TimestampWithTimeZone].
   *
   * @throws [IllegalArgumentException] if [columnName] registered before
   */
   fun timestampz(
    columnName: String,
    lens: Lens<LocalDateTime, T>
  ): Column<T, LocalDateTime> =
    Column(columnName, lens, SqlType.TimestampWithTimeZone).apply { registerColumn(this) }
}
