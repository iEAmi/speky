package com.github.speky.table

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
}
