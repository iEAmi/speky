@file:Suppress("StringLiteralDuplication")
package com.github.speky.compiler.pg

import com.github.speky.compiler.pg.internal.PgTerm
import com.github.speky.compiler.pg.internal.find
import com.github.speky.compiler.pg.resolve.ColumnResolver
import com.github.speky.core.specification.Alias
import com.github.speky.core.specification.Filter
import com.github.speky.core.specification.Filtered

/**
 * Internal compiler for [Filtered].
 */
internal class FilteredCompiler(private val compiler: PgSpecificationCompiler) :
  PgAwareCompiler<Filtered<*, *>>, ColumnResolver by compiler {
  override fun Filtered<*, *>.compile(): PgTerm =
    PgTerm(with(compiler) { delegate.compile() }) + where() + filter.compile(alias)

  private fun Filter<*, *>.compile(alias: Alias<*>): PgTerm {
    val aliasStr = PgTerm(alias.find(lens.propertyRef).value)
    val propName = lens.propertyRef.columnName()
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
