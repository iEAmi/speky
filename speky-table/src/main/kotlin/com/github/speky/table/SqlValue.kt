package com.github.speky.table

import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime

/**
 * Presents value comes from sql.
 *
 * @property type [SqlType] of the value.
 */
sealed class SqlValue private constructor(val type: SqlType) {

  /**
   * Value with type [SqlType.SmallInt].
   *
   * @property value kotlin value
   */
  data class Short(val value: kotlin.Short) : SqlValue(SqlType.SmallInt) {
    companion object {
      /**
       * [ValueTransformer] instance for [Short].
       */
      val transformer: ValueTransformer<kotlin.Short, Short> =
        object : ValueTransformer<kotlin.Short, Short> {
          override fun toSqlValue(value: kotlin.Short): Short = Short(value)
          override fun fromSqlValue(sqlValue: Short): kotlin.Short = sqlValue.value
        }
    }
  }

  /**
   * Value with type [SqlType.Integer].
   *
   * @property value kotlin value
   */
  data class Int(val value: kotlin.Int) : SqlValue(SqlType.Integer) {
    companion object {
      /**
       * [ValueTransformer] instance for [Int].
       */
      val transformer: ValueTransformer<kotlin.Int, Int> =
        object : ValueTransformer<kotlin.Int, Int> {
          override fun toSqlValue(value: kotlin.Int): Int = Int(value)
          override fun fromSqlValue(sqlValue: Int): kotlin.Int = sqlValue.value
        }
    }
  }

  /**
   * Value with type [SqlType.Bigint].
   *
   * @property value kotlin value
   */
  data class Bigint(val value: Long) : SqlValue(SqlType.Bigint) {
    companion object {
      /**
       * [ValueTransformer] instance for [Bigint].
       */
      val transformer: ValueTransformer<Long, Bigint> =
        object : ValueTransformer<Long, Bigint> {
          override fun toSqlValue(value: Long): Bigint = Bigint(value)
          override fun fromSqlValue(sqlValue: Bigint): Long = sqlValue.value
        }
    }
  }

  /**
   * Value with type [SqlType.Float].
   *
   * @property value kotlin value
   */
  data class Float(val value: kotlin.Float) : SqlValue(SqlType.Float) {
    companion object {
      /**
       * [ValueTransformer] instance for [Float].
       */
      val transformer: ValueTransformer<kotlin.Float, Float> =
        object : ValueTransformer<kotlin.Float, Float> {
          override fun toSqlValue(value: kotlin.Float): Float = Float(value)
          override fun fromSqlValue(sqlValue: Float): kotlin.Float = sqlValue.value
        }
    }
  }

  /**
   * Value with type [SqlType.Real].
   *
   * @property value kotlin value
   */
  data class Real(val value: kotlin.Double) : SqlValue(SqlType.Real) {
    companion object {
      /**
       * [ValueTransformer] instance for [Real].
       */
      val transformer: ValueTransformer<kotlin.Double, Real> =
        object : ValueTransformer<kotlin.Double, Real> {
          override fun toSqlValue(value: kotlin.Double): Real = Real(value)
          override fun fromSqlValue(sqlValue: Real): kotlin.Double = sqlValue.value
        }
    }
  }

  /**
   * Value with type [SqlType.Double].
   *
   * @property value kotlin value
   */
  data class Double(val value: kotlin.Double) : SqlValue(SqlType.Double) {
    companion object {
      /**
       * [ValueTransformer] instance for [Double].
       */
      val transformer: ValueTransformer<kotlin.Double, Double> =
        object : ValueTransformer<kotlin.Double, Double> {
          override fun toSqlValue(value: kotlin.Double): Double = Double(value)
          override fun fromSqlValue(sqlValue: Double): kotlin.Double = sqlValue.value
        }
    }
  }

