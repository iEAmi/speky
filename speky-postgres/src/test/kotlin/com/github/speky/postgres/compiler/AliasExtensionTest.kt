package com.github.speky.postgres.compiler

import com.github.speky.core.ClassRef
import com.github.speky.core.PropertyRef
import com.github.speky.core.specification.Alias
import com.github.speky.postgres.compiler.internal.find
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

internal class AliasExtensionTest : FunSpec({
  test("Alias.Single.find should same value as input") {
    val al = Alias.of<Int>("na")

    val find = al.find(PropertyRef<String>("na", ClassRef.of(), ClassRef.of<Int>()))

    find shouldBe al
  }

  test("Alias.JustClassRef.find should throw") {
    val al = Alias.justClassRef<Int>()

    val e = shouldThrow<UnsupportedOperationException> {
      al.find(PropertyRef<String>("na", ClassRef.of(), ClassRef.of<Int>()))
    }

    e.message shouldNotBe null
    e.message!! shouldBe "JustClassRef could not be Single"
  }

  test("Alias.Multiply.find should return valid value") {
    val al = Alias.of<Int, Long, String>(Alias.of("na"), Alias.of("ma"))

    val find = al.find(PropertyRef<String>("name", ClassRef.of(), ClassRef.of<Int>()))

    find shouldBe Alias.of<Int>("na")
  }

  test("Alias.Multiply.find with same ClassRef") {
    val al = Alias.of<Int, Int, Int>(Alias.of("na"), Alias.of("ma"))

    val find = al.find(PropertyRef<String>("name", ClassRef.of(), ClassRef.of<Int>()))

    find shouldBe Alias.of<Int>("na")
  }
})
