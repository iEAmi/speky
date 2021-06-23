package com.github.speky.extenstion

import com.github.speky.core.specification.Alias
import com.github.speky.core.specification.Sized
import com.github.speky.extension.desc
import com.github.speky.extension.from
import com.github.speky.extension.gte
import com.github.speky.extension.on
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

private data class Human(val id: Long, val name: String, val age: Long)

internal class ExtensionTest : FunSpec({
  test("write spec with extensions should be easy") {
    val spec = from<Human>("h")
      .select(Human::id.on(), Human::name.on(), Human::age.on())
      .filter(Human::age gte 29)
      .order(Human::age.desc())
      .size(10, 0)

    spec.shouldBeInstanceOf<Sized<Human, Long>>()
    spec.alias shouldBe Alias.of<Human>("h")
  }
})
