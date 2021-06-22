package com.github.speky.core.specification

import com.github.speky.core.Lens
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

internal class FilterTest : FunSpec({
  test("Filter.eq() should return Filter.Equal") {
    val lens = Lens.on<String, Int>("a")
    val filter = Filter.eq(lens, 12)

    filter.shouldBeInstanceOf<Filter.Equal<String, Int>>()
    filter.value shouldBe 12
    filter.lens shouldBe lens
    filter.lens.propertyRef shouldBe lens.propertyRef
    filter.lens.declaringClassRef shouldBe lens.declaringClassRef
  }

  test("Filter.neq() should return Filter.NotEqual") {
    val lens = Lens.on<String, Int>("b")
    val filter = Filter.neq(lens, 12)

    filter.shouldBeInstanceOf<Filter.NotEqual<String, Int>>()
    filter.value shouldBe 12
    filter.lens shouldBe lens
    filter.lens.propertyRef shouldBe lens.propertyRef
    filter.lens.declaringClassRef shouldBe lens.declaringClassRef
  }

  test("Filter.gt() should return Filter.GreaterThan") {
    val lens = Lens.on<String, Int>("c")
    val filter = Filter.gt(lens, 12)

    filter.shouldBeInstanceOf<Filter.GreaterThan<String, Int>>()
    filter.value shouldBe 12
    filter.lens shouldBe lens
    filter.lens.propertyRef shouldBe lens.propertyRef
    filter.lens.declaringClassRef shouldBe lens.declaringClassRef
  }

  test("Filter.gte() should return Filter.GreaterThanEqual") {
    val lens = Lens.on<String, Int>("d")
    val filter = Filter.gte(lens, 12)

    filter.shouldBeInstanceOf<Filter.GreaterThanEqual<String, Int>>()
    filter.value shouldBe 12
    filter.lens shouldBe lens
    filter.lens.propertyRef shouldBe lens.propertyRef
    filter.lens.declaringClassRef shouldBe lens.declaringClassRef
  }

  test("Filter.lt() should return Filter.LessThan") {
    val lens = Lens.on<String, Int>("e")
    val filter = Filter.lt(lens, 12)

    filter.shouldBeInstanceOf<Filter.LessThan<String, Int>>()
    filter.value shouldBe 12
    filter.lens shouldBe lens
    filter.lens.propertyRef shouldBe lens.propertyRef
    filter.lens.declaringClassRef shouldBe lens.declaringClassRef
  }

  test("Filter.lte() should return Filter.LessThanEqual") {
    val lens = Lens.on<String, Int>("f")
    val filter = Filter.lte(lens, 12)

    filter.shouldBeInstanceOf<Filter.LessThanEqual<String, Int>>()
    filter.value shouldBe 12
    filter.lens shouldBe lens
    filter.lens.propertyRef shouldBe lens.propertyRef
    filter.lens.declaringClassRef shouldBe lens.declaringClassRef
  }

  test("Filter.like() should return Filter.Like") {
    val lens = Lens.on<String, String>("g")
    val filter = Filter.like(lens, "d")

    filter.shouldBeInstanceOf<Filter.Like<String>>()
    filter.value shouldBe "d"
    filter.lens shouldBe lens
    filter.lens.propertyRef shouldBe lens.propertyRef
    filter.lens.declaringClassRef shouldBe lens.declaringClassRef
  }

  test("Filter.contains() should return Filter.Contains") {
    val lens = Lens.on<String, String>("h")
    val filter = Filter.contains(lens, "foo")

    filter.shouldBeInstanceOf<Filter.Contains<String>>()
    filter.value shouldBe "foo"
    filter.lens shouldBe lens
    filter.lens.propertyRef shouldBe lens.propertyRef
    filter.lens.declaringClassRef shouldBe lens.declaringClassRef
  }

  test("Filter.startsWith() should return Filter.StartsWith") {
    val lens = Lens.on<String, String>("i")
    val filter = Filter.startsWith(lens, "baz")

    filter.shouldBeInstanceOf<Filter.StartsWith<String>>()
    filter.value shouldBe "baz"
    filter.lens shouldBe lens
    filter.lens.propertyRef shouldBe lens.propertyRef
    filter.lens.declaringClassRef shouldBe lens.declaringClassRef
  }

  test("Filter.endsWith() should return Filter.EndsWith") {
    val lens = Lens.on<String, String>("j")
    val filter = Filter.endsWith(lens, "fii")

    filter.shouldBeInstanceOf<Filter.EndsWith<String>>()
    filter.value shouldBe "fii"
    filter.lens shouldBe lens
    filter.lens.propertyRef shouldBe lens.propertyRef
    filter.lens.declaringClassRef shouldBe lens.declaringClassRef
  }

  test("Filter.show on Filter.Equal") {
    val lens = Lens.on<String, Int>("length")
    val filter = Filter.eq(lens, 12)

    with(Filter.show) { filter.show() shouldBe "String.length = 12" }
  }

  test("Filter.show on Filter.NotEqual") {
    val lens = Lens.on<String, Int>("length")
    val filter = Filter.neq(lens, 12)

    with(Filter.show) { filter.show() shouldBe "String.length != 12" }
  }

  test("Filter.show on Filter.GreaterThan") {
    val lens = Lens.on<String, Int>("length")
    val filter = Filter.gt(lens, 12)

    with(Filter.show) { filter.show() shouldBe "String.length > 12" }
  }

  test("Filter.show on Filter.GreaterThanEqual") {
    val lens = Lens.on<String, Int>("length")
    val filter = Filter.gte(lens, 12)

    with(Filter.show) { filter.show() shouldBe "String.length >= 12" }
  }

  test("Filter.show on Filter.LessThan") {
    val lens = Lens.on<String, Int>("length")
    val filter = Filter.lt(lens, 12)

    with(Filter.show) { filter.show() shouldBe "String.length < 12" }
  }

  test("Filter.show on Filter.LessThanEqual") {
    val lens = Lens.on<String, Int>("length")
    val filter = Filter.lte(lens, 12)

    with(Filter.show) { filter.show() shouldBe "String.length <= 12" }
  }

  test("Filter.show on Filter.Like") {
    val lens = Lens.on<String, String>("length")
    val filter = Filter.like(lens, "d")

    with(Filter.show) { filter.show() shouldBe "String.length like '%d%'" }
  }

  test("Filter.show on Filter.Contains") {
    val lens = Lens.on<String, String>("length")
    val filter = Filter.contains(lens, "foo")

    with(Filter.show) { filter.show() shouldBe "String.length like '%foo%'" }
  }

  test("Filter.show on Filter.StartsWith") {
    val lens = Lens.on<String, String>("length")
    val filter = Filter.startsWith(lens, "baz")

    with(Filter.show) { filter.show() shouldBe "String.length like 'baz%'" }
  }

  test("Filter.show on Filter.EndsWith") {
    val lens = Lens.on<String, String>("length")
    val filter = Filter.endsWith(lens, "fii")

    with(Filter.show) { filter.show() shouldBe "String.length like '%fii'" }
  }
})
