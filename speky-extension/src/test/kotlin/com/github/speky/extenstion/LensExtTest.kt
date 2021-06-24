package com.github.speky.extenstion

import com.github.speky.core.specification.Value
import com.github.speky.extension.on
import com.github.speky.extension.setTo
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

internal class LensExtTest : FunSpec({
  test("Lens setTo should return same value as Value.of") {
    val to = Human::age.on() setTo 10
    val value = Value.of<Human, Long>("age", 10)

    to.lens shouldBe value.lens
    to.value shouldBe value.value
  }
})
