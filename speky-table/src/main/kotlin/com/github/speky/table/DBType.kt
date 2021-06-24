package com.github.speky.table

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
}
