package com.github.speky.core.specification.executor

import com.github.speky.core.specification.Specification
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime

/**
 * Result of the execution of a [Specification] against database.
 */
@Suppress("ComplexInterface")
interface ExecutionResult {
  /**
   * Gets [Short] value of the column with [columnName].
   */
  fun getSmallInt(columnName: String): Short

  /**
   * Gets [Int] value of the column with [columnName].
   */
  fun getInteger(columnName: String): Int

  /**
   * Gets [Long] value of the column with [columnName].
   */
  fun getBigint(columnName: String): Long

  /**
   * Gets [Float] value of the column with [columnName].
   */
  fun getFloat(columnName: String): Float

  /**
   * Gets [Double] value of the column with [columnName].
   */
  fun getReal(columnName: String): Double

  /**
   * Gets [Double] value of the column with [columnName].
   */
  fun getDouble(columnName: String): Double

  /**
   * Gets [Double] value of the column with [columnName].
   */
  fun getNumeric(columnName: String): Double

  /**
   * Gets [BigDecimal] value of the column with [columnName].
   */
  fun getDecimal(columnName: String): BigDecimal

  /**
   * Gets [String] value of the column with [columnName].
   */
  fun getVarchar(columnName: String): String

  /**
   * Gets [String] value of the column with [columnName].
   */
  fun getLongVarchar(columnName: String): String

  /**
   * Gets [java.util.Date] value of the column with [columnName].
   */
  fun getDate(columnName: String): java.util.Date

  /**
   * Gets [java.sql.Time] value of the column with [columnName].
   */
  fun getTime(columnName: String): java.sql.Time

  /**
   * Gets [Instant] value of the column with [columnName].
   */
  fun getTimestamp(columnName: String): Instant

  /**
   * Gets [ByteArray] value of the column with [columnName].
   */
  fun getBinary(columnName: String): ByteArray

  /**
   * Gets [java.util.UUID] value of the column with [columnName].
   */
  fun getOther(columnName: String): java.util.UUID

  /**
   * Gets [Array] value of the column with [columnName].
   */
  fun getArray(columnName: String): Array<out Any>

  /**
   * Gets [ByteArray] value of the column with [columnName].
   */
  fun getBlob(columnName: String): ByteArray

  /**
   * Gets [Boolean] value of the column with [columnName].
   */
  fun getBoolean(columnName: String): Boolean

  /**
   * Gets [LocalTime] value of the column with [columnName].
   */
  fun getTimeWithTimeZone(columnName: String): LocalTime

  /**
   * Gets [LocalDateTime] value of the column with [columnName].
   */
  fun getTimestampWithTimeZone(columnName: String): LocalDateTime
}
