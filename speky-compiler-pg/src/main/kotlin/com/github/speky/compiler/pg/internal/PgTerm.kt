@file:Suppress(
  "DANGEROUS_CHARACTERS",
  "ObjectPropertyName",
  "MethodOverloading",
  "ModifierOrder"
)

package com.github.speky.compiler.pg.internal

import com.github.speky.core.specification.Alias

/**
 * Simple value class that holds [String]. this is exist just for simple compiling
 */
@JvmInline
internal value class PgTerm(private val value: String) {
  /**
   * Plus operator for [PgTerm].
   */
  operator fun plus(other: PgTerm): PgTerm = PgTerm(value + other.value)

  /**
   * Plus operator for [Int].
   */
  operator fun plus(other: Int): PgTerm = PgTerm(value + "$other")

  /**
   * Plus operator for [String].
   */
  operator fun plus(other: String): PgTerm = PgTerm("$value$other")

  /**
   * Plus operator for [Alias.Single<*>].
   */
  operator fun plus(other: Alias.Single<*>): PgTerm = PgTerm("$value${other.value}")

  /**
   * Plus operator for [Alias<*>].
   */
  operator fun plus(other: Alias<*>): PgTerm = this + other as Alias.Single<*>

  override fun toString(): String = value
}
