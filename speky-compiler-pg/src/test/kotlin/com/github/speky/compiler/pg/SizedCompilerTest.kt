package com.github.speky.compiler.pg

import com.github.speky.compiler.pg.resolve.ColumnResolver
import com.github.speky.compiler.pg.resolve.TableResolver
import com.github.speky.core.Lens
import com.github.speky.core.PropertyRef
import com.github.speky.core.specification.Alias
import com.github.speky.core.specification.Order
import com.github.speky.core.specification.Specification
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

internal class SizedCompilerTest : FunSpec({
  test("compiling Sized") {
    val sized = Specification.from<Human>("hh")
      .select()
      .order(Order.asc(Lens.on<Human, String>("name")))
      .size(10, 0)
    val compiler = SizedCompiler(PgSpecificationCompiler(MockTableResolver, MockColumnResolver))

    with(compiler) {
      sized.compile()
        .toString() shouldBe "SELECT *\nFROM human AS hh\nORDER BY hh.name\nLIMIT 10 OFFSET 0"
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
