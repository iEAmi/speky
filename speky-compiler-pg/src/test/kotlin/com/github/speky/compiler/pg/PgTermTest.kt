package com.github.speky.compiler.pg

import com.github.speky.compiler.pg.internal.PgTerm
import com.github.speky.core.specification.Alias
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

internal class PgTermTest : FunSpec({
  test("toString should return inner value") {
    val term = PgTerm("Name")
    term.toString() shouldBe "Name"
  }

  test("operator plus PgTerm should be valid") {
    val term = PgTerm("Name")

    term + PgTerm("family") shouldBe PgTerm( "Namefamily")
  }

  test("operator plus String should be valid") {
    val term = PgTerm("Name")

    term + ".foo" shouldBe PgTerm( "Name.foo")
  }

  test("operator plus Alias.Single should be valid") {
    val term = PgTerm("Name")

    term + Alias.of<Int>("i") shouldBe PgTerm( "Namei")
  }

  test("operator plus Alias should be valid") {
    val term = PgTerm("Name")

    term + (Alias.of<Int>("i") as Alias<Int>) shouldBe PgTerm( "Namei")
  }
})
