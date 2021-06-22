package com.github.speky.core.specification

import com.github.speky.core.Lens
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

internal class FilteredTest : FunSpec({
  test("Source.Single.order() should return Ordered") {
    val spec = Specification.from<Int>("0").select()
    val filtered = spec.filter(Filter.neq(Lens.on<Int, String>("name"), "g"))
    val ordered = filtered.order(Order.asc(Lens.on<Int, String>("name")))

    ordered.shouldBeInstanceOf<Ordered<Int, String>>()
    ordered.order.lens shouldBe Order.asc(Lens.on<Int, String>("name")).lens
    ordered.classRef shouldBe spec.classRef
    ordered.delegate shouldBe filtered
    ordered.alias shouldBe spec.alias
  }
})
