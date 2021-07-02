package com.github.speky.example.infra.table

import com.github.speky.core.ClassRef
import com.github.speky.core.table.ConstructorRef
import com.github.speky.core.table.ConstructorRef.Parameter
import com.github.speky.example.domain.Employee
import com.github.speky.extension.RichTable

object Employees : RichTable<Employee>("employees", ClassRef.of()) {
  private val id = bigint(Employee::id)
  private val name = varchar(Employee::name)
  private val family = varchar(Employee::family)
  private val jobId = bigint(Employee::jobId, "job_id")

  override fun constructorRef(): ConstructorRef<Employee> =
    ::Employee.ctorRef(
      Parameter.of(id),
      Parameter.of(name),
      Parameter.of(family),
      Parameter.of(jobId)
    )
}
