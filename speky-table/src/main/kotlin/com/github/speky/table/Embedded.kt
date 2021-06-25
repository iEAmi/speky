package com.github.speky.table

abstract class Embedded<E, T>(val columnPrefix: String, val tableRef: Table<T>) {
  private val columns: MutableSet<Column<T, *>> = mutableSetOf()
}
