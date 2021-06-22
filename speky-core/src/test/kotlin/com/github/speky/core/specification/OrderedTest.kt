package com.github.speky.core.specification

import com.github.speky.core.Lens
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

internal class OrderedTest : FunSpec({
  test("Ordered.size() should return Sized") {
    val spec = Ordered(Specification.from("A"), Order.asc(Lens.on<Int, String>("name")))
    val sized = spec.size(10, 11)

    sized.shouldBeInstanceOf<Sized<Int, String>>()
    sized.limit shouldBe 10
    sized.offset shouldBe 11
    sized.delegate shouldBe spec
    sized.alias shouldBe spec.alias
    sized.classRef shouldBe spec.classRef
  }
})
