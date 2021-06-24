package com.github.speky.table

import java.time.LocalDateTime

/**
 * Presets database type in speky.
 *
 * @param T kotlin equivalent type
 */
interface DBType<T> {

  /**
   * Integer type in relational databases.
   */
  object Int : DBType<kotlin.Int>

  /**
   * BigInteger type in relational databases.
   */
  object Bigint : DBType<Long>

  /**
   * Varchar type in relational databases.
   */
  object Varchar : DBType<String>

  /**
   * Text type in relational databases.
   */
  object Text : DBType<String>

  /**
   * Boolean type in relational databases.
   */
  object Boolean : DBType<kotlin.Boolean>

  /**
   * Timestampz type in postgre databases.
   */
  object UUID : DBType<java.util.UUID>

  /**
   * Timestamp type in relational databases.
   */
  object Timestamp : DBType<LocalDateTime>

  /**
   * Timestampz type in postgre databases.
   */
  object Timestampz : DBType<LocalDateTime>
}
