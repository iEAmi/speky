package com.github.speky.table

import com.github.speky.core.ClassRef
import com.github.speky.core.Lens
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

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
    val fRef: FunctionRef<Person> = FunctionRef(4) { ::Person.call(it) }

    return ConstructorRef(
      fRef,
      ConstructorRef.Parameter.of(id),
      ConstructorRef.Parameter.of(name),
      ConstructorRef.Parameter.of(age),
      ConstructorRef.Parameter.of(address)
    )
  }
}

private class AddressEmbedded<T>(
  lens: Lens<Address, T>
) : Embedded<Address, T>("address_", lens, ClassRef.of()) {
  val country = varchar("country", Lens.on("country"))
  val province = varchar("province", Lens.on("province"))
  val city = varchar("city", Lens.on("city"))
  val no = int("no", Lens.on("no"))

  override fun constructorRef(): ConstructorRef<Address> {
    val fRef: FunctionRef<Address> = FunctionRef(4) { ::Address.call(it) }

    return ConstructorRef(
      fRef,
      ConstructorRef.Parameter.of(country),
      ConstructorRef.Parameter.of(province),
      ConstructorRef.Parameter.of(city),
      ConstructorRef.Parameter.of(no)
    )
  }
}
