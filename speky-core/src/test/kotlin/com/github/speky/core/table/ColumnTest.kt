package com.github.speky.core.table

import com.github.speky.core.Lens
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

internal class ColumnTest : FunSpec({
  test("Column.show test") {
    val column = Column(
      "name", Lens.on<Int, String>("string"),
      SqlType.Varchar, SqlValue.Varchar.transformer
    )
    with(Column.show) {
      column.show() shouldBe "name: varchar"
    }
  }
})
