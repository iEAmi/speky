package com.github.speky.compiler.pg

import com.github.speky.compiler.pg.resolve.ColumnResolver
import com.github.speky.compiler.pg.resolve.TableResolver
import com.github.speky.core.Lens
import com.github.speky.core.PropertyRef
import com.github.speky.core.specification.Alias
import com.github.speky.core.specification.Specification
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
  private class Human(val id: Long, val name: String, val family: String)
  private object MockTableResolver : TableResolver {
    override fun resolveTableName(alias: Alias<*>): String = alias.classRef.name.lowercase()
  }

  private object MockColumnResolver : ColumnResolver {
    override fun resolveColumnName(prop: PropertyRef<*>): String = prop.name
  }
}
