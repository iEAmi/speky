package com.github.speky.extenstion

import com.github.speky.core.ClassRef
import com.github.speky.extension.RichTable
import com.github.speky.table.Table
import io.kotest.core.spec.style.FunSpec
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty1

internal class RichTableTest : FunSpec({
  test("define table using extensions") {

  }
}) {

  @JvmInline
  value class PersonId(val value: Long)
  data class Address(val city: Int, val province: String)
  private data class Person(
    val id: PersonId,
    val name: String,
    val age: Int,
    val no: Int,
    val address: Address
  )

  private object Persons : RichTable<Person>("persons", ClassRef.of()) {
    val id = Person::id.transform() {}
    val name = Person::name.asVarchar()
    val age = Person::age.asInt()

    val addressCity = Person::address.embedded {
      val city =
    }

    override fun foo() {}
  }
}

fun <T, R> KProperty1<T, R>.embedded(f: (Embedded<T>) -> Unit): Embedded<T> = TODO()

class Embedded<T>(tableName: String) : Table<T>(tableName) {
  override fun foo() {}
}
