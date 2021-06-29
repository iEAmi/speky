package com.github.speky.extenstion

import com.github.speky.core.specification.Alias
import com.github.speky.core.specification.Sink
import com.github.speky.core.specification.Sized
import com.github.speky.core.specification.Value
import com.github.speky.extension.*
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

internal data class Human(val id: Long, val name: String, val age: Long)

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

  test("write insert spec with extensions") {
    val spec = insertOn(
      Human::id.on() setTo 10L,
      Human::name.on() setTo "Foo",
      Human::age.on() setTo 29,
    )

    spec.shouldBeInstanceOf<Sink.Insert<Human>>()
    spec.values shouldHaveSize 3
    spec.values shouldContain Value.of("id", 10L)
    spec.values shouldContain Value.of("name", "Foo")
    spec.values shouldContain Value.of("age", 29L)

    val specV2 = insertOn(
      Human::id setTo 10L,
      Human::name setTo "Foo",
      Human::age setTo 29,
    )

    spec.alias shouldBe specV2.alias
    spec.values shouldContainAll specV2.values
  }
})
