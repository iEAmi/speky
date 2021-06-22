package com.github.speky.core.specification

import com.github.speky.core.ClassRef
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

internal class SpecificationTest : FunSpec({

  test("invoke(Alias) should return Source.Single") {
    val spec = Specification.invoke(Alias<Int>("alias"))

    spec.shouldBeInstanceOf<Source.Single<Int>>()
    spec.alias shouldBe Alias("alias")
    spec.alias.classRef shouldBe ClassRef()
    spec.classRef shouldBe spec.classRef
  }

  test("invoke(String) should return Source.Single") {
    val spec = Specification.invoke<Int>("alias")

    spec.shouldBeInstanceOf<Source.Single<Int>>()
    spec.alias shouldBe Alias("alias")
    spec.alias.classRef shouldBe ClassRef()
    spec.classRef shouldBe spec.classRef
  }
})
