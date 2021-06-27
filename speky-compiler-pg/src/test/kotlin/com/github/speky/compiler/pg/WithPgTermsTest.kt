package com.github.speky.compiler.pg

import com.github.speky.compiler.pg.internal.PgTerm
import com.github.speky.compiler.pg.internal.WithPgTerms
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

internal class WithPgTermsTest : FunSpec({
  test("validate pg terms") {
    Mock.from() shouldBe PgTerm("\nFROM ")
    Mock.`as`() shouldBe PgTerm(" AS ")
    Mock.crossJoin() shouldBe PgTerm("\n\t\t CROSS JOIN ")
    Mock.innerJoin() shouldBe PgTerm("\n\t\t INNER JOIN ")
    Mock.on() shouldBe PgTerm(" ON ")
    Mock.comma() shouldBe PgTerm(", ")
    Mock.dot() shouldBe PgTerm(".")
    Mock.eq() shouldBe PgTerm(" = ")
  }
}) {
  private object Mock : WithPgTerms
}