  /**
   * Value with type [SqlType.Numeric].
   *
   * @property value kotlin value
   */
  data class Numeric(val value: kotlin.Double) : SqlValue(SqlType.Numeric) {
    companion object {
      /**
       * [ValueTransformer] instance for [Numeric].
       */
      val transformer: ValueTransformer<kotlin.Double, Numeric> =
        object : ValueTransformer<kotlin.Double, Numeric> {
          override fun toSqlValue(value: kotlin.Double): Numeric = Numeric(value)
          override fun fromSqlValue(sqlValue: Numeric): kotlin.Double = sqlValue.value
        }
    }
  }

  /**
   * Value with type [SqlType.Decimal].
   *
   * @property value kotlin value
   */
  data class Decimal(val value: BigDecimal) : SqlValue(SqlType.Decimal) {
    companion object {
      /**
       * [ValueTransformer] instance for [Decimal].
       */
      val transformer: ValueTransformer<BigDecimal, Decimal> =
        object : ValueTransformer<BigDecimal, Decimal> {
          override fun toSqlValue(value: BigDecimal): Decimal = Decimal(value)
          override fun fromSqlValue(sqlValue: Decimal): BigDecimal = sqlValue.value
        }
    }
  }

  /**
   * Value with type [SqlType.Varchar].
   *
   * @property value kotlin value
   */
  data class Varchar(val value: String) : SqlValue(SqlType.Varchar) {
    companion object {
      /**
       * [ValueTransformer] instance for [Varchar].
       */
      val transformer: ValueTransformer<String, Varchar> =
        object : ValueTransformer<String, Varchar> {
          override fun toSqlValue(value: String): Varchar = Varchar(value)
          override fun fromSqlValue(sqlValue: Varchar): String = sqlValue.value
        }
    }
  }

  /**
   * Value with type [SqlType.LongVarchar].
   *
   * @property value kotlin value
   */
  data class Text(val value: String) : SqlValue(SqlType.LongVarchar) {
    companion object {
      /**
       * [ValueTransformer] instance for [Text].
       */
      val transformer: ValueTransformer<String, Text> =
        object : ValueTransformer<String, Text> {
          override fun toSqlValue(value: String): Text = Text(value)
          override fun fromSqlValue(sqlValue: Text): String = sqlValue.value
        }
    }
  }

  /**
   * Value with type [SqlType.Date].
   *
   * @property value kotlin value
   */
  data class Date(val value: java.util.Date) : SqlValue(SqlType.Date) {
    companion object {
      /**
       * [ValueTransformer] instance for [Date].
       */
      val transformer: ValueTransformer<java.util.Date, Date> =
        object : ValueTransformer<java.util.Date, Date> {
          override fun toSqlValue(value: java.util.Date): Date = Date(value)
          override fun fromSqlValue(sqlValue: Date): java.util.Date = sqlValue.value
        }
    }
  }

  /**
   * Value with type [SqlType.Time].
   *
   * @property value kotlin value
   */
  data class Time(val value: java.sql.Time) : SqlValue(SqlType.Time) {
    companion object {
      /**
       * [ValueTransformer] instance for [Time].
       */
      val transformer: ValueTransformer<java.sql.Time, Time> =
        object : ValueTransformer<java.sql.Time, Time> {
          override fun toSqlValue(value: java.sql.Time): Time = Time(value)
          override fun fromSqlValue(sqlValue: Time): java.sql.Time = sqlValue.value
        }
    }
  }

  /**
   * Value with type [SqlType.Timestamp].
   *
   * @property value kotlin value
   */
  data class Timestamp(val value: Instant) : SqlValue(SqlType.Timestamp) {
    companion object {
      /**
       * [ValueTransformer] instance for [Timestamp].
       */
      val transformer: ValueTransformer<Instant, Timestamp> =
        object : ValueTransformer<Instant, Timestamp> {
          override fun toSqlValue(value: Instant): Timestamp = Timestamp(value)
          override fun fromSqlValue(sqlValue: Timestamp): Instant = sqlValue.value
        }
    }
  }

