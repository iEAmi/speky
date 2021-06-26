package com.github.speky.table

import com.github.speky.core.Lens
import java.math.BigDecimal
import java.sql.Time
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

/**
 * Set of methods that could define [Column].
 */
@Suppress("EXPOSED_SUPER_CLASS")
abstract class ColumnDefinition<T> internal constructor() : ColumnPool<T>() {
  /**
   * Registers new [Column] of type [SqlType.SmallInt].
   *
   * @throws [IllegalArgumentException] if [columnName] registered before
   */
  fun short(columnName: String, lens: Lens<Short, T>): Column<T, Short, SqlValue.Short> =
    column(columnName, lens, SqlType.SmallInt, SqlValue.Short.transformer)

  /**
   * Registers new [Column] of type [SqlType.Integer].
   *
   * @throws [IllegalArgumentException] if [columnName] registered before
   */
  fun int(columnName: String, lens: Lens<Int, T>): Column<T, Int, SqlValue.Int> =
    column(columnName, lens, SqlType.Integer, SqlValue.Int.transformer)

  /**
   * Registers new [Column] of type [SqlType.Bigint].
   *
   * @throws [IllegalArgumentException] if [columnName] registered before
   */
  fun bigint(columnName: String, lens: Lens<Long, T>): Column<T, Long, SqlValue.Bigint> =
    column(columnName, lens, SqlType.Bigint, SqlValue.Bigint.transformer)

  /**
   * Registers new [Column] of type [SqlType.Float].
   *
   * @throws [IllegalArgumentException] if [columnName] registered before
   */
  fun float(columnName: String, lens: Lens<Float, T>): Column<T, Float, SqlValue.Float> =
    column(columnName, lens, SqlType.Float, SqlValue.Float.transformer)

  /**
   * Registers new [Column] of type [SqlType.Real].
   *
   * @throws [IllegalArgumentException] if [columnName] registered before
   */
  fun real(columnName: String, lens: Lens<Double, T>): Column<T, Double, SqlValue.Real> =
    column(columnName, lens, SqlType.Real, SqlValue.Real.transformer)

  /**
   * Registers new [Column] of type [SqlType.Double].
   *
   * @throws [IllegalArgumentException] if [columnName] registered before
   */
  fun double(columnName: String, lens: Lens<Double, T>): Column<T, Double, SqlValue.Double> =
    column(columnName, lens, SqlType.Double, SqlValue.Double.transformer)

  /**
   * Registers new [Column] of type [SqlType.Numeric].
   *
   * @throws [IllegalArgumentException] if [columnName] registered before
   */
  fun numeric(columnName: String, lens: Lens<Double, T>): Column<T, Double, SqlValue.Numeric> =
    column(columnName, lens, SqlType.Numeric, SqlValue.Numeric.transformer)

  /**
   * Registers new [Column] of type [SqlType.Decimal].
   *
   * @throws [IllegalArgumentException] if [columnName] registered before
   */
  fun decimal(
    columnName: String,
    lens: Lens<BigDecimal, T>
  ): Column<T, BigDecimal, SqlValue.Decimal> =
    column(columnName, lens, SqlType.Decimal, SqlValue.Decimal.transformer)

  /**
   * Registers new [Column] of type [SqlType.Varchar].
   *
   * @throws [IllegalArgumentException] if [columnName] registered before
   */
  fun varchar(columnName: String, lens: Lens<String, T>): Column<T, String, SqlValue.Varchar> =
    column(columnName, lens, SqlType.Varchar, SqlValue.Varchar.transformer)

  /**
   * Registers new [Column] of type [SqlType.LongVarchar].
   *
   * @throws [IllegalArgumentException] if [columnName] registered before
   */
  fun text(columnName: String, lens: Lens<String, T>): Column<T, String, SqlValue.Text> =
    column(columnName, lens, SqlType.LongVarchar, SqlValue.Text.transformer)

  /**
   * Registers new [Column] of type [SqlType.Date].
   *
   * @throws [IllegalArgumentException] if [columnName] registered before
   */
  fun date(columnName: String, lens: Lens<Date, T>): Column<T, Date, SqlValue.Date> =
    column(columnName, lens, SqlType.Date, SqlValue.Date.transformer)

