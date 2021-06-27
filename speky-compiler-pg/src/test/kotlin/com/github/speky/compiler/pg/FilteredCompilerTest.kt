package com.github.speky.compiler.pg

import com.github.speky.compiler.pg.resolve.ColumnResolver
import com.github.speky.compiler.pg.resolve.TableResolver
import com.github.speky.core.Lens
import com.github.speky.core.PropertyRef
import com.github.speky.core.specification.Alias
import com.github.speky.core.specification.Filter
import com.github.speky.core.specification.Specification
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

internal class FilteredCompilerTest : FunSpec({
  test("compiling Filtered by Filter.Equal") {
    val filtered = Specification.from<Human>("hh")
      .select()
      .filter(Filter.eq(Lens.on("name"), "Foo"))
    val compiler = FilteredCompiler(PgSpecificationCompiler(MockTableResolver, MockColumnResolver))

    with(compiler) {
      filtered.compile().toString() shouldBe "SELECT *\nFROM human AS hh\nWHERE hh.name = 'Foo'"
    }
  }

  test("compiling Filtered by Filter.NotEqual") {
    val filtered = Specification.from<Human>("hh")
      .select()
      .filter(Filter.neq(Lens.on("name"), "Foo"))
    val compiler = FilteredCompiler(PgSpecificationCompiler(MockTableResolver, MockColumnResolver))

    with(compiler) {
      filtered.compile().toString() shouldBe "SELECT *\nFROM human AS hh\nWHERE hh.name != 'Foo'"
    }
  }

  test("compiling Filtered by Filter.GreaterThan") {
    val filtered = Specification.from<Human>("hh")
      .select()
      .filter(Filter.gt(Lens.on("id"), 10L))
    val compiler = FilteredCompiler(PgSpecificationCompiler(MockTableResolver, MockColumnResolver))

    with(compiler) {
      filtered.compile().toString() shouldBe "SELECT *\nFROM human AS hh\nWHERE hh.id > 10"
    }
  }

  test("compiling Filtered by Filter.GreaterThanEqual") {
    val filtered = Specification.from<Human>("hh")
      .select()
      .filter(Filter.gte(Lens.on("id"), 10L))
    val compiler = FilteredCompiler(PgSpecificationCompiler(MockTableResolver, MockColumnResolver))

    with(compiler) {
      filtered.compile().toString() shouldBe "SELECT *\nFROM human AS hh\nWHERE hh.id >= 10"
    }
  }

  test("compiling Filtered by Filter.LessThan") {
    val filtered = Specification.from<Human>("hh")
      .select()
      .filter(Filter.lt(Lens.on("id"), 10L))
    val compiler = FilteredCompiler(PgSpecificationCompiler(MockTableResolver, MockColumnResolver))

    with(compiler) {
      filtered.compile().toString() shouldBe "SELECT *\nFROM human AS hh\nWHERE hh.id < 10"
    }
  }

  test("compiling Filtered by Filter.LessThanEqual") {
    val filtered = Specification.from<Human>("hh")
      .select()
      .filter(Filter.lte(Lens.on("name"), "Baz"))
    val compiler = FilteredCompiler(PgSpecificationCompiler(MockTableResolver, MockColumnResolver))

    with(compiler) {
      filtered.compile().toString() shouldBe "SELECT *\nFROM human AS hh\nWHERE hh.name <= 'Baz'"
    }
  }

  test("compiling Filtered by Filter.Like") {
    val filtered = Specification.from<Human>("hh")
      .select()
      .filter(Filter.like(Lens.on<Human, String>("name"), "Fo"))
    val compiler = FilteredCompiler(PgSpecificationCompiler(MockTableResolver, MockColumnResolver))

    with(compiler) {
      filtered.compile().toString() shouldBe "SELECT *\nFROM human AS hh\nWHERE hh.name LIKE '%Fo%'"
    }
  }

  test("compiling Filtered by Filter.Contains") {
    val filtered = Specification.from<Human>("hh")
      .select()
      .filter(Filter.contains(Lens.on("name"), "Fo"))
    val compiler = FilteredCompiler(PgSpecificationCompiler(MockTableResolver, MockColumnResolver))

    with(compiler) {
      filtered.compile().toString() shouldBe "SELECT *\nFROM human AS hh\nWHERE hh.name LIKE '%Fo%'"
    }
  }

  test("compiling Filtered by Filter.StartsWith") {
    val filtered = Specification.from<Human>("hh")
      .select()
      .filter(Filter.startsWith(Lens.on("name"), "Fo"))
    val compiler = FilteredCompiler(PgSpecificationCompiler(MockTableResolver, MockColumnResolver))

    with(compiler) {
      filtered.compile().toString() shouldBe "SELECT *\nFROM human AS hh\nWHERE hh.name LIKE 'Fo%'"
    }
  }

  test("compiling Filtered by Filter.EndsWith") {
    val filtered = Specification.from<Human>("hh")
      .select()
      .filter(Filter.endsWith(Lens.on("name"), "Fo"))
    val compiler = FilteredCompiler(PgSpecificationCompiler(MockTableResolver, MockColumnResolver))

    with(compiler) {
      filtered.compile().toString() shouldBe "SELECT *\nFROM human AS hh\nWHERE hh.name LIKE '%Fo'"
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
