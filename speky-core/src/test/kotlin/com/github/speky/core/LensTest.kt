package com.github.speky.core

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeInstanceOf

internal class LensTest : FunSpec({
  test("Lens.on() should return Focus") {
    val lens = Lens.on<String, Int>("name")
    lens.shouldBeInstanceOf<Lens.Focus<String, Int>>()
  }

  test("declaringClassRef should be equals to property declaringClassRef") {
    val lens = Lens.Focus<String, Int>(PropertyRef.of("family", ClassRef.of<String>()))
    lens.declaringClassRef shouldBe lens.propertyRef.declaringClassRef
  }

  test("Lens.on() should return valid Lens") {
    val lens = Lens.on<String, Int>("name")
    lens.shouldBeInstanceOf<Lens.Focus<String, Int>>()

    lens.propertyRef shouldBe PropertyRef.of<Int>("name", ClassRef.of<String>())
    lens.propertyRef.name shouldBe "name"
    lens.propertyRef.classRef shouldBe ClassRef.of<Int>()
    lens.declaringClassRef shouldBe ClassRef.of<String>()

    lens shouldBe Lens.Focus(PropertyRef.of<Int>("name", ClassRef.of<String>()))
  }

  test("Lens.Focus equals and hashCode") {
    val lens = Lens.on<String, Int>("name")

    (lens == lens) shouldBe true
    (lens.hashCode() == lens.hashCode()) shouldBe true

    (lens == Lens.on<String, Int>("name")) shouldBe true

    (lens == ClassRef.of<String>()) shouldBe false
    lens.equals("") shouldBe false
    lens.equals(null) shouldBe false

    (lens == Lens.on<String, Int>("family")) shouldBe false
    (lens == Lens.on<Int, Int>("name")) shouldBe false
    (lens == Lens.on<String, Long>("name")) shouldBe false

    (lens == Lens.Focus<Int, String>(PropertyRef.of("name", ClassRef.of<String>()))) shouldBe true
    (lens.hashCode() == Lens.Focus<Int, String>(PropertyRef.of("name", ClassRef.of<String>()))
      .hashCode()) shouldBe true

    (lens.hashCode() == PropertyRef.of<Int>("name", ClassRef.of<String>()).hashCode()) shouldBe true

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

    (lens == lensName.zoom(lensSize)) shouldBe true
    (lens == lensSize.zoom(Lens.on<Long, Int>("name"))) shouldBe false

    (lens == ClassRef.of<String>()) shouldBe false
    (lens == lensName) shouldBe false
    (lens == lensSize) shouldBe false

    (lens == Lens.on<String, Int>("family").zoom(lensSize)) shouldBe false
    (lens == lensName.zoom(Lens.on<Int, Long>("family"))) shouldBe false
    (lens == Lens.on<Int, Int>("name").zoom(Lens.on<Int, Long>("family"))) shouldBe false

    (lens == Lens.on<Long, Int>("name").zoom(Lens.on<Int, Long>("Size"))) shouldBe false
    (lens == Lens.on<Long, Int>("name").zoom(Lens.on<Int, Int>("Size"))) shouldBe false

    lens shouldNotBe Lens.Zoom(
      lensName,
      lensSize,
      PropertyRef("Int", ClassRef.of(), ClassRef.of<String>())
    )
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
