package com.github.speky.core.specification

import com.github.speky.core.ClassRef
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeInstanceOf

internal class AliasTest : FunSpec({
  test("flatten Alias.Single should return same Alias.Single") {
    val al = Alias.of<Int>("nam")

    al.flatten() shouldHaveSize 1
    al.flatten() shouldContain al
  }

  test("flatten Alias.JustClassRef should throw") {
    val e = shouldThrow<UnsupportedOperationException> {
      Alias.justClassRef<Int>().flatten()
    }

    e.message shouldNotBe null
    e.message!! shouldBe "JustClassRef could not bew Single"
  }

  test("flatten Alias.Multiply should return at least two") {
    val al = Alias.of<Int, Int, Int>(Alias.of("n"), Alias.of("m"))

    al.flatten() shouldHaveSize 2
    al.flatten() shouldContain Alias.of<Int>("n")
    al.flatten() shouldContain Alias.of<Int>("m")
  }

  test("flatten Alias.Multiply should return three") {
    val al = Alias.of<Int, Int, Int>(
      Alias.of<Int, Int, Int>(
        Alias.of("n"),
        Alias.of("m")
      ),
      Alias.of("b")
    )

    al.flatten() shouldHaveSize 3
    al.flatten() shouldContain Alias.of<Int>("n")
    al.flatten() shouldContain Alias.of<Int>("m")
    al.flatten() shouldContain Alias.of<Int>("b")
  }

  test("Alias.of(String) should return Alias.Single") {
    val al = Alias.of<Int>("nam")

    al.shouldBeInstanceOf<Alias.Single<Int>>()
    al.value shouldBe "nam"
    (al.classRef == ClassRef.of<Int>()) shouldBe true
    al.classRef.name shouldBe "Int"
    al.classRef.qualifiedName shouldBe "kotlin.Int"
  }

  test("Alias.of() with default value should return Alias.Single") {
    val al = Alias.of<Int>("int")

    al.shouldBeInstanceOf<Alias.Single<Int>>()
    al.value shouldBe "int"
    (al.classRef == ClassRef.of<Int>()) shouldBe true
  }

  test("Alias.of(Alias, Alias) should return Alias.Multiply") {
    val alias1 = Alias.of<Int>("1")
    val alias2 = Alias.of<String>("2")

    val al = Alias.of<Int, String, Long>(alias1, alias2)

    al.shouldBeInstanceOf<Alias.Multiply<Int, String, Long>>()
    al.left.shouldBeInstanceOf<Alias.Single<Int>>()
    al.right.shouldBeInstanceOf<Alias.Single<String>>()

    (al.left as Alias.Single<Int>).value shouldBe "1"
    (al.right as Alias.Single<String>).value shouldBe "2"

    (al.left.classRef == ClassRef.of<Int>()) shouldBe true
    (al.right.classRef == ClassRef.of<String>()) shouldBe true

    (al.classRef == ClassRef.of<Long>()) shouldBe true
  }

  test("Alias.justClass() should return Alias.JustClassRef") {
    val al = Alias.justClassRef<Int>()

    al.shouldBeInstanceOf<Alias.JustClassRef<Int>>()
    (al.classRef == ClassRef.of<Int>()) shouldBe true
  }

  test("Alias.show for Alias.Single") {
    val al = Alias.of<Int>("nam")

    with(Alias.show) {
      al.show() shouldBe "Int as nam"
    }
  }

  test("Alias.show for Alias.Multiply") {
    val alias1 = Alias.of<Int>("1")
    val alias2 = Alias.of<String>("2")

    val alias3 = Alias.of<Int, String, Long>(alias1, alias2)

    with(Alias.show) {
      alias3.show() shouldBe "Int as 1 & String as 2"
    }

    val al = Alias.of<Long, Short, Boolean>(alias3, Alias.of("3"))

    with(Alias.show) {
      al.show() shouldBe "Int as 1 & String as 2 & Short as 3"
    }
  }

  test("Alias.show for Alias.JustClassRef") {
    val al = Alias.justClassRef<Int>()

    with(Alias.show) {
      al.show() shouldBe al.classRef.name.lowercase()
    }
  }

  test("Alias.Single equals and hashCode") {
    val al = Alias.of<Int>("nam")

    (al == al) shouldBe true
    (al.hashCode() == al.hashCode()) shouldBe true

    (al == ClassRef.of<Int>()) shouldBe false

    (al == Alias.of<Int>("nam")) shouldBe true
    (al.hashCode() == Alias.of<Int>("nam").hashCode()) shouldBe true

    (al == Alias.of<Int>("name")) shouldBe false
    (al.hashCode() == Alias.of<Int>("name").hashCode()) shouldBe false

    (al == Alias.of<String>("nam")) shouldBe false
    (al.hashCode() == Alias.of<String>("nam").hashCode()) shouldBe false
  }

  test("Alias.Multiply equals and hashCode") {
    val alias1 = Alias.of<Int>("1")
    val alias2 = Alias.of<String>("2")

    val al = Alias.of<Int, String, Long>(alias1, alias2)

    (al == al) shouldBe true
    (al.hashCode() == al.hashCode()) shouldBe true

    (al == Alias.of<Int, String, Long>(Alias.of("1"), Alias.of("2"))) shouldBe true

    (al == ClassRef.of<Int>()) shouldBe false

    (al == alias1) shouldBe false
    (al == alias2) shouldBe false

    (al == Alias.of<Int, Int, String>(Alias.of("1"), Alias.of("1"))) shouldBe false
    (al == Alias.of<Long, String, Long>(Alias.of("2"), Alias.of("1"))) shouldBe false
    (al == Alias.of<Int, String, Long>(Alias.of("2"), Alias.of("3"))) shouldBe false

    (al == Alias.of<Int, String, Long>(Alias.of("1"), Alias.of("22"))) shouldBe false
    (al == Alias.of<Int, String, Long>(Alias.of("11"), Alias.of("22"))) shouldBe false

    (al == Alias.of<Int>("name")) shouldBe false
    (al.hashCode() == Alias.of<Int>("name").hashCode()) shouldBe false

    (al == Alias.of<String>("nam")) shouldBe false
    (al.hashCode() == Alias.of<String>("nam").hashCode()) shouldBe false
  }

  test("Alias.JustClassRef equals and hashCode") {
    val al = Alias.justClassRef<Int>()

    al.shouldBeInstanceOf<Alias.JustClassRef<Int>>()
    (al == al) shouldBe true
    (al.hashCode() == al.hashCode()) shouldBe true

    (al == ClassRef.of<Int>()) shouldBe false

    (al == Alias.justClassRef<Int>()) shouldBe true
    (al.hashCode() == Alias.justClassRef<Int>().hashCode()) shouldBe true

    (al == Alias.justClassRef<Long>()) shouldBe false
    (al.hashCode() == Alias.justClassRef<Long>().hashCode()) shouldBe false
  }
})