  /**
   * Value with type [SqlType.Binary].
   *
   * @property value kotlin value
   */
  class Binary(val value: ByteArray) : SqlValue(SqlType.Binary) {
    companion object {
      /**
       * [ValueTransformer] instance for [Binary].
       */
      val transformer: ValueTransformer<ByteArray, Binary> =
        object : ValueTransformer<ByteArray, Binary> {
          override fun toSqlValue(value: ByteArray): Binary = Binary(value)
          override fun fromSqlValue(sqlValue: Binary): ByteArray = sqlValue.value
        }
    }
  }

  /**
   * Value with type [SqlType.Other].
   *
   * @property value kotlin value
   */
  data class UUID(val value: java.util.UUID) : SqlValue(SqlType.Other) {
    companion object {
      /**
       * [ValueTransformer] instance for [UUID].
       */
      val transformer: ValueTransformer<java.util.UUID, UUID> =
        object : ValueTransformer<java.util.UUID, UUID> {
          override fun toSqlValue(value: java.util.UUID): UUID = UUID(value)
          override fun fromSqlValue(sqlValue: UUID): java.util.UUID = sqlValue.value
        }
    }
  }

  /**
   * Value with type [SqlType.Array].
   *
   * @property value kotlin value
   */
  class Array(val value: kotlin.Array<out Any>) : SqlValue(SqlType.Array) {
    companion object {
      /**
       * [ValueTransformer] instance for [Array].
       */
      val transformer: ValueTransformer<kotlin.Array<out Any>, Array> =
        object : ValueTransformer<kotlin.Array<out Any>, Array> {
          override fun toSqlValue(value: kotlin.Array<out Any>): Array = Array(value)
          override fun fromSqlValue(sqlValue: Array): kotlin.Array<out Any> = sqlValue.value
        }
    }
  }

  /**
   * Value with type [SqlType.Blob].
   *
   * @property value kotlin value
   */
  class Blob(val value: ByteArray) : SqlValue(SqlType.Blob) {
    companion object {
      /**
       * [ValueTransformer] instance for [Blob].
       */
      val transformer: ValueTransformer<ByteArray, Blob> =
        object : ValueTransformer<ByteArray, Blob> {
          override fun toSqlValue(value: ByteArray): Blob = Blob(value)
          override fun fromSqlValue(sqlValue: Blob): ByteArray = sqlValue.value
        }
    }
  }

  /**
   * Value with type [SqlType.Boolean].
   *
   * @property value kotlin value
   */
  data class Boolean(val value: kotlin.Boolean) : SqlValue(SqlType.Boolean) {
    companion object {
      /**
       * [ValueTransformer] instance for [Boolean].
       */
      val transformer: ValueTransformer<kotlin.Boolean, Boolean> =
        object : ValueTransformer<kotlin.Boolean, Boolean> {
          override fun toSqlValue(value: kotlin.Boolean): Boolean = Boolean(value)
          override fun fromSqlValue(sqlValue: Boolean): kotlin.Boolean = sqlValue.value
        }
    }
  }

  /**
   * Value with type [SqlType.TimeWithTimeZone].
   *
   * @property value kotlin value
   */
  data class Timez(val value: LocalTime) : SqlValue(SqlType.TimeWithTimeZone) {
    companion object {
      /**
       * [ValueTransformer] instance for [Timez].
       */
      val transformer: ValueTransformer<LocalTime, Timez> =
        object : ValueTransformer<LocalTime, Timez> {
          override fun toSqlValue(value: LocalTime): Timez = Timez(value)
          override fun fromSqlValue(sqlValue: Timez): LocalTime = sqlValue.value
        }
    }
  }

  /**
   * Value with type [SqlType.TimestampWithTimeZone].
   *
   * @property value kotlin value
   */
  data class Timestampz(val value: LocalDateTime) : SqlValue(SqlType.TimestampWithTimeZone) {
    companion object {
      /**
       * [ValueTransformer] instance for [Timestampz].
       */
      val transformer: ValueTransformer<LocalDateTime, Timestampz> =
        object : ValueTransformer<LocalDateTime, Timestampz> {
          override fun toSqlValue(value: LocalDateTime): Timestampz = Timestampz(value)
          override fun fromSqlValue(sqlValue: Timestampz): LocalDateTime = sqlValue.value
        }
    }
  }
}
