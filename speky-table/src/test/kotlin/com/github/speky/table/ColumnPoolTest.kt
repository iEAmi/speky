package com.github.speky.table

import com.github.speky.core.Lens
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

internal class ColumnPoolTest : FunSpec({
  test("duplicate column should throw") {
    val e = shouldThrow<IllegalArgumentException> {
      MyColumnPool()
    }

    e.message shouldNotBe null
    e.message!! shouldBe "Duplicate column 'name'"
  }
}) {
  private class Note
  private class MyColumnPool : Table<Note>("notes") {
    val name = varchar("name", Lens.on("name"))
    val nameAgain = varchar("name", Lens.on("name"))
    override fun constructorRef(): ConstructorRef<Note> {
      TODO("Not yet implemented")
    }
  }
}
