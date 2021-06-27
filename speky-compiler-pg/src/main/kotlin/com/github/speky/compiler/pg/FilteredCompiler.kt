package com.github.speky.compiler.pg

import com.github.speky.compiler.pg.internal.PgTerm
import com.github.speky.compiler.pg.internal.find
import com.github.speky.compiler.pg.resolve.ColumnResolver
import com.github.speky.core.specification.Alias
import com.github.speky.core.specification.Filter
import com.github.speky.core.specification.Filtered

internal class FilteredCompiler(private val compiler: PgSpecificationCompiler) :
  PgAwareCompiler<Filtered<*, *>>, ColumnResolver by compiler {
  override fun Filtered<*, *>.compile(): PgTerm =
    PgTerm(with(compiler) { delegate.compile() }) + where() + filter.compile(alias)

  private fun Filter<*, *>.compile(alias: Alias<*>): String {
    val aliasStr = alias.find(lens.propertyRef).value
    val propName = lens.propertyRef.columnName()

    return when (this) {
      is Filter.Equal            -> "$aliasStr.$propName = ${wrapInQuotes(value)}"
      is Filter.NotEqual         -> "$aliasStr.$propName != ${wrapInQuotes(value)}"
      is Filter.GreaterThan      -> "$aliasStr.$propName > ${wrapInQuotes(value)}"
      is Filter.GreaterThanEqual -> "$aliasStr.$propName >= ${wrapInQuotes(value)}"
      is Filter.LessThan         -> "$aliasStr.$propName < ${wrapInQuotes(value)}"
      is Filter.LessThanEqual    -> "$aliasStr.$propName <= ${wrapInQuotes(value)}"
      is Filter.Like             -> "$aliasStr.$propName LIKE '%$value%'"
      is Filter.Contains         -> "$aliasStr.$propName LIKE '%$value%'"
      is Filter.StartsWith       -> "$aliasStr.$propName LIKE '$value%'"
      is Filter.EndsWith         -> "$aliasStr.$propName LIKE '%$value'"
    }
  }

  fun wrapInQuotes(a: Any?): Any? = if (a is String? || a is String) "'$a'" else a
}
