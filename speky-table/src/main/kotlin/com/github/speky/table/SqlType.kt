package com.github.speky.table

/**
 * Raw sql type in relational database.
 *
 * @see [java.sql.Types]
 */
sealed interface SqlType {

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
