package com.github.speky.core.specification

import com.github.speky.core.ClassRef
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

internal class SpecificationTest : FunSpec({

  test("invoke(Alias) should return Source.Single") {
    val spec = Specification.from<Int>("alias")

    spec.shouldBeInstanceOf<Source.Single<Int>>()
    spec.alias shouldBe Alias.of("alias")
    spec.alias.classRef shouldBe ClassRef.of()
    spec.classRef shouldBe spec.classRef
  }

  test("invoke(String) should return Source.Single") {
    val spec = Specification.from<Int>("alias")

    spec.shouldBeInstanceOf<Source.Single<Int>>()
    spec.alias shouldBe Alias.of("alias")
    spec.alias.classRef shouldBe ClassRef.of()
    spec.classRef shouldBe spec.classRef
  }
})
