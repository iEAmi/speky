package com.github.speky.core

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.comparables.shouldBeEqualComparingTo
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldNotContain

internal class ClassRefTest : FunSpec({
  test("name should not contains dot") {
    val ref = ClassRef.of<String>()
    ref.name shouldNotContain "."
  }

  test("qualifiedName should contains dot") {
    val ref = ClassRef.of<String>()
    ref.qualifiedName shouldContain "."
  }

  test("qualifiedName length should be greater that name") {
    val ref = ClassRef.of<Int>()
    ref.qualifiedName.length shouldBeGreaterThan ref.name.length
  }

  test("toString should contains name and qualifiedName") {
    val ref = ClassRef.of<String>()
    ref.toString() shouldContain ref.name
    ref.toString() shouldContain ref.qualifiedName
    ref.toString() shouldBe "ClassRef(name=${ref.name}, qualifiedName=${ref.qualifiedName})"
  }

  test("class should equals to itself") {
    val ref = ClassRef<String>("String", "kotlin.String")
    (ref == ref) shouldBe true
    ref.equals("") shouldBe false

    (ref == ClassRef<String>("String", "kotlin.String")) shouldBe true

    (ref == ClassRef<String>("S", ref.qualifiedName)) shouldBe false
    (ref == ClassRef<String>(ref.name, "dd")) shouldBe false

    ref.hashCode() shouldBeEqualComparingTo ref.hashCode()
    ref.hashCode() shouldBeEqualComparingTo ClassRef<String>(
      ref.name,
      ref.qualifiedName
    ).hashCode()
  }

  test("invoke<String>() should return String CLassRef") {
    val ref = ClassRef.of<String>()

    ref.name shouldBe "String"
    ref.qualifiedName shouldBe "kotlin.String"
  }

  test("show should return name @ qualifiedName") {
    val ref = ClassRef.of<Int>()
    with(ClassRef.show) {
      ref.show() shouldBe "Int @ kotlin.Int"
    }
  }
})
