package com.github.speky.core.specification

import com.github.speky.core.ClassRef
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

internal class AliasTest : FunSpec({
  test("Alias.Invoke(String) should return Alias.Single") {
    val al = Alias.invoke<Int>("nam")

    al.shouldBeInstanceOf<Alias.Single<Int>>()
    al.value shouldBe "nam"
    (al.classRef == ClassRef<Int>()) shouldBe true
  }

  test("Alias.Invoke() with default value should return Alias.Single") {
    val al = Alias.invoke<Int>()

    al.shouldBeInstanceOf<Alias.Single<Int>>()
    al.value shouldBe "int"
    (al.classRef == ClassRef<Int>()) shouldBe true
  }

  test("Alias.invoke(Alias, Alias) should return Alias.Multiply") {
    val alias1 = Alias.invoke<Int>("1")
    val alias2 = Alias.invoke<String>("2")

    val al = Alias.invoke<Int, String, Long>(alias1, alias2)

    al.shouldBeInstanceOf<Alias.Multiply<Int, String, Long>>()
    al.left.shouldBeInstanceOf<Alias.Single<Int>>()
    al.right.shouldBeInstanceOf<Alias.Single<String>>()

    (al.left as Alias.Single<Int>).value shouldBe "1"
    (al.right as Alias.Single<String>).value shouldBe "2"

    (al.left.classRef == ClassRef<Int>()) shouldBe true
    (al.right.classRef == ClassRef<String>()) shouldBe true

    (al.classRef == ClassRef<Long>()) shouldBe true
  }

  test("Alias.show for Alias.Single") {
    val al = Alias.invoke<Int>("nam")

    with(Alias.show) {
      al.show() shouldBe "Int as nam"
    }
  }

  test("Alias.show for Alias.Multiply") {
    val alias1 = Alias.invoke<Int>("1")
    val alias2 = Alias.invoke<String>("2")

    val alias3 = Alias.invoke<Int, String, Long>(alias1, alias2)

    with(Alias.show) {
      alias3.show() shouldBe "Int as 1 & String as 2"
    }

    val al = Alias.invoke<Long, Short, Boolean>(alias3, Alias.invoke("3"))

    with(Alias.show) {
      al.show() shouldBe "Int as 1 & String as 2 & Short as 3"
    }
  }

  test("Alias.Single equals and hashCode") {
    val al = Alias.invoke<Int>("nam")

    (al == al) shouldBe true
    (al.hashCode() == al.hashCode()) shouldBe true

    (al == ClassRef<Int>()) shouldBe false

    (al == Alias.invoke<Int>("nam")) shouldBe true
    (al.hashCode() == Alias.invoke<Int>("nam").hashCode()) shouldBe true

    (al == Alias.invoke<Int>("name")) shouldBe false
    (al.hashCode() == Alias.invoke<Int>("name").hashCode()) shouldBe false

    (al == Alias.invoke<String>("nam")) shouldBe false
    (al.hashCode() == Alias.invoke<String>("nam").hashCode()) shouldBe false
  }

  test("Alias.Multiply equals and hashCode") {
    val alias1 = Alias.invoke<Int>("1")
    val alias2 = Alias.invoke<String>("2")

    val al = Alias.invoke<Int, String, Long>(alias1, alias2)

    (al == al) shouldBe true
    (al.hashCode() == al.hashCode()) shouldBe true

    (al == Alias.invoke<Int, String, Long>(Alias.invoke("1"), Alias.invoke("2"))) shouldBe true

    (al == ClassRef<Int>()) shouldBe false

    (al == alias1) shouldBe false
    (al == alias2) shouldBe false

    (al == Alias.invoke<Int, Int, String>(Alias.invoke("1"), Alias.invoke("1"))) shouldBe false
    (al == Alias.invoke<Long, String, Long>(Alias.invoke("2"), Alias.invoke("1"))) shouldBe false
    (al == Alias.invoke<Int, String, Long>(Alias.invoke("2"), Alias.invoke("3"))) shouldBe false

    (al == Alias.invoke<Int, String, Long>(Alias.invoke("1"), Alias.invoke("22"))) shouldBe false
    (al == Alias.invoke<Int, String, Long>(Alias.invoke("11"), Alias.invoke("22"))) shouldBe false

    (al == Alias.invoke<Int>("name")) shouldBe false
    (al.hashCode() == Alias.invoke<Int>("name").hashCode()) shouldBe false

    (al == Alias.invoke<String>("nam")) shouldBe false
    (al.hashCode() == Alias.invoke<String>("nam").hashCode()) shouldBe false
  }
})
