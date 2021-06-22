package com.github.speky.core.specification

import com.github.speky.core.ClassRef
import com.github.speky.core.Lens
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

internal data class Table1(val name: String, val id: Long)
internal data class Table2(val name: String, val id: Long)
internal data class Table3(val name: String, val id: Long)

internal class SourceTest : FunSpec({

  test("Source.Single select()") {
    val spec = Specification.invoke<Int>("0")

    spec.shouldBeInstanceOf<Source.Single<Int>>()

    val select = spec.select()

    select.shouldBeInstanceOf<Selected.All<Int>>()
    (select.alias == Alias.invoke<Int>("0")) shouldBe true
    (select.classRef == ClassRef<Int>()) shouldBe true
    (select.delegate == select.source) shouldBe true
    (select.delegate == spec) shouldBe true
    (select.source == spec) shouldBe true
  }

  test("Source.Single select(lenses)") {
    val spec = Specification.invoke<Table1>("0")

    spec.shouldBeInstanceOf<Source.Single<Table1>>()

    val select = spec.select(Lens.on<Table1, String>("name"), Lens.on<Table1, Long>("id"))

    select.shouldBeInstanceOf<Selected.Some<Table1>>()
    select.lenses.size shouldBe 2
    select.lenses shouldContain Lens.on<Table1, String>("name")
    select.lenses shouldContain Lens.on<Table1, Long>("id")
    (select.alias == Alias.invoke<Table1>("0")) shouldBe true
    (select.classRef == ClassRef<Table1>()) shouldBe true
    (select.delegate == select.source) shouldBe true
    (select.delegate == spec) shouldBe true
    (select.source == spec) shouldBe true
  }

  test("Source.Single.crossJoin() should return Mix.CrossJoin") {
    val spec: Source.Single<Int> = Specification.invoke("int")

    val crossJoin = spec.crossJoin<Long, String>("long")

    crossJoin.shouldBeInstanceOf<Source.Mix.CrossJoin<Int, Long, String>>()

    crossJoin.alias shouldBe Alias.invoke(Alias.invoke("int"), Alias.invoke("long"))
    crossJoin.classRef shouldBe ClassRef("String", "kotlin.String")

    crossJoin.select().shouldBeInstanceOf<Selected.All<String>>()
  }

  test("Source.Single.and() should return Mix.Multiply") {
    val spec: Source.Single<Int> = Specification.invoke("int")

    val and = spec.and<Long, String>("long")

    and.shouldBeInstanceOf<Source.Mix.Multiply<Int, Long, String>>()

    and.alias shouldBe Alias.invoke(Alias.invoke("int"), Alias.invoke("long"))
    and.classRef shouldBe ClassRef("String", "kotlin.String")

    and.select().shouldBeInstanceOf<Selected.All<String>>()
  }

  test("Source.Single.innerJoin() should return Mix.InneJoin") {
    val spec: Source.Single<Table1> = Specification.invoke("int")

    val and = spec.innerJoin<Table2, Table3, String>("long", Lens.on("name"), Lens.on("name"))

    and.shouldBeInstanceOf<Source.Mix.InnerJoin<Table1, Table2, Table3, String>>()

    and.leftSelector shouldBe Lens.on("name")
    and.rightSelector shouldBe Lens.on("name")
    and.alias shouldBe Alias.invoke(Alias.invoke("int"), Alias.invoke("long"))
    and.classRef shouldBe ClassRef("Table3", "com.github.speky.core.specification.Table3")

    and.select().shouldBeInstanceOf<Selected.All<String>>()
  }
})
