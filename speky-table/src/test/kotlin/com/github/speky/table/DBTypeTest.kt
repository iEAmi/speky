package com.github.speky.table

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import java.time.LocalDateTime
import java.util.*

internal class DBTypeTest : FunSpec({
  test("DBType.Int should be DBType<kotlin.Int>") {
    DBType.Int.shouldBeInstanceOf<DBType<Int>>()
  }

  test("DBType.Bigint should be DBType<kotlin.Long>") {
    DBType.Bigint.shouldBeInstanceOf<DBType<Long>>()
  }

  test("DBType.Varchar should be DBType<kotlin.String>") {
    DBType.Varchar.shouldBeInstanceOf<DBType<String>>()
  }

  test("DBType.Text should be DBType<kotlin.String>") {
    DBType.Text.shouldBeInstanceOf<DBType<String>>()
  }

  test("DBType.Boolean should be DBType<kotlin.Boolean>") {
    DBType.Boolean.shouldBeInstanceOf<DBType<Boolean>>()
  }

  test("DBType.UUID should be DBType<java.util.UUID>") {
    DBType.UUID.shouldBeInstanceOf<DBType<UUID>>()
  }

  test("DBType.Timestamp should be DBType<LocalDateTime>") {
    DBType.Timestamp.shouldBeInstanceOf<DBType<LocalDateTime>>()
  }

  test("DBType.Timestampz should be DBType<LocalDateTime>") {
    DBType.Timestampz.shouldBeInstanceOf<DBType<LocalDateTime>>()
  }
})
