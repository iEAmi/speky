package com.github.speky.core.specification

import com.github.speky.core.ClassRef
import com.github.speky.core.Lens
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeInstanceOf

internal class SinkTest : FunSpec({
  test("insert into table") {
    val insert = Sink.insert<Table1>(
      listOf(
        Value.of("id", 1L),
        Value.of("name", "Ahmad")
      )
    )

    insert.shouldBeInstanceOf<Sink.Insert<Table1>>()
    insert.alias.shouldBeInstanceOf<Alias.JustClassRef<Table1>>()
    insert.classRef shouldBe insert.alias.classRef
    insert.classRef shouldBe ClassRef.of()
    insert.values shouldHaveSize 2
    insert.values shouldContain Value.of("id", 1L)
    insert.values shouldContain Value.of("name", "Ahmad")
  }

  test("insert without columns should throw") {
    shouldThrow<NoSuchElementException> { Sink.insert<Table1>(emptyList()) }
  }

  test("inconsistent values in Insert") {
    val e = shouldThrow<IllegalArgumentException> {
      Sink.insert<Table1>(
        listOf(
          Value.of("id", 1L),
          Value.of("name", "Ahmad"),

          Value.of("id", 2L),
        )
      )
    }

    e.message?.shouldNotBe(null)
    e.message!! shouldBe
        "inconsistent value count for columns. max value count is 2, but name have less count."
  }

  test("Sink.show on Insert") {
    val insert = Sink.insert<Table1>(
      listOf(
        Value.of("id", 1L),
        Value.of("name", "Ahmad")
      )
    )

    with(Sink.show) {
      insert.show() shouldBe "Insert into table1"
    }
  }

  test("update all columns") {
    val update = Sink.update<Table1>(
      setOf(
        Value.of("name", "Baz"),
        Value.of("name", "Foo"),
        Value.of("name", "Foo"),
        Value.of("name", "Foo"),
        Value.of("name", "Foo"),
      )
    )

    update.shouldBeInstanceOf<Sink.Update<Table1>>()
    update.alias.shouldBeInstanceOf<Alias.JustClassRef<Table1>>()
    update.classRef shouldBe update.alias.classRef
    update.classRef shouldBe ClassRef.of()
    update.values shouldHaveSize 2
    update.values shouldContain Value.of("name", "Baz")
    update.values shouldContain Value.of("name", "Foo")
  }

  test("update with filter") {
    val update = Sink.update<Table1>(setOf(Value.of("name", "Baz")))
      .filter(Filter.eq(Lens.on<Table1, Long>("id"), 10))

    update.shouldBeInstanceOf<Filtered<Table1, Long>>()
    update.alias.shouldBeInstanceOf<Alias.JustClassRef<Table1>>()
    update.classRef shouldBe update.alias.classRef
    update.classRef shouldBe ClassRef.of()

    update.delegate.shouldBeInstanceOf<Sink.Update<Table1>>()
    (update.delegate as Sink.Update<Table1>).values shouldHaveSize 1
    (update.delegate as Sink.Update<Table1>).values shouldContain Value.of("name", "Baz")
  }

  test("Sink.show on Update") {
    val update = Sink.update<Table1>(
      setOf(
        Value.of("id", 1L),
        Value.of("name", "Ahmad")
      )
    )

    with(Sink.show) {
      update.show() shouldBe "Update table1"
    }
  }
})
