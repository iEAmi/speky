package com.github.speky.postgres

import com.github.speky.core.ClassRef
import com.github.speky.core.Lens
import com.github.speky.core.specification.Filter
import com.github.speky.core.specification.Order
import com.github.speky.core.specification.Specification
import com.github.speky.core.table.ConstructorRef
import com.github.speky.core.table.Embedded
import com.github.speky.core.table.Table
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

internal class DatabaseTest : FunSpec({
  test("compile select specification against database") {
    val spec = Specification.from<Person>("pp")
      .select()

    MyDatabase.compile(spec) shouldBe "SELECT *\nFROM all_persons AS pp"
  }

  test("compile select some specification against database") {
    val spec = Specification.from<Person>("pp")
      .select(Lens.on<Person, Long>("id"), Lens.on<Person, String>("name"))

    MyDatabase.compile(spec) shouldBe "SELECT pp.p_id, pp.p_name\nFROM all_persons AS pp"
  }

  test("compile order asc specification against database") {
    val spec = Specification.from<Person>("pp")
      .select(Lens.on<Person, Long>("id"), Lens.on<Person, String>("name"))
      .order(Order.asc(Lens.on<Person, String>("name")))

    MyDatabase.compile(spec) shouldBe
        "SELECT pp.p_id, pp.p_name\nFROM all_persons AS pp\nORDER BY pp.p_name"
  }

  test("compile order desc specification against database") {
    val spec = Specification.from<Person>("pp")
      .select(Lens.on<Person, Long>("id"), Lens.on<Person, String>("name"))
      .order(Order.desc(Lens.on<Person, String>("name")))

    MyDatabase.compile(spec) shouldBe
        "SELECT pp.p_id, pp.p_name\nFROM all_persons AS pp\nORDER BY pp.p_name DESC"
  }

  test("compile sized specification against database") {
    val spec = Specification.from<Person>("pp")
      .select(Lens.on<Person, Long>("id"), Lens.on<Person, String>("name"))
      .order(Order.desc(Lens.on<Person, String>("name")))
      .size(10, 0)

    MyDatabase.compile(spec) shouldBe
        "SELECT pp.p_id, pp.p_name\nFROM all_persons AS pp\nORDER BY pp.p_name DESC\nLIMIT 10 OFFSET 0"
  }

  test("compile filter specification against database") {
    val spec = Specification.from<Person>("pp")
      .select(Lens.on<Person, Long>("id"), Lens.on<Person, String>("name"))
      .filter(Filter.like(Lens.on<Person, String>("name"), "spek"))
      .order(Order.desc(Lens.on<Person, String>("name")))
      .size(10, 0)

    MyDatabase.compile(spec) shouldBe
        "SELECT pp.p_id, pp.p_name\nFROM all_persons AS pp\nWHERE pp.p_name LIKE '%spek%'\nORDER BY pp.p_name DESC\nLIMIT 10 OFFSET 0"
  }

  test("compile select embedded specification against database") {
    val spec = Specification.from<Person>("pp")
      .select(Lens.on<Person, Long>("id"), Lens.on<Person, Address>("address"))

    MyDatabase.compile(spec) shouldBe "SELECT pp.p_id, pp.address_city, pp.address_province\nFROM all_persons AS pp"
  }
}) {
  private object MyDatabase : Database() {
    override fun onRegisterTable() {
      registerTable(Persons, ClassRef.of())
    }
  }

  private class Address(val city: String, val province: String)
  private class Person(val id: Long, val name: String, val address: Address)
  private object Persons : Table<Person>("all_persons") {
    val id = bigint("p_id", Lens.on("id"))
    val name = varchar("p_name", Lens.on("name"))
    val address = embedded(AddressEmbedded(Lens.on("address")))

    override fun constructorRef(): ConstructorRef<Person> {
      TODO("Not yet implemented")
    }
  }

  private class AddressEmbedded<T>(lens: Lens<Address, T>) :
    Embedded<Address, T>("address_", lens, ClassRef.of()) {

    val city = varchar("city", Lens.on("city"))
    val province = varchar("province", Lens.on("province"))

    override fun constructorRef(): ConstructorRef<Address> {
      TODO("Not yet implemented")
    }
  }
}
