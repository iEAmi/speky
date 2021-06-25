package com.github.speky.table

import com.github.speky.core.ClassRef
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

      override fun constructorRef(): ConstructorRef<Person> {
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

    table.id.sqlType shouldBe SqlType.Bigint
    table.name.sqlType shouldBe SqlType.Varchar
    table.age.sqlType shouldBe SqlType.Integer
    table.text.sqlType shouldBe SqlType.LongVarchar
    table.boolean.sqlType shouldBe SqlType.Boolean
    table.uuid.sqlType shouldBe SqlType.Other
    table.timestamp.sqlType shouldBe SqlType.Timestamp
    table.timestampz.sqlType shouldBe SqlType.TimestampWithTimeZone

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

private data class Address(val country: String, val province: String, val city: String, val no: Int)
private data class Person(val id: Long, val name: String, val age: Int, val address: Address)
private class Persons : Table<Person>("persons") {
  val id = bigint("person_id", Lens.on("id"))
  val name = varchar("person_name", Lens.on("name"))
  val age = int("person_age", Lens.on("age"))

  val text = text("text", Lens.on("text"))
  val boolean = boolean("boolean", Lens.on("boolean"))
  val uuid = uuid("uuid", Lens.on("uuid"))
  val timestamp = timestamp("timestamp", Lens.on("timestamp"))
  val timestampz = timestampz("timestampz", Lens.on("timestampz"))

  val address = embedded(AddressEmbedded(Lens.on("address")))

  override fun constructorRef(): ConstructorRef<Person> {
    val fRef: FunctionRef<Person> = FunctionRef(3) { ::Person.call(it) }

    return ConstructorRef(fRef, id, name, age)
  }

  class AddressEmbedded<T>(lens: Lens<Address, T>) :
    Embedded<Address, T>("address_", lens, ClassRef.of()) {
    val country = varchar("country", Lens.on("country"))
    val province = varchar("province", Lens.on("province"))
    val city = varchar("city", Lens.on("city"))
    val no = int("no", Lens.on("no"))

    override fun constructorRef(): ConstructorRef<Address> {
      val fRef: FunctionRef<Address> = FunctionRef(4) { ::Address.call(it) }

      return ConstructorRef(fRef, country, province, city, no)
    }
  }
}
