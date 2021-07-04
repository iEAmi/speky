package com.github.speky.example

import com.github.speky.example.infra.DefaultDatabase
import com.github.speky.example.infra.table.Companies
import com.github.speky.example.infra.table.Jobs

object Main {
  private const val CONNECTION_STR = "jdbc:postgresql://localhost/speky?user=postgres&password=postgres"
  private val database: DefaultDatabase = DefaultDatabase(CONNECTION_STR)

  @JvmStatic
  fun main(args: Array<String>) {
    initDatabase()
  }

  private fun initDatabase() {
    with(database) {

//      println(compile(Jobs.initValues))
//      println(compile(Companies.initValues))

//      execute(Companies.initValues)
      execute(Jobs.initValues)
    }
  }
}
