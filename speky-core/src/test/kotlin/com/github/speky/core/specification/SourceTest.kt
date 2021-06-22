package com.github.speky.core.specification

import com.github.speky.core.ClassRef
import com.github.speky.core.Lens
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

internal data class Table(val name: String, val id: Long)

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
    val spec = Specification.invoke<Table>("0")

    spec.shouldBeInstanceOf<Source.Single<Table>>()

    val select = spec.select(Lens.on<Table, String>("name"), Lens.on<Table, Long>("id"))

    select.shouldBeInstanceOf<Selected.Some<Table>>()
    select.lenses.size shouldBe 2
    select.lenses shouldContain Lens.on<Table, String>("name")
    select.lenses shouldContain Lens.on<Table, Long>("id")
    (select.alias == Alias.invoke<Table>("0")) shouldBe true
    (select.classRef == ClassRef<Table>()) shouldBe true
    (select.delegate == select.source) shouldBe true
    (select.delegate == spec) shouldBe true
    (select.source == spec) shouldBe true
  }
})
