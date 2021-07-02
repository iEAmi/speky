package com.github.speky.example

import com.github.speky.example.infra.DefaultDatabase

object Main {
  private const val CONNECTION_STR = ""
  private val database: DefaultDatabase = DefaultDatabase(CONNECTION_STR)

  @JvmStatic
  fun main(args: Array<String>) {

  }
}
