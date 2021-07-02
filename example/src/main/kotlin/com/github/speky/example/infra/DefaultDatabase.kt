package com.github.speky.example.infra

import com.github.speky.core.ClassRef
import com.github.speky.example.infra.table.Companies
import com.github.speky.example.infra.table.Employees
import com.github.speky.example.infra.table.Jobs
import com.github.speky.postgres.Database

class DefaultDatabase(url: String) : Database(url) {
  override fun onRegisterTable() {
    registerTable(Companies, ClassRef.of())
    registerTable(Employees, ClassRef.of())
    registerTable(Jobs, ClassRef.of())
  }
}
