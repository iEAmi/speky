package com.github.speky.core.specification

import com.github.speky.core.Lens
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

internal class OrderTest : FunSpec({
  test("Order.asc() should return Order.Ascending") {
    val lens = Lens.on<String, Int>("a")
    val order = Order.asc(lens)

    order.shouldBeInstanceOf<Order.Ascending<String, Int>>()
    order.lens shouldBe lens
    order.lens.propertyRef shouldBe lens.propertyRef
    order.lens.declaringClassRef shouldBe lens.declaringClassRef
  }

  test("Order.desc() should return Order.Descending") {
    val lens = Lens.on<String, Int>("a")
    val order = Order.desc(lens)

    order.shouldBeInstanceOf<Order.Descending<String, Int>>()
    order.lens shouldBe lens
    order.lens.propertyRef shouldBe lens.propertyRef
    order.lens.declaringClassRef shouldBe lens.declaringClassRef
  }

  test("Order.show on Order.Ascending") {
    val lens = Lens.on<String, Int>("a")
    val order = Order.asc(lens)

    with(Order.show) { order.show() shouldBe "order by String.a asc" }
  }

  test("Order.show on Order.Descending") {
    val lens = Lens.on<String, Int>("a")
    val order = Order.desc(lens)

    with(Order.show) { order.show() shouldBe "order by String.a desc" }
  }
})
