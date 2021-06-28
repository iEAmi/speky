package com.github.speky.core.table

import com.github.speky.core.ClassRef
import com.github.speky.core.Lens
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

internal class EmbeddedTest : FunSpec({
  test("duplicate column should throw") {
    class EmbeddedCity : Embedded<City, Int>("city_", Lens.on("city"), ClassRef.of()) {
      val name = varchar("name", Lens.on("name"))
      val nameAgain = varchar("name", Lens.on("name"))

      override fun constructorRef(): ConstructorRef<City> {
        TODO()
      }
    }

    val e = shouldThrow<IllegalArgumentException> { EmbeddedCity() }

    e.message shouldNotBe null
    e.message!! shouldBe "Duplicate column 'name'"
  }

  test("Embedded.show test") {
    val embedded = EmbeddedCity()

    with(Embedded.show) {
      embedded.show() shouldBe "Embedded city: City"
    }
  }
}) {

  private data class City(val name: String, val province: String, val code: String)
  private class EmbeddedCity : Embedded<City, Int>("city_", Lens.on("city"), ClassRef.of()) {
    val name = varchar("name", Lens.on("name"))
    val province = varchar("province", Lens.on("province"))
    val code = varchar("code", Lens.on("code"))

    override fun constructorRef(): ConstructorRef<City> {
      val fRef = FunctionRef(3) { ::City.call(it) }

      return ConstructorRef(
        fRef,
        ConstructorRef.Parameter.of(name),
        ConstructorRef.Parameter.of(province),
        ConstructorRef.Parameter.of(code)
      )
    }
  }
}
