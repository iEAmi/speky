package com.github.speky.extension

import com.github.speky.core.ClassRef
import com.github.speky.core.Lens
import com.github.speky.core.PropertyRef
import com.github.speky.core.table.Column
import com.github.speky.core.table.ConstructorRef
import com.github.speky.core.table.FunctionRef
import com.github.speky.core.table.SqlType
import com.github.speky.core.table.SqlValue
import com.github.speky.core.table.Table
import com.github.speky.core.table.ValueTransformer
import java.math.BigDecimal
import java.sql.Time
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*
import kotlin.reflect.KCallable
import kotlin.reflect.KProperty1

abstract class RichTable<T>(tableName: String, val classRef: ClassRef<T>) : Table<T>(tableName) {

  fun KCallable<T>.ctorRef(
    vararg parameters: ConstructorRef.Parameter<T, *>
  ): ConstructorRef<T> =
    ConstructorRef(FunctionRef(this.parameters.size) { this.call(*it) }, *parameters)

  inline fun <reified R, V : SqlValue> KProperty1<T, R>.`as`(
    sqlType: SqlType,
    columnName: String = name,
    transformer: ValueTransformer<R, V>
  ): Column<T, R, V> = Column(columnName, lensOf(), sqlType, transformer)

  fun KProperty1<T, Short>.asShort(
    columnName: String = name
  ): Column<T, Short, SqlValue.Short> = short(columnName, lensOf())

  fun KProperty1<T, Int>.asInt(
    columnName: String = name
  ): Column<T, Int, SqlValue.Int> = int(columnName, lensOf())

  fun KProperty1<T, Long>.asBigint(
    columnName: String = name
  ): Column<T, Long, SqlValue.Bigint> = bigint(columnName, lensOf())

  fun KProperty1<T, Float>.asFloat(
    columnName: String = name
  ): Column<T, Float, SqlValue.Float> = float(columnName, lensOf())

  fun KProperty1<T, Double>.asReal(
    columnName: String = name
  ): Column<T, Double, SqlValue.Real> = real(columnName, lensOf())

  fun KProperty1<T, Double>.asDouble(
    columnName: String = name
  ): Column<T, Double, SqlValue.Double> = double(columnName, lensOf())

  fun KProperty1<T, Double>.asNumeric(
    columnName: String = name
  ): Column<T, Double, SqlValue.Numeric> = numeric(columnName, lensOf())

  fun KProperty1<T, BigDecimal>.asDecimal(
    columnName: String = name
  ): Column<T, BigDecimal, SqlValue.Decimal> = decimal(columnName, lensOf())

  fun KProperty1<T, String>.asVarchar(
    columnName: String = name
  ): Column<T, String, SqlValue.Varchar> = varchar(columnName, lensOf())

  fun KProperty1<T, String>.asText(
    columnName: String = name
  ): Column<T, String, SqlValue.Text> = text(columnName, lensOf())

  fun KProperty1<T, Date>.asDate(
    columnName: String = name
  ): Column<T, Date, SqlValue.Date> = date(columnName, lensOf())

  fun KProperty1<T, Time>.asTime(
    columnName: String = name
  ): Column<T, Time, SqlValue.Time> = time(columnName, lensOf())

  fun KProperty1<T, Instant>.asTimestamp(
    columnName: String = name
  ): Column<T, Instant, SqlValue.Timestamp> = timestamp(columnName, lensOf())

  fun KProperty1<T, ByteArray>.asBinary(
    columnName: String = name
  ): Column<T, ByteArray, SqlValue.Binary> = binary(columnName, lensOf())

  fun KProperty1<T, UUID>.asUUID(
    columnName: String = name
  ): Column<T, UUID, SqlValue.UUID> = uuid(columnName, lensOf())

  fun KProperty1<T, Array<out Any>>.asArray(
    columnName: String = name
  ): Column<T, Array<out Any>, SqlValue.Array> = array(columnName, lensOf())

  fun KProperty1<T, ByteArray>.asBlob(
    columnName: String = name
  ): Column<T, ByteArray, SqlValue.Blob> = blob(columnName, lensOf())

  fun KProperty1<T, Boolean>.asBoolean(
    columnName: String = name
  ): Column<T, Boolean, SqlValue.Boolean> = boolean(columnName, lensOf())

