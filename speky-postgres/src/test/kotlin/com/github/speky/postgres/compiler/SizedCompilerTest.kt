package com.github.speky.postgres.compiler

import com.github.speky.core.Lens
import com.github.speky.core.PropertyRef
import com.github.speky.core.specification.Alias
import com.github.speky.core.specification.Order
import com.github.speky.core.specification.Specification
import com.github.speky.core.table.*
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
    override fun resolveTableName(alias: Alias<*>): Table<*> =
      object : Table<Any>(alias.classRef.name.lowercase()) {
        override fun constructorRef(): ConstructorRef<Any> {
          TODO("Not yet implemented")
        }
      }
  }

  private object MockColumnResolver : ColumnResolver {
    override fun resolveColumns(prop: PropertyRef<*>): List<Column<*, *, *>> =
      listOf(Column(prop.name, Lens.on<Int, Long>("id"), SqlType.Bigint, SqlValue.Bigint.transformer))
  }
}
