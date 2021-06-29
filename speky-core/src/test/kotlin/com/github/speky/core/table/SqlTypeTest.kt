package com.github.speky.core.table

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

internal class SqlTypeTest : FunSpec({
  test("SqlType.show should return type names") {
    with(SqlType.show) {
      SqlType.SmallInt.show() shouldBe "smallint"
      SqlType.Integer.show() shouldBe "integer"
      SqlType.Bigint.show() shouldBe "bigint"
      SqlType.Float.show() shouldBe "float"
      SqlType.Real.show() shouldBe "real"
      SqlType.Double.show() shouldBe "double"
      SqlType.Numeric.show() shouldBe "numeric"
      SqlType.Decimal.show() shouldBe "decimal"
      SqlType.Varchar.show() shouldBe "varchar"
      SqlType.LongVarchar.show() shouldBe "longvarchar"
      SqlType.Date.show() shouldBe "date"
      SqlType.Time.show() shouldBe "time"
      SqlType.Timestamp.show() shouldBe "timestamp"
      SqlType.Binary.show() shouldBe "binary"
      SqlType.Other.show() shouldBe "other"
      SqlType.Array.show() shouldBe "array"
      SqlType.Blob.show() shouldBe "blob"
      SqlType.Boolean.show() shouldBe "boolean"
      SqlType.TimeWithTimeZone.show() shouldBe "timewithtimezone"
      SqlType.TimestampWithTimeZone.show() shouldBe "timestampwithtimezone"
    }
  }
})
