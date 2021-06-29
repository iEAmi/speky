package com.github.speky.core.table

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import java.math.BigDecimal
import java.sql.Time
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

internal class SqlValueTest : FunSpec({
  test("test SqlValues") {
    val short = SqlValue.Short(1)
    val int = SqlValue.Int(2)
    val bigint = SqlValue.Bigint(33)
    val float = SqlValue.Float(12.3f)
    val real = SqlValue.Real(11.3)
    val double = SqlValue.Double(23.3)
    val numeric = SqlValue.Numeric(121.2)
    val decimal = SqlValue.Decimal(BigDecimal.TEN)
    val varchar = SqlValue.Varchar("Name")
    val text = SqlValue.Text("Long text")
    val date = SqlValue.Date(Date())
    val time = SqlValue.Time(Time.valueOf(LocalTime.now()))
    val timestamp = SqlValue.Timestamp(Instant.now())
    val binary = SqlValue.Binary(ByteArray(10))
    val uuid = SqlValue.UUID(UUID.randomUUID())
    val array = SqlValue.Array(arrayOf(1, 2, 3, 4, 5))
    val blob = SqlValue.Blob(ByteArray(2))
    val boolean = SqlValue.Boolean(true)
    val timez = SqlValue.Timez(LocalTime.now())
    val timestampz = SqlValue.Timestampz(LocalDateTime.now())

    short.value.shouldBeInstanceOf<Short>()
    int.value.shouldBeInstanceOf<Int>()
    bigint.value.shouldBeInstanceOf<Long>()
    float.value.shouldBeInstanceOf<Float>()
    real.value.shouldBeInstanceOf<Double>()
    double.value.shouldBeInstanceOf<Double>()
    numeric.value.shouldBeInstanceOf<Double>()
    decimal.value.shouldBeInstanceOf<BigDecimal>()
    varchar.value.shouldBeInstanceOf<String>()
    text.value.shouldBeInstanceOf<String>()
    date.value.shouldBeInstanceOf<Date>()
    time.value.shouldBeInstanceOf<Time>()
    timestamp.value.shouldBeInstanceOf<Instant>()
    binary.value.shouldBeInstanceOf<ByteArray>()
    uuid.value.shouldBeInstanceOf<UUID>()
    array.value.shouldBeInstanceOf<Array<out Any>>()
    blob.value.shouldBeInstanceOf<ByteArray>()
    boolean.value.shouldBeInstanceOf<Boolean>()
    timez.value.shouldBeInstanceOf<LocalTime>()
    timestampz.value.shouldBeInstanceOf<LocalDateTime>()

    short.type shouldBe SqlType.SmallInt
    int.type shouldBe SqlType.Integer
    bigint.type shouldBe SqlType.Bigint
    float.type shouldBe SqlType.Float
    real.type shouldBe SqlType.Real
    double.type shouldBe SqlType.Double
    numeric.type shouldBe SqlType.Numeric
    decimal.type shouldBe SqlType.Decimal
    varchar.type shouldBe SqlType.Varchar
    text.type shouldBe SqlType.LongVarchar
    date.type shouldBe SqlType.Date
    time.type shouldBe SqlType.Time
    timestamp.type shouldBe SqlType.Timestamp
    binary.type shouldBe SqlType.Binary
    uuid.type shouldBe SqlType.Other
    array.type shouldBe SqlType.Array
    blob.type shouldBe SqlType.Blob
    boolean.type shouldBe SqlType.Boolean
    timez.type shouldBe SqlType.TimeWithTimeZone
    timestampz.type shouldBe SqlType.TimestampWithTimeZone
  }

  test("SqlValues ValueTransformer instance") {
    val short = SqlValue.Short.transformer
    val int = SqlValue.Int.transformer
    val bigint = SqlValue.Bigint.transformer
    val float = SqlValue.Float.transformer
    val real = SqlValue.Real.transformer
    val double = SqlValue.Double.transformer
    val numeric = SqlValue.Numeric.transformer
    val decimal = SqlValue.Decimal.transformer
    val varchar = SqlValue.Varchar.transformer
    val text = SqlValue.Text.transformer
    val date = SqlValue.Date.transformer
    val time = SqlValue.Time.transformer
    val timestamp = SqlValue.Timestamp.transformer
    val binary = SqlValue.Binary.transformer
    val uuid = SqlValue.UUID.transformer
    val array = SqlValue.Array.transformer
    val blob = SqlValue.Blob.transformer
    val boolean = SqlValue.Boolean.transformer
    val timez = SqlValue.Timez.transformer
    val timestampz = SqlValue.Timestampz.transformer

    short.shouldBeInstanceOf<ValueTransformer<Short, SqlValue.Short>>()
    int.shouldBeInstanceOf<ValueTransformer<Int, SqlValue.Int>>()
    bigint.shouldBeInstanceOf<ValueTransformer<Long, SqlValue.Binary>>()
    float.shouldBeInstanceOf<ValueTransformer<Float, SqlValue.Float>>()
    real.shouldBeInstanceOf<ValueTransformer<Double, SqlValue.Real>>()
    double.shouldBeInstanceOf<ValueTransformer<Double, SqlValue.Double>>()
    numeric.shouldBeInstanceOf<ValueTransformer<Double, SqlValue.Numeric>>()
    decimal.shouldBeInstanceOf<ValueTransformer<BigDecimal, SqlValue.Decimal>>()
    varchar.shouldBeInstanceOf<ValueTransformer<String, SqlValue.Varchar>>()
    text.shouldBeInstanceOf<ValueTransformer<String, SqlValue.Text>>()
    date.shouldBeInstanceOf<ValueTransformer<Date, SqlValue.Date>>()
    time.shouldBeInstanceOf<ValueTransformer<Time, SqlValue.Time>>()
    timestamp.shouldBeInstanceOf<ValueTransformer<Instant, SqlValue.Timestamp>>()
    binary.shouldBeInstanceOf<ValueTransformer<ByteArray, SqlValue.Binary>>()
    uuid.shouldBeInstanceOf<ValueTransformer<UUID, SqlValue.UUID>>()
    array.shouldBeInstanceOf<ValueTransformer<Array<out Any>, SqlValue.Array>>()
    blob.shouldBeInstanceOf<ValueTransformer<ByteArray, SqlValue.Blob>>()
    boolean.shouldBeInstanceOf<ValueTransformer<Boolean, SqlValue.Boolean>>()
    timez.shouldBeInstanceOf<ValueTransformer<LocalTime, SqlValue.Timez>>()
    timestampz.shouldBeInstanceOf<ValueTransformer<LocalDateTime, SqlValue.Timestampz>>()

    val testTime = Time.valueOf(LocalTime.now())
    val instant = Instant.now()
    val testUUID = UUID.randomUUID()
    val localTime = LocalTime.now()
    val localDateTime = LocalDateTime.now()
    val testBinary = ByteArray(2)

    short.toSqlValue(1) shouldBe SqlValue.Short(1)
    int.toSqlValue(1) shouldBe SqlValue.Int(1)
    bigint.toSqlValue(1) shouldBe SqlValue.Bigint(1)
    float.toSqlValue(1.5f) shouldBe SqlValue.Float(1.5f)
    real.toSqlValue(1.34) shouldBe SqlValue.Real(1.34)
    double.toSqlValue(1.32) shouldBe SqlValue.Double(1.32)
    numeric.toSqlValue(1.22) shouldBe SqlValue.Numeric(1.22)
    decimal.toSqlValue(BigDecimal.TEN) shouldBe SqlValue.Decimal(BigDecimal.TEN)
    varchar.toSqlValue("name") shouldBe SqlValue.Varchar("name")
    text.toSqlValue("Long text") shouldBe SqlValue.Text("Long text")
    date.toSqlValue(Date(20)) shouldBe SqlValue.Date(Date(20))
    time.toSqlValue(testTime) shouldBe SqlValue.Time(testTime)
    timestamp.toSqlValue(instant) shouldBe SqlValue.Timestamp(instant)
    binary.toSqlValue(testBinary).value shouldBe SqlValue.Binary(testBinary).value
    uuid.toSqlValue(testUUID) shouldBe SqlValue.UUID(testUUID)
    array.toSqlValue(arrayOf("a", "b")).value shouldBe SqlValue.Array(arrayOf("a", "b")).value
    blob.toSqlValue(testBinary).value shouldBe SqlValue.Blob(testBinary).value
    boolean.toSqlValue(true) shouldBe SqlValue.Boolean(true)
    timez.toSqlValue(localTime) shouldBe SqlValue.Timez(localTime)
    timestampz.toSqlValue(localDateTime) shouldBe SqlValue.Timestampz(localDateTime)

    short.fromSqlValue(SqlValue.Short(1)) shouldBe 1
    int.fromSqlValue(SqlValue.Int(1)) shouldBe 1
    bigint.fromSqlValue(SqlValue.Bigint(1)) shouldBe 1
    float.fromSqlValue(SqlValue.Float(1.5f)) shouldBe 1.5f
    real.fromSqlValue(SqlValue.Real(1.34)) shouldBe 1.34
    double.fromSqlValue(SqlValue.Double(1.32)) shouldBe 1.32
    numeric.fromSqlValue(SqlValue.Numeric(1.22)) shouldBe 1.22
    decimal.fromSqlValue(SqlValue.Decimal(BigDecimal.TEN)) shouldBe BigDecimal.TEN
    varchar.fromSqlValue(SqlValue.Varchar("name")) shouldBe "name"
    text.fromSqlValue(SqlValue.Text("Long text")) shouldBe "Long text"
    date.fromSqlValue(SqlValue.Date(Date(20))) shouldBe Date(20)
    time.fromSqlValue(SqlValue.Time(testTime)) shouldBe testTime
    timestamp.fromSqlValue(SqlValue.Timestamp(instant)) shouldBe instant
    binary.fromSqlValue(SqlValue.Binary(testBinary)) shouldBe testBinary
    uuid.fromSqlValue(SqlValue.UUID(testUUID)) shouldBe testUUID
    array.fromSqlValue(SqlValue.Array(arrayOf("a", "b"))) shouldBe arrayOf("a", "b")
    blob.fromSqlValue(SqlValue.Blob(testBinary)) shouldBe testBinary
    boolean.fromSqlValue(SqlValue.Boolean(true)) shouldBe true
    timez.fromSqlValue(SqlValue.Timez(localTime)) shouldBe localTime
    timestampz.fromSqlValue(SqlValue.Timestampz(localDateTime)) shouldBe localDateTime
  }
})
