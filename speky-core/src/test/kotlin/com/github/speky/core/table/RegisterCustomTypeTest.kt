package com.github.speky.core.table

import com.google.gson.Gson
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

internal class RegisterCustomTypeTest : FunSpec({
  test("Define custom type") {
    data class Mobile(val value: String)

    class MobileTransformer : ValueTransformer<Mobile, SqlValue.Varchar> {
      override fun toSqlValue(value: Mobile): SqlValue.Varchar = SqlValue.Varchar(value.value)
      override fun fromSqlValue(sqlValue: SqlValue.Varchar): Mobile = Mobile(sqlValue.value)
    }

    val transformer = MobileTransformer()

    transformer.toSqlValue(Mobile("990")) shouldBe SqlValue.Varchar("990")
    transformer.fromSqlValue(SqlValue.Varchar("990")) shouldBe Mobile("990")
  }

  test("Define json custom type") {
    class MobileTransformer(private val gson: Gson) : ValueTransformer<Mobile, SqlValue.Varchar> {
      override fun toSqlValue(value: Mobile): SqlValue.Varchar =
        SqlValue.Varchar(gson.toJson(value))

      override fun fromSqlValue(sqlValue: SqlValue.Varchar): Mobile =
        gson.fromJson(sqlValue.value, Mobile::class.java)
    }

    val transformer = MobileTransformer(Gson())
    val mobile = Mobile(98, "990")

    transformer.toSqlValue(mobile) shouldBe
        SqlValue.Varchar("{\"countryCode\":98,\"value\":\"990\"}")

    transformer.fromSqlValue(
      SqlValue.Varchar("{\"countryCode\":98,\"value\":\"990\"}")
    ) shouldBe Mobile(98, "990")
  }
}) {
  private data class Mobile(val countryCode: Int, val value: String)
}
