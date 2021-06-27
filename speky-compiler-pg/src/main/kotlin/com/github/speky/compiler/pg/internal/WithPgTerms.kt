@file:Suppress("StringLiteralDuplication", "ComplexInterface")

package com.github.speky.compiler.pg.internal

/**
 * Internal helper class.
 */
internal interface WithPgTerms {
  fun select(): PgTerm = SELECT
  fun all(prefix: String = " ", postFix: String = ""): PgTerm = PgTerm(prefix) + ALL + postFix
  fun from(prefix: String = "\n", postFix: String = " "): PgTerm = PgTerm(prefix) + FROM + postFix
  fun where(prefix: String = "\n", postFix: String = " "): PgTerm = PgTerm(prefix) + WHERE + postFix
  fun `as`(prefix: String = " ", postFix: String = " "): PgTerm = PgTerm(prefix) + AS + postFix
  fun limit(prefix: String = "\n", postFix: String = " "): PgTerm = PgTerm(prefix) + LIMIT + postFix
  fun offset(prefix: String = " ", postFix: String = " "): PgTerm =
    PgTerm(prefix) + OFFSET + postFix

  fun crossJoin(prefix: String = "\n\t\t ", postFix: String = " "): PgTerm =
    PgTerm(prefix) + CROSS_JOIN + postFix

  fun innerJoin(prefix: String = "\n\t\t ", postFix: String = " "): PgTerm =
    PgTerm(prefix) + INNER_JOIN + postFix

  fun orderBy(prefix: String = "\n", postFix: String = " "): PgTerm =
    PgTerm(prefix) + ORDER_BY + postFix

  fun on(prefix: String = " ", postFix: String = " "): PgTerm = PgTerm(prefix) + ON + postFix
  fun desc(prefix: String = " ", postFix: String = ""): PgTerm = PgTerm(prefix) + DESC + postFix

  fun comma(postFix: String = " "): PgTerm = COMMA + postFix
  fun dot(): PgTerm = DOT
  fun eq(prefix: String = " ", postFix: String = " "): PgTerm = PgTerm(prefix) + EQ + postFix
  fun neq(prefix: String = " ", postFix: String = " "): PgTerm = PgTerm(prefix) + NEQ + postFix
  fun gt(prefix: String = " ", postFix: String = " "): PgTerm = PgTerm(prefix) + GT + postFix
  fun gte(prefix: String = " ", postFix: String = " "): PgTerm = PgTerm(prefix) + GTE + postFix
  fun lt(prefix: String = " ", postFix: String = " "): PgTerm = PgTerm(prefix) + LT + postFix
  fun lte(prefix: String = " ", postFix: String = " "): PgTerm = PgTerm(prefix) + LTE + postFix
  fun like(prefix: String = " ", postFix: String = " "): PgTerm = PgTerm(prefix) + LIKE + postFix

  private companion object {
    private val SELECT: PgTerm = PgTerm("SELECT")
    private val ALL: PgTerm = PgTerm("*")
    private val FROM: PgTerm = PgTerm("FROM")
    private val WHERE: PgTerm = PgTerm("WHERE")
    private val AS: PgTerm = PgTerm("AS")
    private val LIMIT: PgTerm = PgTerm("LIMIT")
    private val OFFSET: PgTerm = PgTerm("OFFSET")
    private val CROSS_JOIN: PgTerm = PgTerm("CROSS JOIN")
    private val INNER_JOIN: PgTerm = PgTerm("INNER JOIN")
    private val ORDER_BY: PgTerm = PgTerm("ORDER BY")
    private val ON: PgTerm = PgTerm("ON")
    private val DESC: PgTerm = PgTerm("DESC")
    private val DOT: PgTerm = PgTerm(".")
    private val COMMA: PgTerm = PgTerm(",")
    private val EQ: PgTerm = PgTerm("=")
    private val NEQ: PgTerm = PgTerm("!=")
    private val GT: PgTerm = PgTerm(">")
    private val GTE: PgTerm = PgTerm(">=")
    private val LT: PgTerm = PgTerm("<")
    private val LTE: PgTerm = PgTerm("<=")
    private val LIKE: PgTerm = PgTerm("LIKE")
  }
}
