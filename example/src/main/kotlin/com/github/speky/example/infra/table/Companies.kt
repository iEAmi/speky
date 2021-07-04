package com.github.speky.example.infra.table

import com.github.speky.core.ClassRef
import com.github.speky.core.specification.Sink
import com.github.speky.core.table.ConstructorRef
import com.github.speky.core.table.ConstructorRef.Parameter
import com.github.speky.example.domain.Company
import com.github.speky.extension.RichTable
import com.github.speky.extension.insertOn
import com.github.speky.extension.setTo

object Companies : RichTable<Company>("companies", ClassRef.of()) {
  private val id = Company::id.asBigint()
  private val name = Company::name.asVarchar()

  override fun constructorRef(): ConstructorRef<Company> =
    ::Company.ctorRef(Parameter.of(id), Parameter.of(name))

  val initValues: Sink<Company> = insertOn(
      Company::id setTo 1L,
      Company::name setTo "Google",

      Company::id setTo 2L,
      Company::name setTo "Apple",

      Company::id setTo 3L,
      Company::name setTo "Amazon",

      Company::id setTo 4L,
      Company::name setTo "Uber"
    )
}
