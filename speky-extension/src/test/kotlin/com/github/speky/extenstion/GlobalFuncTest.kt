package com.github.speky.extenstion

import com.github.speky.core.specification.Specification
import com.github.speky.extension.from
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

internal class GlobalFuncTest : FunSpec({
  test("from() should be return same value as Specification.from()") {
    val from = from<Int>()
    val spec = Specification.from<Int>("int")

    from.alias shouldBe spec.alias
    from.classRef shouldBe spec.classRef
  }

  test("from(String) should be return same value as Specification.from(String)") {
    val from = from<Int>("foo")
    val spec = Specification.from<Int>("foo")

    from.alias shouldBe spec.alias
    from.classRef shouldBe spec.classRef
  }
})
