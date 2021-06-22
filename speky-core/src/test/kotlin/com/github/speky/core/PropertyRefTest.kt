package com.github.speky.core

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.comparables.shouldBeEqualComparingTo
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain

internal class PropertyRefTest : FunSpec({
  test("invoke() should throw exception with empty name") {
    shouldThrow<IllegalArgumentException> {
      PropertyRef.of<String>("", ClassRef<String>("Name", "a"))
    }
  }

  test("invoke() should throw exception with blank name") {
    shouldThrow<IllegalArgumentException> {
      PropertyRef.of<String>("  ", ClassRef<String>("Name", "a"))
    }
  }

  test("toString should contains all properties") {
    val ref = PropertyRef.of<String>("firstName", ClassRef.of<String>())
    ref.toString() shouldContain ref.name
    ref.toString() shouldContain ref.classRef.toString()
    ref.toString() shouldContain ref.declaringClassRef.toString()
  }

  test("equals and hashCode") {
    val ref = PropertyRef("family", ClassRef.of<String>(), ClassRef.of<Any>())
    (ref == ref) shouldBe true
    ref.equals("") shouldBe false

    (ref == PropertyRef.of<String>("family", ClassRef.of<Any>())) shouldBe true
    (ref == PropertyRef.of<String>("lastName", ClassRef.of<Any>())) shouldBe false
    (ref == PropertyRef.of<String>("family", ClassRef.of<Int>())) shouldBe false

    ref.hashCode() shouldBeEqualComparingTo ref.hashCode()
    ref.hashCode() shouldBeEqualComparingTo PropertyRef.of<String>(
      "family",
      ClassRef.of<Any>()
    ).hashCode()
  }

  test("show should return declaringClass.name") {
    val ref = PropertyRef.of<String>("firstName", ClassRef.of<String>())

    with(PropertyRef.show) {
      ref.show() shouldBe "String.firstName"
    }
  }
})
