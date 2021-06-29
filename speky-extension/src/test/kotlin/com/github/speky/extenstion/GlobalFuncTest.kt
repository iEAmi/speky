package com.github.speky.extenstion

import com.github.speky.core.specification.Specification
import com.github.speky.core.specification.Value
import com.github.speky.extension.from
import com.github.speky.extension.insertOn
import com.github.speky.extension.updateOn
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainAll
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

  test("insertOn should return same value as Specification.insertOn()") {
    val insert = insertOn<Int>(
      Value.of("name", 10),
      Value.of("family", 20)
    )

    val spec = Specification.insertOn<Int>(
      Value.of("name", 10),
      Value.of("family", 20)
    )

    insert.alias shouldBe spec.alias
    insert.classRef shouldBe spec.classRef
    insert.values shouldContainAll spec.values
  }

  test("updateOn should return same value as Specification.updateOn()") {
    val update = updateOn<Int>(
      Value.of("name", 10),
      Value.of("family", 20)
    )

    val spec = updateOn<Int>(
      Value.of("name", 10),
      Value.of("family", 20)
    )

    update.alias shouldBe spec.alias
    update.classRef shouldBe spec.classRef
    update.values shouldContainAll spec.values
  }
})
