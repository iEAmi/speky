@file:Suppress("DANGEROUS_CHARACTERS", "ObjectPropertyName")

package com.github.speky.compiler.pg.internal

import com.github.speky.core.specification.Alias

@JvmInline
internal value class PgTerm(private val value: String) {
  operator fun plus(other: PgTerm): PgTerm = PgTerm(value + other.value)
  operator fun plus(other: Int): PgTerm = PgTerm(value + "$other")
  operator fun plus(other: String): PgTerm = PgTerm("$value$other")
  operator fun plus(other: Alias.Single<*>): PgTerm = PgTerm("$value${other.value}")
  operator fun plus(other: Alias<*>): PgTerm = this + (other as Alias.Single<*>)

  override fun toString(): String = value
}
