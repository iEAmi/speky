package com.github.speky.extenstion

import com.github.speky.core.Lens
import com.github.speky.core.specification.Filter
import com.github.speky.core.specification.Order
import com.github.speky.core.specification.Value
import com.github.speky.extension.asc
import com.github.speky.extension.contains
import com.github.speky.extension.desc
import com.github.speky.extension.endsWith
import com.github.speky.extension.eq
import com.github.speky.extension.gt
import com.github.speky.extension.gte
import com.github.speky.extension.like
import com.github.speky.extension.lt
import com.github.speky.extension.lte
import com.github.speky.extension.neq
import com.github.speky.extension.on
import com.github.speky.extension.setTo
import com.github.speky.extension.startsWith
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

private data class Person(val id: Long, val name: String)

internal class KPropertyExtTest : FunSpec({
  test("KProperty1.on() should return same value as Lens.on()") {
    Person::id.on() shouldBe Lens.on("id")
  }

  test("KProperty1.asc() should return same value as Order.asc()") {
    val asc = Person::id.asc()
    val order = Order.asc(Lens.on<Person, Long>("id"))

    asc.shouldBeInstanceOf<Order.Ascending<Person, Long>>()
    asc.lens shouldBe order.lens
  }

  test("KProperty1.desc() should return same value as Order.desc()") {
    val desc = Person::id.desc()
    val order = Order.desc(Lens.on<Person, Long>("id"))

    desc.shouldBeInstanceOf<Order.Descending<Person, Long>>()
    desc.lens shouldBe order.lens
  }

  test("KProperty1.eq() should return same value as Filter.eq()") {
    val eq = Person::id.eq(1)
    val filter = Filter.eq(Lens.on<Person, Long>("id"), 1)

    eq.shouldBeInstanceOf<Filter.Equal<Person, Long>>()
    eq.value shouldBe filter.value
    eq.lens shouldBe filter.lens
  }

  test("KProperty1.neq() should return same value as Filter.neq()") {
    val neq = Person::id.neq(12)
    val filter = Filter.neq(Lens.on<Person, Long>("id"), 12)

    neq.shouldBeInstanceOf<Filter.NotEqual<Person, Long>>()
    neq.value shouldBe filter.value
    neq.lens shouldBe filter.lens
  }

  test("KProperty1.gt() should return same value as Filter.gt()") {
    val gt = Person::id.gt(22)
    val filter = Filter.gt(Lens.on<Person, Long>("id"), 22)

    gt.shouldBeInstanceOf<Filter.GreaterThan<Person, Long>>()
    gt.value shouldBe filter.value
    gt.lens shouldBe filter.lens
  }

  test("KProperty1.gte() should return same value as Filter.gte()") {
    val gte = Person::id.gte(33)
    val filter = Filter.gte(Lens.on<Person, Long>("id"), 33)

    gte.shouldBeInstanceOf<Filter.GreaterThanEqual<Person, Long>>()
    gte.value shouldBe filter.value
    gte.lens shouldBe filter.lens
  }

  test("KProperty1.lt() should return same value as Filter.lt()") {
    val lt = Person::id.lt(34)
    val filter = Filter.lt(Lens.on<Person, Long>("id"), 34)

    lt.shouldBeInstanceOf<Filter.LessThan<Person, Long>>()
    lt.value shouldBe filter.value
    lt.lens shouldBe filter.lens
  }

  test("KProperty1.lte() should return same value as Filter.lte()") {
    val lte = Person::id.lte(32)
    val filter = Filter.lte(Lens.on<Person, Long>("id"), 32)

    lte.shouldBeInstanceOf<Filter.LessThanEqual<Person, Long>>()
    lte.value shouldBe filter.value
    lte.lens shouldBe filter.lens
  }

  test("KProperty1.like() should return same value as Filter.like()") {
    val like = Person::name.like("Foo")
    val filter = Filter.like(Lens.on<Person, String>("name"), "Foo")

    like.shouldBeInstanceOf<Filter.Like<Person>>()
    like.value shouldBe filter.value
    like.lens shouldBe filter.lens
  }

  test("KProperty1.contains() should return same value as Filter.contains()") {
    val contains = Person::name.contains("Baz")
    val filter = Filter.contains(Lens.on<Person, String>("name"), "Baz")

    contains.shouldBeInstanceOf<Filter.Contains<Person>>()
    contains.value shouldBe filter.value
    contains.lens shouldBe filter.lens
  }

  test("KProperty1.startsWith() should return same value as Filter.startsWith()") {
    val startsWith = Person::name.startsWith("A")
    val filter = Filter.startsWith(Lens.on<Person, String>("name"), "A")

    startsWith.shouldBeInstanceOf<Filter.StartsWith<Person>>()
    startsWith.value shouldBe filter.value
    startsWith.lens shouldBe filter.lens
  }

  test("KProperty1.endsWith() should return same value as Filter.endsWith()") {
    val endsWith = Person::name.endsWith("dot")
    val filter = Filter.endsWith(Lens.on<Person, String>("name"), "dot")

    endsWith.shouldBeInstanceOf<Filter.EndsWith<Person>>()
    endsWith.value shouldBe filter.value
    endsWith.lens shouldBe filter.lens
  }

  test("KProperty1.setTo() should return same value as Value.of") {
    val setTo = Person::name setTo "Foo"
    val value = Value.of<Person, String>("name", "Foo")

    setTo.value shouldBe value.value
    setTo.lens shouldBe value.lens
  }
})
