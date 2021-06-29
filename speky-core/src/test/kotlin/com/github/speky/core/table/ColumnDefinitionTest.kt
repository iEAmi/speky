package com.github.speky.core.table

import com.github.speky.core.Lens
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

internal class ColumnDefinitionTest : FunSpec({
  test("column name should be valid") {
    shouldNotThrow<IllegalArgumentException> { MyColumnDefinition() }
    val definition = MyColumnDefinition()

    definition.smallInt.name shouldBe "smallInt"
    definition.integer.name shouldBe "integer"
    definition.bigint.name shouldBe "bigint"
    definition.float.name shouldBe "float"
    definition.real.name shouldBe "real"
    definition.double.name shouldBe "double"
    definition.numeric.name shouldBe "numeric"
    definition.decimal.name shouldBe "decimal"
    definition.varchar.name shouldBe "varchar"
    definition.longVarchar.name shouldBe "longVarchar"
    definition.date.name shouldBe "date"
    definition.time.name shouldBe "time"
    definition.timestamp.name shouldBe "timestamp"
    definition.binary.name shouldBe "binary"
    definition.other.name shouldBe "other"
    definition.array.name shouldBe "array"
    definition.blob.name shouldBe "blob"
    definition.boolean.name shouldBe "boolean"
    definition.timeWithTimeZone.name shouldBe "timeWithTimeZone"
    definition.timestampWithTimeZone.name shouldBe "timestampWithTimeZone"
    definition.custom.name shouldBe "mobile"
  }

  test("column lens should be valid") {
    shouldNotThrow<IllegalArgumentException> { MyColumnDefinition() }
    val definition = MyColumnDefinition()

    definition.smallInt.lens shouldBe Lens.on("name")
    definition.integer.lens shouldBe Lens.on("name")
    definition.bigint.lens shouldBe Lens.on("name")
    definition.float.lens shouldBe Lens.on("name")
    definition.real.lens shouldBe Lens.on("name")
    definition.double.lens shouldBe Lens.on("name")
    definition.numeric.lens shouldBe Lens.on("name")
    definition.decimal.lens shouldBe Lens.on("name")
    definition.varchar.lens shouldBe Lens.on("name")
    definition.longVarchar.lens shouldBe Lens.on("name")
    definition.date.lens shouldBe Lens.on("name")
    definition.time.lens shouldBe Lens.on("name")
    definition.timestamp.lens shouldBe Lens.on("name")
    definition.binary.lens shouldBe Lens.on("name")
    definition.other.lens shouldBe Lens.on("name")
    definition.array.lens shouldBe Lens.on("name")
    definition.blob.lens shouldBe Lens.on("name")
    definition.boolean.lens shouldBe Lens.on("name")
    definition.timeWithTimeZone.lens shouldBe Lens.on("name")
    definition.timestampWithTimeZone.lens shouldBe Lens.on("name")
    definition.custom.lens shouldBe Lens.on("mobile")
  }

  test("column sqlType should be valid") {
    shouldNotThrow<IllegalArgumentException> { MyColumnDefinition() }
    val definition = MyColumnDefinition()

    definition.smallInt.sqlType shouldBe SqlType.SmallInt
    definition.integer.sqlType shouldBe SqlType.Integer
    definition.bigint.sqlType shouldBe SqlType.Bigint
    definition.float.sqlType shouldBe SqlType.Float
    definition.real.sqlType shouldBe SqlType.Real
    definition.double.sqlType shouldBe SqlType.Double
    definition.numeric.sqlType shouldBe SqlType.Numeric
    definition.decimal.sqlType shouldBe SqlType.Decimal
    definition.varchar.sqlType shouldBe SqlType.Varchar
    definition.longVarchar.sqlType shouldBe SqlType.LongVarchar
    definition.date.sqlType shouldBe SqlType.Date
    definition.time.sqlType shouldBe SqlType.Time
    definition.timestamp.sqlType shouldBe SqlType.Timestamp
    definition.binary.sqlType shouldBe SqlType.Binary
    definition.other.sqlType shouldBe SqlType.Other
    definition.array.sqlType shouldBe SqlType.Array
    definition.blob.sqlType shouldBe SqlType.Blob
    definition.boolean.sqlType shouldBe SqlType.Boolean
    definition.timeWithTimeZone.sqlType shouldBe SqlType.TimeWithTimeZone
    definition.timestampWithTimeZone.sqlType shouldBe SqlType.TimestampWithTimeZone
    definition.custom.sqlType shouldBe SqlType.Varchar
  }

  test("column transformer should be valid") {
    shouldNotThrow<IllegalArgumentException> { MyColumnDefinition() }
    val definition = MyColumnDefinition()

    definition.smallInt.transformer shouldBe SqlValue.Short.transformer
    definition.integer.transformer shouldBe SqlValue.Int.transformer
    definition.bigint.transformer shouldBe SqlValue.Bigint.transformer
    definition.float.transformer shouldBe SqlValue.Float.transformer
    definition.real.transformer shouldBe SqlValue.Real.transformer
    definition.double.transformer shouldBe SqlValue.Double.transformer
    definition.numeric.transformer shouldBe SqlValue.Numeric.transformer
    definition.decimal.transformer shouldBe SqlValue.Decimal.transformer
    definition.varchar.transformer shouldBe SqlValue.Varchar.transformer
    definition.longVarchar.transformer shouldBe SqlValue.Text.transformer
    definition.date.transformer shouldBe SqlValue.Date.transformer
    definition.time.transformer shouldBe SqlValue.Time.transformer
    definition.timestamp.transformer shouldBe SqlValue.Timestamp.transformer
    definition.binary.transformer shouldBe SqlValue.Binary.transformer
    definition.other.transformer shouldBe SqlValue.UUID.transformer
    definition.array.transformer shouldBe SqlValue.Array.transformer
    definition.blob.transformer shouldBe SqlValue.Blob.transformer
    definition.boolean.transformer shouldBe SqlValue.Boolean.transformer
    definition.timeWithTimeZone.transformer shouldBe SqlValue.Timez.transformer
    definition.timestampWithTimeZone.transformer shouldBe SqlValue.Timestampz.transformer
    definition.custom.transformer.shouldBeInstanceOf<MobileTransformer>()
  }
}) {

 private data class Mobile(val value: String)

  private class MobileTransformer : ValueTransformer<Mobile, SqlValue.Varchar> {
    override fun toSqlValue(value: Mobile): SqlValue.Varchar = SqlValue.Varchar(value.value)
    override fun fromSqlValue(sqlValue: SqlValue.Varchar): Mobile = Mobile(sqlValue.value)
  }

  private class Human
  private class MyColumnDefinition : Table<Human>("humans") {
    val smallInt = short("smallInt", Lens.on("name"))
    val integer = int("integer", Lens.on("name"))
    val bigint = bigint("bigint", Lens.on("name"))
    val float = float("float", Lens.on("name"))
    val real = real("real", Lens.on("name"))
    val double = double("double", Lens.on("name"))
    val numeric = numeric("numeric", Lens.on("name"))
    val decimal = decimal("decimal", Lens.on("name"))
    val varchar = varchar("varchar", Lens.on("name"))
    val longVarchar = text("longVarchar", Lens.on("name"))
    val date = date("date", Lens.on("name"))
    val time = time("time", Lens.on("name"))
    val timestamp = timestamp("timestamp", Lens.on("name"))
    val binary = binary("binary", Lens.on("name"))
    val other = uuid("other", Lens.on("name"))
    val array = array("array", Lens.on("name"))
    val blob = blob("blob", Lens.on("name"))
    val boolean = boolean("boolean", Lens.on("name"))
    val timeWithTimeZone = timez("timeWithTimeZone", Lens.on("name"))
    val timestampWithTimeZone = timestampz("timestampWithTimeZone", Lens.on("name"))

    val custom = custom("mobile", Lens.on("mobile"), MobileTransformer())
    override fun constructorRef(): ConstructorRef<Human> {
      TODO("Not yet implemented")
    }
  }
}
