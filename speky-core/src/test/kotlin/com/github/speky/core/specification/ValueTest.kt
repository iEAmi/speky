package com.github.speky.core.specification

import com.github.speky.core.Lens
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

internal class ValueTest : FunSpec({
  test("Value.of should return valid Value") {
    val value = Value.of<String, Int>("length", 10)

    value.value shouldBe 10
    value.lens shouldBe Lens.on("length")
  }

  test("Value.show validation") {
    val value = Value.of<String, Int>("length", 10)

    with(Value.show) {
      value.show() shouldBe "String.length = 10"
    }
  }
})
