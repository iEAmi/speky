package com.github.speky.postgres.compiler

import com.github.speky.core.specification.Sink
import com.github.speky.core.table.ColumnResolver
import com.github.speky.core.table.TableResolver
import com.github.speky.postgres.compiler.internal.PgTerm
import com.github.speky.postgres.compiler.internal.WithPgTerms

internal class SinkCompiler(private val compiler: PgSpecificationCompiler) :
  WithPgTerms, ColumnUtil, TableResolver by compiler, ColumnResolver by compiler {

  fun Sink<*>.compile(): PgTerm = when (this) {
    is Sink.Insert -> insertInto() + alias.tableName() + lBracket() + columns() + rBracket() +
        values(postFix = " ") + cValues()
    is Sink.Update -> TODO()
  }

  private fun Sink.Insert<*>.columns(): String =
    // in insert only one column is available
    values.map { it.lens.propertyRef.columnsName().first() }.distinct().joinToString()

  @Suppress("UNCHECKED_CAST")
  private fun Sink.Insert<*>.cValues(): String {
    val group = values.groupBy({ it.lens.propertyRef.name }) { it.value }
    val valueGroup: List<Pair<*, *>> = group.values.fold(emptyList<Any?>()) { acc, list ->
      if (acc.isEmpty()) list else acc.zip(list)
    } as List<Pair<*, *>>

    return valueGroup.flatten().joinToString(separator = ",\n") { list ->
      list.map { wrapInQuotes(it) }.joinToString(
        prefix = lBracket(prefix = "").toString(),
        postfix = rBracket().toString()
      )
    }
  }

  private fun List<Pair<*, *>>.flatten(): List<List<*>> = map { it.flatten() }

  private fun Pair<*, *>.flatten(): List<Any?> {
    val firstList = if (first is Pair<*, *>)
      (first as Pair<*, *>).flatten()
    else
      listOf(first)

    val secondList = if (second is Pair<*, *>)
      (second as Pair<*, *>).flatten()
    else
      listOf(second)

    return firstList + secondList
  }
}
