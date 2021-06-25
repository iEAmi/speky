package com.github.speky.table

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

internal class FunctionRefTest : FunSpec({
  test("FunctionRef should dispatch invoke to delegate function") {
    val f = MockFunction()
    val fRef = FunctionRef(3, f)

    fRef.invoke(1, 2, 3)

    f.invoked shouldBe true
    f.paramCount shouldBe 3
  }
}) {
  private class MockFunction : (Array<out Any?>) -> Int {
    var invoked: Boolean = false
      private set

    var paramCount: Int = 0
      private set

    override fun invoke(p1: Array<out Any?>): Int {
      invoked = true
      paramCount = p1.size
      return 0
    }
  }
}
