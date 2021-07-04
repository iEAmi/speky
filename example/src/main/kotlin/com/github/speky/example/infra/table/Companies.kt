package com.github.speky.example.infra.table

import com.github.speky.core.ClassRef
import com.github.speky.core.specification.Value
import com.github.speky.core.table.ConstructorRef
import com.github.speky.core.table.ConstructorRef.Parameter
import com.github.speky.example.domain.Company
import com.github.speky.extension.RichTable
import com.github.speky.extension.insertOn

object Companies : RichTable<Company>("companies", ClassRef.of()) {
  private val id = Company::id.asBigint()
  private val name = Company::name.asVarchar()

  override fun constructorRef(): ConstructorRef<Company> =
    ::Company.ctorRef(Parameter.of(id), Parameter.of(name))

  val initValues = insertOn<Company>(
    Value.of("id", 1L),
    Value.of("name", "Google"),

    Value.of("id", 2L),
    Value.of("name", "Apple"),

    Value.of("id", 3L),
    Value.of("name", "Amazon"),

    Value.of("id", 4L),
    Value.of("name", "Uber")
  )
}
