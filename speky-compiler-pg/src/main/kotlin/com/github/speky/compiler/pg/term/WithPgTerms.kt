@file:Suppress("PropertyName", "ObjectPropertyName")

package com.github.speky.compiler.pg.term

internal interface WithPgTerms {
  fun select(): PgTerm = SELECT
  fun all(prefix: String = " ", postFix: String = ""): PgTerm = PgTerm(prefix) + ALL + postFix
  fun from(prefix: String = "\n", postFix: String = " "): PgTerm = PgTerm(prefix) + FROM + postFix
  fun `as`(prefix: String = " ", postFix: String = " "): PgTerm = PgTerm(prefix) + AS + postFix
  fun crossJoin(prefix: String = "\n\t\t ", postFix: String = " "): PgTerm =
    PgTerm(prefix) + CROSS_JOIN + postFix

  fun innerJoin(prefix: String = "\n\t\t ", postFix: String = " "): PgTerm =
    PgTerm(prefix) + INNER_JOIN + postFix

  fun on(prefix: String = " ", postFix: String = " "): PgTerm = PgTerm(prefix) + ON + postFix

  fun comma(postFix: String = " "): PgTerm = COMMA + postFix
  fun dot(): PgTerm = DOT
  fun eq(prefix: String = " ", postFix: String = " "): PgTerm = PgTerm(prefix) + `=` + postFix

  companion object {
    val SELECT: PgTerm = PgTerm("SELECT")
    val ALL: PgTerm = PgTerm("*")
    val FROM: PgTerm = PgTerm("FROM")
    val AS: PgTerm = PgTerm("AS")
    val CROSS_JOIN: PgTerm = PgTerm("CROSS JOIN")
    val INNER_JOIN: PgTerm = PgTerm("INNER JOIN")
    val ON: PgTerm = PgTerm("ON")

    val DOT: PgTerm = PgTerm(".")
    val COMMA: PgTerm = PgTerm(",")
    val `=`: PgTerm = PgTerm("=")
  }
}
