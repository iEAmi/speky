package com.github.speky.postgres.compiler

import com.github.speky.core.Lens
import com.github.speky.core.PropertyRef
import com.github.speky.core.specification.Alias
import com.github.speky.core.specification.Order
import com.github.speky.core.specification.Specification
import com.github.speky.core.table.*
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

internal class OrderedCompilerTest : FunSpec({
  test("compiling Ordered by Order.Ascending") {
    val ordered = Specification.from<Human>("hh")
      .select()
      .order(Order.asc(Lens.on<Human, String>("name")))
    val compiler = OrderedCompiler(PgSpecificationCompiler(MockTableResolver, MockColumnResolver))

    with(compiler) {
      ordered.compile().toString() shouldBe "SELECT *\nFROM human AS hh\nORDER BY hh.name"
    }
  }

  test("compiling Ordered by Order.Descending") {
    val ordered = Specification.from<Human>("hh")
      .select()
      .order(Order.desc(Lens.on<Human, String>("name")))
    val compiler = OrderedCompiler(PgSpecificationCompiler(MockTableResolver, MockColumnResolver))

    with(compiler) {
      ordered.compile().toString() shouldBe "SELECT *\nFROM human AS hh\nORDER BY hh.name DESC"
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
    override fun resolveColumnName(prop: PropertyRef<*>): Column<*, *, *> =
      Column(prop.name, Lens.on<Int, Long>("id"), SqlType.Bigint, SqlValue.Bigint.transformer)
  }
}
