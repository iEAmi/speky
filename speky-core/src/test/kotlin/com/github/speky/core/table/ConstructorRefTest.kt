package com.github.speky.core.table

import com.github.speky.core.ClassRef
import com.github.speky.core.Lens
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeInstanceOf

internal class ConstructorRefTest : FunSpec({
  test("ConstructorRef.Parameter.show test") {
    val embedded = object : Embedded<Int, String>("name_", Lens.on("length"), ClassRef.of()) {
      override fun constructorRef(): ConstructorRef<Int> {
        TODO("Not yet implemented")
      }
    }
    val normalParam = ConstructorRef.Parameter.NormalParam(
      Column(
        "name",
        Lens.on<String, Int>("length"),
        SqlType.Integer,
        SqlValue.Int.transformer
      )
    )
    val embeddedParam = ConstructorRef.Parameter.EmbeddedParam(embedded)

    with(ConstructorRef.Parameter.show) {
      normalParam.show() shouldBe "name: integer"
      embeddedParam.show() shouldBe "Embedded length: Int"
    }
  }

  test("ConstructorRef.Parameter.of should return value") {
    val embedded = object : Embedded<Int, String>("name_", Lens.on("length"), ClassRef.of()) {
      override fun constructorRef(): ConstructorRef<Int> {
        TODO("Not yet implemented")
      }
    }
    val column = Column("name", Lens.on<String, Int>("length"),
      SqlType.Integer, SqlValue.Int.transformer)

    val normalParam = ConstructorRef.Parameter.NormalParam(column)
    val embeddedParam = ConstructorRef.Parameter.EmbeddedParam(embedded)

    ConstructorRef.Parameter.of(column)
      .shouldBeInstanceOf<ConstructorRef.Parameter.NormalParam<String, Int, SqlValue.Int>>()

    ConstructorRef.Parameter.of(embedded)
      .shouldBeInstanceOf<ConstructorRef.Parameter.EmbeddedParam<String, Int>>()

    ConstructorRef.Parameter.of(column) shouldBe normalParam
    ConstructorRef.Parameter.of(embedded) shouldBe embeddedParam
  }

  test("inconsistent arity should throw") {
    val e = shouldThrow<IllegalArgumentException> {
      val f = { _: Int -> "" }
      val fRef = FunctionRef(1) { f.invoke(it[0] as Int) }

      val column = Column("name", Lens.on<String, Int>("length"),
        SqlType.Integer, SqlValue.Int.transformer)

      ConstructorRef(fRef, ConstructorRef.Parameter.of(column), ConstructorRef.Parameter.of(column))
    }

    e.message shouldNotBe null
    e.message!! shouldBe "Inconsistent arity"
  }
})
