package com.github.speky.table

abstract class ValueTransformer<T, S : SqlValue> {
  abstract fun toSqlValue(value: T): S

  abstract fun fromSqlValue(sqlValue: S): T
}