  /**
   * Registers new [Column] of type [SqlType.Time].
   *
   * @throws [IllegalArgumentException] if [columnName] registered before
   */
  fun time(columnName: String, lens: Lens<Time, T>): Column<T, Time, SqlValue.Time> =
    column(columnName, lens, SqlType.Time, SqlValue.Time.transformer)

  /**
   * Registers new [Column] of type [SqlType.Timestamp].
   *
   * @throws [IllegalArgumentException] if [columnName] registered before
   */
  fun timestamp(
    columnName: String,
    lens: Lens<Instant, T>
  ): Column<T, Instant, SqlValue.Timestamp> =
    column(columnName, lens, SqlType.Timestamp, SqlValue.Timestamp.transformer)

  /**
   * Registers new [Column] of type [SqlType.Binary].
   *
   * @throws [IllegalArgumentException] if [columnName] registered before
   */
  fun binary(columnName: String, lens: Lens<ByteArray, T>): Column<T, ByteArray, SqlValue.Binary> =
    column(columnName, lens, SqlType.Binary, SqlValue.Binary.transformer)

  /**
   * Registers new [Column] of type [SqlType.Other].
   *
   * @throws [IllegalArgumentException] if [columnName] registered before
   */
  fun uuid(columnName: String, lens: Lens<UUID, T>): Column<T, UUID, SqlValue.UUID> =
    column(columnName, lens, SqlType.Other, SqlValue.UUID.transformer)

  /**
   * Registers new [Column] of type [SqlType.Array].
   *
   * @throws [IllegalArgumentException] if [columnName] registered before
   */
  fun array(
    columnName: String,
    lens: Lens<Array<out Any>, T>
  ): Column<T, Array<out Any>, SqlValue.Array> =
    column(columnName, lens, SqlType.Array, SqlValue.Array.transformer)

  /**
   * Registers new [Column] of type [SqlType.Blob].
   *
   * @throws [IllegalArgumentException] if [columnName] registered before
   */
  fun blob(columnName: String, lens: Lens<ByteArray, T>): Column<T, ByteArray, SqlValue.Blob> =
    column(columnName, lens, SqlType.Blob, SqlValue.Blob.transformer)

  /**
   * Registers new [Column] of type [SqlType.Boolean].
   *
   * @throws [IllegalArgumentException] if [columnName] registered before
   */
  fun boolean(columnName: String, lens: Lens<Boolean, T>): Column<T, Boolean, SqlValue.Boolean> =
    column(columnName, lens, SqlType.Boolean, SqlValue.Boolean.transformer)

  /**
   * Registers new [Column] of type [SqlType.TimeWithTimeZone].
   *
   * @throws [IllegalArgumentException] if [columnName] registered before
   */
  fun timez(columnName: String, lens: Lens<LocalTime, T>): Column<T, LocalTime, SqlValue.Timez> =
    column(columnName, lens, SqlType.TimeWithTimeZone, SqlValue.Timez.transformer)

  /**
   * Registers new [Column] of type [SqlType.TimestampWithTimeZone].
   *
   * @throws [IllegalArgumentException] if [columnName] registered before
   */
  fun timestampz(
    columnName: String,
    lens: Lens<LocalDateTime, T>
  ): Column<T, LocalDateTime, SqlValue.Timestampz> =
    column(columnName, lens, SqlType.TimestampWithTimeZone, SqlValue.Timestampz.transformer)

  /**
   * Registers new [Column] of custom type.
   *
   * @param columnName name of column
   * @param lens [Lens] that points to property
   * @param transformer [ValueTransformer] for transforming value from/to sql value
   *
   * @throws [IllegalArgumentException] if [columnName] registered before
   */
  fun <R, S : SqlValue> custom(
    columnName: String,
    lens: Lens<R, T>,
    transformer: ValueTransformer<R, S>
  ): Column<T, R, S> = Column(columnName, lens, SqlType.Varchar, transformer)

  @Suppress("LongParameterList")
  private fun <R, S : SqlValue> column(
    columnName: String,
    lens: Lens<R, T>,
    sqlType: SqlType,
    transformer: ValueTransformer<R, S>
  ): Column<T, R, S> = Column(columnName, lens, sqlType, transformer)
    .apply { registerColumn(this) }
}
