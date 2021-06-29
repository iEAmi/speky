package com.github.speky.postgres.executor

import com.github.speky.core.specification.executor.ExecutionResult
import java.math.BigDecimal
import java.sql.ResultSet
import java.sql.Time
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

/**
 * Implementation of [ExecutionResult] for based-on [ResultSet].
 *
 * @property resultSet jdbc result
 */
@Suppress("UNCHECKED_CAST")
internal class ExecutionResultFromResultSet(private val resultSet: ResultSet) : ExecutionResult {
  override fun getSmallInt(columnName: String): Short =
    resultSet.getShort(resultSet.findColumn(columnName))

  override fun getInteger(columnName: String): Int =
    resultSet.getInt(resultSet.findColumn(columnName))

  override fun getBigint(columnName: String): Long =
    resultSet.getLong(resultSet.findColumn(columnName))

  override fun getFloat(columnName: String): Float =
    resultSet.getFloat(resultSet.findColumn(columnName))

  override fun getReal(columnName: String): Double =
    resultSet.getDouble(resultSet.findColumn(columnName))

  override fun getDouble(columnName: String): Double =
    resultSet.getDouble(resultSet.findColumn(columnName))

  override fun getNumeric(columnName: String): Double =
    resultSet.getDouble(resultSet.findColumn(columnName))

  override fun getDecimal(columnName: String): BigDecimal =
    resultSet.getBigDecimal(resultSet.findColumn(columnName))

  override fun getVarchar(columnName: String): String =
    resultSet.getString(resultSet.findColumn(columnName))

  override fun getLongVarchar(columnName: String): String =
    resultSet.getString(resultSet.findColumn(columnName))

  override fun getDate(columnName: String): Date =
    resultSet.getDate(resultSet.findColumn(columnName))

  override fun getTime(columnName: String): Time =
    resultSet.getTime(resultSet.findColumn(columnName))

  override fun getTimestamp(columnName: String): Instant =
    resultSet.getTimestamp(resultSet.findColumn(columnName)).toInstant()

  override fun getBinary(columnName: String): ByteArray =
    resultSet.getBytes(resultSet.findColumn(columnName))

  override fun getOther(columnName: String): UUID =
    resultSet.getObject(resultSet.findColumn(columnName), UUID::class.java)

  override fun getArray(columnName: String): Array<out Any> =
    resultSet.getArray(resultSet.findColumn(columnName)).array as Array<out Any>

  override fun getBlob(columnName: String): ByteArray =
    resultSet.getBlob(resultSet.findColumn(columnName)).binaryStream.readBytes()

  override fun getBoolean(columnName: String): Boolean =
    resultSet.getBoolean(resultSet.findColumn(columnName))

  override fun getTimeWithTimeZone(columnName: String): LocalTime =
    resultSet.getTime(resultSet.findColumn(columnName)).toLocalTime()

  override fun getTimestampWithTimeZone(columnName: String): LocalDateTime =
    resultSet.getTimestamp(resultSet.findColumn(columnName)).toLocalDateTime()
}
