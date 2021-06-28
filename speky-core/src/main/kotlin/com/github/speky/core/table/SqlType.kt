package com.github.speky.core.table

import com.github.speky.core.Show

/**
 * Raw sql type in relational database.
 *
 * @see [java.sql.Types]
 */
sealed interface SqlType {

  companion object {
    /**
     * [Show] instance for [SqlType].
     */
    val show: Show<SqlType> = Show {
      when (this) {
        Array                 -> Array::class.simpleName!!.lowercase()
        Bigint                -> Bigint::class.simpleName!!.lowercase()
        Binary                -> Binary::class.simpleName!!.lowercase()
        Blob                  -> Blob::class.simpleName!!.lowercase()
        Boolean               -> Boolean::class.simpleName!!.lowercase()
        Date                  -> Date::class.simpleName!!.lowercase()
        Decimal               -> Decimal::class.simpleName!!.lowercase()
        Double                -> Double::class.simpleName!!.lowercase()
        Float                 -> Float::class.simpleName!!.lowercase()
        Integer               -> Integer::class.simpleName!!.lowercase()
        LongVarchar           -> LongVarchar::class.simpleName!!.lowercase()
        Numeric               -> Numeric::class.simpleName!!.lowercase()
        Other                 -> Other::class.simpleName!!.lowercase()
        Real                  -> Real::class.simpleName!!.lowercase()
        SmallInt              -> SmallInt::class.simpleName!!.lowercase()
        Time                  -> Time::class.simpleName!!.lowercase()
        TimeWithTimeZone      -> TimeWithTimeZone::class.simpleName!!.lowercase()
        Timestamp             -> Timestamp::class.simpleName!!.lowercase()
        TimestampWithTimeZone -> TimestampWithTimeZone::class.simpleName!!.lowercase()
        Varchar               -> Varchar::class.simpleName!!.lowercase()
      }
    }
  }

  /**
   * @see [java.sql.Types.SMALLINT].
   */
  object SmallInt : SqlType

  /**
   * @see [java.sql.Types.INTEGER].
   */
  object Integer : SqlType

  /**
   * @see [java.sql.Types.BIGINT].
   */
  object Bigint : SqlType

  /**
   * @see [java.sql.Types.FLOAT].
   */
  object Float : SqlType

  /**
   * @see [java.sql.Types.REAL].
   */
  object Real : SqlType

  /**
   * @see [java.sql.Types.DOUBLE].
   */
  object Double : SqlType

  /**
   * @see [java.sql.Types.NUMERIC].
   */
  object Numeric : SqlType

  /**
   * @see [java.sql.Types.DECIMAL].
   */
  object Decimal : SqlType

  /**
   * @see [java.sql.Types.VARCHAR].
   */
  object Varchar : SqlType

  /**
   * @see [java.sql.Types.LONGVARCHAR].
   */
  object LongVarchar : SqlType

  /**
   * @see [java.sql.Types.DATE].
   */
  object Date : SqlType

  /**
   * @see [java.sql.Types.TIME].
   */
  object Time : SqlType

  /**
   * @see [java.sql.Types.TIMESTAMP].
   */
  object Timestamp : SqlType

  /**
   * @see [java.sql.Types.BINARY].
   */
  object Binary : SqlType

  /**
   * @see [java.sql.Types.OTHER].
   */
  object Other : SqlType

  /**
   * @see [java.sql.Types.ARRAY].
   */
  object Array : SqlType

  /**
   * @see [java.sql.Types.BLOB].
   */
  object Blob : SqlType

  /**
   * @see [java.sql.Types.BOOLEAN].
   */
  object Boolean : SqlType

  /**
   * @see [java.sql.Types.TIME_WITH_TIMEZONE].
   */
  object TimeWithTimeZone : SqlType

  /**
   * @see [java.sql.Types.TIMESTAMP_WITH_TIMEZONE].
   */
  object TimestampWithTimeZone : SqlType
}
