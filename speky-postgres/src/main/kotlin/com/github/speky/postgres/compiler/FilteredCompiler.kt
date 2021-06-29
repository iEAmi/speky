@file:Suppress("StringLiteralDuplication")

package com.github.speky.postgres.compiler

import com.github.speky.core.specification.Alias
import com.github.speky.core.specification.Filter
import com.github.speky.core.specification.Filtered
import com.github.speky.core.table.ColumnResolver
import com.github.speky.postgres.compiler.internal.PgTerm
import com.github.speky.postgres.compiler.internal.WithPgTerms
import com.github.speky.postgres.compiler.internal.find

/**
 * Internal compiler for [Filtered].
 */
internal class FilteredCompiler(private val compiler: PgSpecificationCompiler) : WithPgTerms,
  ColumnResolver by compiler {

  fun Filtered<*, *>.compile(): PgTerm =
    PgTerm(with(compiler) { delegate.compile() }) + where() + filter.compile(alias)

  private fun Filter<*, *>.compile(alias: Alias<*>): PgTerm {
    val aliasStr = PgTerm(alias.find(lens.propertyRef).value)
    val propName = lens.propertyRef.columnsName().first()
    val firstPart = aliasStr + dot() + propName

    return when (this) {
      is Filter.Equal            -> firstPart + eq() + "${wrapInQuotes(value)}"
      is Filter.NotEqual         -> firstPart + neq() + "${wrapInQuotes(value)}"
      is Filter.GreaterThan      -> firstPart + gt() + "${wrapInQuotes(value)}"
      is Filter.GreaterThanEqual -> firstPart + gte() + "${wrapInQuotes(value)}"
      is Filter.LessThan         -> firstPart + lt() + "${wrapInQuotes(value)}"
      is Filter.LessThanEqual    -> firstPart + lte() + "${wrapInQuotes(value)}"
      is Filter.Like             -> firstPart + like() + "'%$value%'"
      is Filter.Contains         -> firstPart + like() + "'%$value%'"
      is Filter.StartsWith       -> firstPart + like() + "'$value%'"
      is Filter.EndsWith         -> firstPart + like() + "'%$value'"
    }
  }

  private fun wrapInQuotes(a: Any?): Any? = if (a is String? || a is String) "'$a'" else a
}