  fun KProperty1<T, LocalTime>.asTimez(
    columnName: String = name
  ): Column<T, LocalTime, SqlValue.Timez> = timez(columnName, lensOf())

  fun KProperty1<T, LocalDateTime>.asTimestampz(
    columnName: String = name
  ): Column<T, LocalDateTime, SqlValue.Timestampz> = timestampz(columnName, lensOf())

  fun short(
    prop: KProperty1<T, Short>,
    columnName: String = prop.name
  ): Column<T, Short, SqlValue.Short> = short(columnName, prop.lensOf())

  fun int(
    prop: KProperty1<T, Int>,
    columnName: String = prop.name
  ): Column<T, Int, SqlValue.Int> = int(columnName, prop.lensOf())

  fun bigint(
    prop: KProperty1<T, Long>,
    columnName: String = prop.name
  ): Column<T, Long, SqlValue.Bigint> = bigint(columnName, prop.lensOf())

  fun float(
    prop: KProperty1<T, Float>,
    columnName: String = prop.name
  ): Column<T, Float, SqlValue.Float> = float(columnName, prop.lensOf())

  fun real(
    prop: KProperty1<T, Double>,
    columnName: String = prop.name
  ): Column<T, Double, SqlValue.Real> = real(columnName, prop.lensOf())

  fun double(
    prop: KProperty1<T, Double>,
    columnName: String = prop.name
  ): Column<T, Double, SqlValue.Double> = double(columnName, prop.lensOf())

  fun numeric(
    prop: KProperty1<T, Double>,
    columnName: String = prop.name
  ): Column<T, Double, SqlValue.Numeric> = numeric(columnName, prop.lensOf())

  fun decimal(
    prop: KProperty1<T, BigDecimal>,
    columnName: String = prop.name
  ): Column<T, BigDecimal, SqlValue.Decimal> = decimal(columnName, prop.lensOf())

  fun varchar(
    prop: KProperty1<T, String>,
    columnName: String = prop.name
  ): Column<T, String, SqlValue.Varchar> = varchar(columnName, prop.lensOf())

  fun text(
    prop: KProperty1<T, String>,
    columnName: String = prop.name
  ): Column<T, String, SqlValue.Text> = text(columnName, prop.lensOf())

  fun date(
    prop: KProperty1<T, Date>,
    columnName: String = prop.name
  ): Column<T, Date, SqlValue.Date> = date(columnName, prop.lensOf())

  fun time(
    prop: KProperty1<T, Time>,
    columnName: String = prop.name
  ): Column<T, Time, SqlValue.Time> = time(columnName, prop.lensOf())

  fun timestamp(
    prop: KProperty1<T, Instant>,
    columnName: String = prop.name
  ): Column<T, Instant, SqlValue.Timestamp> = timestamp(columnName, prop.lensOf())

  fun binary(
    prop: KProperty1<T, ByteArray>,
    columnName: String = prop.name
  ): Column<T, ByteArray, SqlValue.Binary> = binary(columnName, prop.lensOf())

  fun uuid(
    prop: KProperty1<T, UUID>,
    columnName: String = prop.name
  ): Column<T, UUID, SqlValue.UUID> = uuid(columnName, prop.lensOf())

  fun array(
    prop: KProperty1<T, Array<out Any>>,
    columnName: String = prop.name
  ): Column<T, Array<out Any>, SqlValue.Array> = array(columnName, prop.lensOf())

  fun blob(
    prop: KProperty1<T, ByteArray>,
    columnName: String = prop.name
  ): Column<T, ByteArray, SqlValue.Blob> = blob(columnName, prop.lensOf())

  fun boolean(
    prop: KProperty1<T, Boolean>,
    columnName: String = prop.name
  ): Column<T, Boolean, SqlValue.Boolean> = boolean(columnName, prop.lensOf())

  fun timez(
    prop: KProperty1<T, LocalTime>,
    columnName: String = prop.name
  ): Column<T, LocalTime, SqlValue.Timez> = timez(columnName, prop.lensOf())

  fun timestampz(
    prop: KProperty1<T, LocalDateTime>,
    columnName: String = prop.name
  ): Column<T, LocalDateTime, SqlValue.Timestampz> = timestampz(columnName, prop.lensOf())

  @PublishedApi
  internal inline fun <reified R> KProperty1<T, R>.lensOf(): Lens<R, T> =
    Lens.Focus(PropertyRef.of(name, classRef))
}
