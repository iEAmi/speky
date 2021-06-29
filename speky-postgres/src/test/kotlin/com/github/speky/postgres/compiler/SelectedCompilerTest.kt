package com.github.speky.postgres.compiler

import com.github.speky.core.Lens
import com.github.speky.core.PropertyRef
import com.github.speky.core.specification.Alias
import com.github.speky.core.specification.Specification
import com.github.speky.core.table.Column
import com.github.speky.core.table.ColumnResolver
import com.github.speky.core.table.ConstructorRef
import com.github.speky.core.table.SqlType
import com.github.speky.core.table.SqlValue
import com.github.speky.core.table.Table
import com.github.speky.core.table.TableResolver
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

internal class SelectedCompilerTest : FunSpec({
  test("compiling Selected.All") {
    val selected = Specification.from<Human>("hh")
      .select()
    val compiler = SelectedCompiler(PgSpecificationCompiler(MockTableResolver, MockColumnResolver))

    with(compiler) {
      selected.compile().toString() shouldBe "SELECT *\nFROM human AS hh"
    }
  }

  test("compiling Selected.Some") {
    val selected = Specification.from<Human>("hh")
      .select(
        Lens.on<Human, Long>("id"),
        Lens.on<Human, Long>("name"),
        Lens.on<Human, Long>("family"),
      )
    val compiler = SelectedCompiler(PgSpecificationCompiler(MockTableResolver, MockColumnResolver))


    with(compiler) {
      selected.compile().toString() shouldBe "SELECT hh.id, hh.name, hh.family\nFROM human AS hh"
    }
  }
}) {
  private data class Human(val id: Long, val name: String, val family: String)
  private object MockTableResolver : TableResolver {
    override fun resolveTableName(alias: Alias<*>): Table<*> =
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
          Lens.on<Int, Long>("id"),
          SqlType.Bigint,
          SqlValue.Bigint.transformer
        )
      )
  }
}
