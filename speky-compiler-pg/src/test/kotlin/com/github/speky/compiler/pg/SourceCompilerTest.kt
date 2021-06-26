package com.github.speky.compiler.pg

import com.github.speky.core.Lens
import com.github.speky.core.specification.Alias
import com.github.speky.core.specification.Source
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

internal class SourceCompilerTest : FunSpec({
  test("compiling Source.Single") {
    val source = Source.Single<Human>(Alias.of("hh"))
    val compiler = SourceCompiler(PgSpecificationCompiler(MockTableResolver))

    with(compiler) {
      source.compile().toString() shouldBe "\nFROM human AS hh"
    }
  }

  test("compiling Source.Mix.Multiply") {
    val source =
      Source.Mix.Multiply<Human, City, HumanCity>(Alias.of(Alias.of("hh"), Alias.of("cc")))
    val compiler = SourceCompiler(PgSpecificationCompiler(MockTableResolver))

    with(compiler) {
      source.compile().toString() shouldBe "\nFROM human AS hh, city AS cc"
    }
  }

  test("compiling Source.Mix.CrossJoin") {
    val source =
      Source.Mix.CrossJoin<Human, City, HumanCity>(Alias.of(Alias.of("hh"), Alias.of("cc")))
    val compiler = SourceCompiler(PgSpecificationCompiler(MockTableResolver))

    with(compiler) {
      source.compile().toString() shouldBe "\nFROM human AS hh\n\t\t CROSS JOIN city AS cc"
    }
  }

  test("compiling Source.Mix.InnerJoin") {
    val source = Source.Mix.InnerJoin<Human, City, HumanCity, Long>(
      Alias.of(Alias.of("hh"), Alias.of("cc")),
      leftSelector = Lens.on("id"),
      rightSelector = Lens.on("id")
    )
    val compiler = SourceCompiler(PgSpecificationCompiler(MockTableResolver))

    with(compiler) {
      source.compile()
        .toString() shouldBe "\nFROM human AS hh\n\t\t INNER JOIN city AS cc ON hh.id = cc.id"
    }
  }
}) {
  private class Human(val id: Long)
  private class City(val id: Long)
  private class HumanCity(val id: Long)
  private object MockTableResolver : TableResolver {
    override fun resolveTableName(alias: Alias<*>): String = alias.classRef.name.lowercase()
  }
}
