package com.github.speky.postgres.compiler

import com.github.speky.core.Lens
import com.github.speky.core.PropertyRef
import com.github.speky.core.specification.Alias
import com.github.speky.core.specification.Order
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
