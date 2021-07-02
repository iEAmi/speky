package com.github.speky.example.infra.table

import com.github.speky.core.ClassRef
import com.github.speky.core.table.ConstructorRef
import com.github.speky.core.table.ConstructorRef.Parameter
import com.github.speky.example.domain.Company
import com.github.speky.extension.RichTable

object Companies : RichTable<Company>("companies", ClassRef.of()) {
  private val id = Company::id.asBigint()
  private val name = Company::name.asVarchar()

  override fun constructorRef(): ConstructorRef<Company> =
    ::Company.ctorRef(Parameter.of(id), Parameter.of(name))
}
