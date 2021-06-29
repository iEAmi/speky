package com.github.speky.core.table

/**
 * Transforming a value from/to sql.
 */
interface ValueTransformer<T, S : SqlValue> {
  /**
   * Convert [value] to [SqlValue].
   */
  fun toSqlValue(value: T): S

  /**
   * Convert [sqlValue] to [T].
   */
  fun fromSqlValue(sqlValue: S): T

  /**
   * Convert [sqlValue] to [T].
   */
  @Suppress("UNCHECKED_CAST")
  fun fromSqlValue0(sqlValue: SqlValue): T = fromSqlValue(sqlValue as S)
}
