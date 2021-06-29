package com.github.speky.postgres.compiler

import com.github.speky.core.Lens
import com.github.speky.core.PropertyRef
import com.github.speky.core.specification.Alias
import com.github.speky.core.specification.Source
import com.github.speky.core.table.Column
import com.github.speky.core.table.ColumnResolver
import com.github.speky.core.table.ConstructorRef
import com.github.speky.core.table.SqlType
import com.github.speky.core.table.SqlValue
import com.github.speky.core.table.Table
import com.github.speky.core.table.TableResolver
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

internal class SourceCompilerTest : FunSpec({
  test("compiling Source.Single") {
    val source = Source.Single<Human>(Alias.of("hh"))
    val compiler = SourceCompiler(PgSpecificationCompiler(MockTableResolver, MockColumnResolver))

    with(compiler) {
      source.compile().toString() shouldBe "\nFROM human AS hh"
    }
  }

  test("compiling Source.Mix.Multiply") {
    val source =
      Source.Mix.Multiply<Human, City, HumanCity>(Alias.of(Alias.of("hh"), Alias.of("cc")))
    val compiler = SourceCompiler(PgSpecificationCompiler(MockTableResolver, MockColumnResolver))

    with(compiler) {
      source.compile().toString() shouldBe "\nFROM human AS hh, city AS cc"
    }
  }

  test("compiling Source.Mix.CrossJoin") {
    val source =
      Source.Mix.CrossJoin<Human, City, HumanCity>(Alias.of(Alias.of("hh"), Alias.of("cc")))
    val compiler = SourceCompiler(PgSpecificationCompiler(MockTableResolver, MockColumnResolver))

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
    val compiler = SourceCompiler(PgSpecificationCompiler(MockTableResolver, MockColumnResolver))

    with(compiler) {
      source.compile()
        .toString() shouldBe "\nFROM human AS hh\n\t\t INNER JOIN city AS cc ON hh.id = cc.id"
    }
  }
}) {
  private data class Human(val id: Long)
  private data class City(val id: Long)
  private data class HumanCity(val id: Long)
  private object MockTableResolver : TableResolver {
    override fun resolveTable(alias: Alias<*>): Table<*> =
      object : Table<Any>(alias.classRef.name.lowercase()) {
        override fun constructorRef(): ConstructorRef<Any> {
          throw Exception()
        }
      }
  }

  private object MockColumnResolver : ColumnResolver {
    override fun resolveColumns(prop: PropertyRef<*>): ColumnResolver.ResolveResult =
      ColumnResolver.column(
        Column(
          prop.name,
          Lens.on<Int, Long>(""),
          SqlType.Bigint,
          SqlValue.Bigint.transformer
        )
      )
  }
}
