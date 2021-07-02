package com.github.speky.postgres.compiler

import com.github.speky.core.Lens
import com.github.speky.core.PropertyRef
import com.github.speky.core.specification.Alias
import com.github.speky.core.specification.Sink
import com.github.speky.core.specification.Value
import com.github.speky.core.table.Column
import com.github.speky.core.table.ColumnResolver
import com.github.speky.core.table.ConstructorRef
import com.github.speky.core.table.SqlType
import com.github.speky.core.table.SqlValue
import com.github.speky.core.table.Table
import com.github.speky.core.table.TableResolver
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

internal class SinkCompilerTest : FunSpec({
  test("compiling Sink.Insert") {
    val sink = Sink.insert<Human>(
      listOf(
        Value.of("id", 1L),
        Value.of("name", "Foo"),
        Value.of("family", "Baz")
      )
    )
    val compiler = SinkCompiler(PgSpecificationCompiler(MockTableResolver, MockColumnResolver))

    with(compiler) {
      sink.compile().toString() shouldBe
          "INSERT INTO human (id, name, family)\nVALUES (1, 'Foo', 'Baz')"
    }
  }
}) {
  private data class Human(val id: Long, val name: String, val family: String)
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
          Lens.on<Int, Long>("id"),
          SqlType.Bigint,
          SqlValue.Bigint.transformer
        )
      )
  }
}
