package com.github.speky.table

import com.github.speky.core.Lens
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeInstanceOf
import java.time.LocalDateTime
import java.util.*

internal class TableTest : FunSpec({
  test("duplicate column name should fails") {
    class Names : Table<Person>("pp") {
      val id = bigint("id", Lens.on("id"))
      val idAgain = bigint("id", Lens.on("id"))

      override fun foo() {
        TODO("Not yet implemented")
      }
    }

    val e = shouldThrow<IllegalArgumentException> {
      Names()
    }

    e.message shouldNotBe null
    e.message!! shouldBe "Duplicate column 'id'"
  }

  test("columns type should be valid") {
    shouldNotThrow<IllegalArgumentException> {
      Persons()
    }

    val table = Persons()
    table.id.shouldBeInstanceOf<Column<Person, Long>>()
    table.name.shouldBeInstanceOf<Column<Person, String>>()
    table.age.shouldBeInstanceOf<Column<Person, Int>>()
    table.text.shouldBeInstanceOf<Column<Person, String>>()
    table.boolean.shouldBeInstanceOf<Column<Person, Boolean>>()
    table.uuid.shouldBeInstanceOf<Column<Person, UUID>>()
    table.timestamp.shouldBeInstanceOf<Column<Person, LocalDateTime>>()
    table.timestampz.shouldBeInstanceOf<Column<Person, LocalDateTime>>()

    table.id.name shouldBe "person_id"
    table.name.name shouldBe "person_name"
    table.age.name shouldBe "person_age"
    table.text.name shouldBe "text"
    table.boolean.name shouldBe "boolean"
    table.uuid.name shouldBe "uuid"
    table.timestamp.name shouldBe "timestamp"
    table.timestampz.name shouldBe "timestampz"

    table.id.table shouldBe table
    table.name.table shouldBe table
    table.age.table shouldBe table
    table.text.table shouldBe table
    table.boolean.table shouldBe table
    table.uuid.table shouldBe table
    table.timestamp.table shouldBe table
    table.timestampz.table shouldBe table

    table.id.dbType shouldBe DBType.Bigint
    table.name.dbType shouldBe DBType.Varchar
    table.age.dbType shouldBe DBType.Int
    table.text.dbType shouldBe DBType.Text
    table.boolean.dbType shouldBe DBType.Boolean
    table.uuid.dbType shouldBe DBType.UUID
    table.timestamp.dbType shouldBe DBType.Timestamp
    table.timestampz.dbType shouldBe DBType.Timestampz

    table.id.lens shouldBe Lens.on("id")
    table.name.lens shouldBe Lens.on("name")
    table.age.lens shouldBe Lens.on("age")
    table.text.lens shouldBe Lens.on("text")
    table.boolean.lens shouldBe Lens.on("boolean")
    table.uuid.lens shouldBe Lens.on("uuid")
    table.timestamp.lens shouldBe Lens.on("timestamp")
    table.timestampz.lens shouldBe Lens.on("timestampz")
  }
})

private data class Person(val id: Long, val name: String, val age: Int)
private class Persons : Table<Person>("persons") {
  val id = bigint("person_id", Lens.on("id"))
  val name = varchar("person_name", Lens.on("name"))
  val age = int("person_age", Lens.on("age"))

  val text = text("text", Lens.on("text"))
  val boolean = boolean("boolean", Lens.on("boolean"))
  val uuid = uuid("uuid", Lens.on("uuid"))
  val timestamp = timestamp("timestamp", Lens.on("timestamp"))
  val timestampz = timestampz("timestampz", Lens.on("timestampz"))

  override fun foo() {
    TODO("Not yet implemented")
  }
}
