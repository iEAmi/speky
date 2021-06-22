package com.github.speky.core

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

internal class LensTest : FunSpec({
  test("Lens.on() should return Focus") {
    val lens = Lens.on<String, Int>("name")
    lens.shouldBeInstanceOf<Lens.Focus<String, Int>>()
  }

  test("declaringClassRef should be equals to property declaringClassRef") {
    val lens = Lens.Focus<String, Int>(PropertyRef.invoke("family", ClassRef<String>()))
    lens.declaringClassRef shouldBe lens.propertyRef.declaringClassRef
  }

  test("Lens.on() should return valid Lens") {
    val lens = Lens.on<String, Int>("name")
    lens.shouldBeInstanceOf<Lens.Focus<String, Int>>()

    lens.propertyRef shouldBe PropertyRef.invoke<Int>("name", ClassRef<String>())
    lens.propertyRef.name shouldBe "name"
    lens.propertyRef.classRef shouldBe ClassRef<Int>()
    lens.declaringClassRef shouldBe ClassRef<String>()

    lens shouldBe Lens.Focus(PropertyRef.invoke<Int>("name", ClassRef<String>()))
  }

  test("Lens.Focus equals and hashCode") {
    val lens = Lens.on<String, Int>("name")

    (lens == lens) shouldBe true
    (lens.hashCode() == lens.hashCode()) shouldBe true

    (lens == Lens.on<String, Int>("name")) shouldBe true

    (lens == ClassRef<String>()) shouldBe false
    lens.equals("") shouldBe false

    (lens == Lens.on<String, Int>("family")) shouldBe false
    (lens == Lens.on<Int, Int>("name")) shouldBe false
    (lens == Lens.on<String, Long>("name")) shouldBe false

    (lens == Lens.Focus<Int, String>(PropertyRef("name", ClassRef<String>()))) shouldBe true
    (lens.hashCode() == Lens.Focus<Int, String>(PropertyRef("name", ClassRef<String>()))
      .hashCode()) shouldBe true

    (lens.hashCode() == PropertyRef<Int>("name", ClassRef<String>()).hashCode()) shouldBe true

    run {
      val lensName = Lens.on<String, Int>("name")
      val lensSize = Lens.on<Int, Long>("Size")

      val zoomed = lensName zoom lensSize

      (lens == zoomed) shouldBe false
    }
  }

  test("Lens.Zoom equals and hashCode") {
    val lensName = Lens.on<String, Int>("name")
    val lensSize = Lens.on<Int, Long>("Size")

    val lens = lensName zoom lensSize

    lens.propertyRef shouldBe lensSize.propertyRef

    (lens == lens) shouldBe true
    (lens.hashCode() == lens.hashCode()) shouldBe true

    (lens == Lens.on<String, Int>("name").zoom(Lens.on<Int, Long>("Size"))) shouldBe true
    (lens == Lens.on<Int, Long>("Size").zoom(Lens.on<Long, Int>("name"))) shouldBe false

    (lens == ClassRef<String>()) shouldBe false
    (lens.hashCode() == ClassRef<String>().hashCode()) shouldBe false
    (lens == lensName) shouldBe false
    (lens.hashCode() == lensName.hashCode()) shouldBe false
    (lens == lensSize) shouldBe false
    (lens.hashCode() == lensSize.hashCode()) shouldBe false

    (lens == Lens.on<String, Int>("family").zoom(Lens.on<Int, Long>("Size"))) shouldBe false
    (lens == Lens.on<String, Int>("name").zoom(Lens.on<Int, Long>("family"))) shouldBe false
    (lens == Lens.on<Int, Int>("name").zoom(Lens.on<Int, Long>("family"))) shouldBe false

    (lens == Lens.on<Long, Int>("name").zoom(Lens.on<Int, Long>("Size"))) shouldBe false
    (lens == Lens.on<Long, Int>("name").zoom(Lens.on<Int, Int>("Size"))) shouldBe false
  }

  test("zoom") {
    val lensName = Lens.on<String, Int>("name")
    val lensSize = Lens.on<Int, Long>("Size")

    val lens = lensName zoom lensSize

    lens.shouldBeInstanceOf<Lens.Zoom<Long, Int, String>>()

    lens.propertyRef.name shouldBe "Size"
    lens.propertyRef.classRef shouldBe ClassRef("Long", "kotlin.Long")
    lens.propertyRef.declaringClassRef shouldBe ClassRef<Int>("Int", "kotlin.Int")

    lens.declaringClassRef shouldBe ClassRef("Int", "kotlin.Int")
  }

  test("show") {
    val lensName = Lens.on<String, Int>("name")

    with(Lens.show) {
      lensName.show() shouldBe "String.name"
    }

    val lensSize = Lens.on<Int, Long>("size")

    val lens = lensName zoom lensSize
    with(Lens.show) {
      lens.show() shouldBe "String.name.size"
    }
  }
})
