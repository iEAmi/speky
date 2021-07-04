package com.github.speky.example.infra.table

import com.github.speky.core.ClassRef
import com.github.speky.core.specification.Sink
import com.github.speky.core.table.ConstructorRef
import com.github.speky.core.table.ConstructorRef.Parameter
import com.github.speky.core.table.Embedded
import com.github.speky.example.domain.Address
import com.github.speky.example.domain.Employee
import com.github.speky.extension.RichTable
import com.github.speky.extension.insertOn
import com.github.speky.extension.on
import com.github.speky.extension.setTo
import com.github.speky.extension.zoom

object Employees : RichTable<Employee>("employees", ClassRef.of()) {
  private val id = bigint(Employee::id)
  private val name = varchar(Employee::name)
  private val family = varchar(Employee::family)
  private val jobId = bigint(Employee::jobId, "job_id")
  private val address = embedded(Addresses)

  override fun constructorRef(): ConstructorRef<Employee> =
    ::Employee.ctorRef(
      Parameter.of(id),
      Parameter.of(name),
      Parameter.of(family),
      Parameter.of(address),
      Parameter.of(jobId)
    )

  val initValues: Sink<Employee> = insertOn(
    Employee::id setTo 1L,
    Employee::name setTo "John",
    Employee::family setTo "Boo",
    Employee::address zoom Address::city setTo "Tehran",
    Employee::address zoom Address::province setTo "Tehran",
    Employee::address zoom Address::country setTo "Iran",
    Employee::jobId setTo 1L,
  )
}

object Addresses :
  Embedded<Address, Employee>("address_", Employee::address.on(), ClassRef.of()) {
  val city = varchar("city", Address::city.on())
  val province = varchar("province", Address::province.on())
  val country = varchar("country", Address::country.on())

  override fun constructorRef(): ConstructorRef<Address> {
    TODO("Not yet implemented")
  }
}
