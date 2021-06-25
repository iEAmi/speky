package com.github.speky.table

sealed class SqlValue private constructor(val type: SqlType) {
  class BigInt(val value: Long) : SqlValue(SqlType.Bigint)
  class Text(val value: String) : SqlValue(SqlType.LongVarchar)
}
