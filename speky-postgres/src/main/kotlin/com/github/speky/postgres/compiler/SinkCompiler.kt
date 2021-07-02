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
        values() + lBracket() + cValues() + rBracket()
    is Sink.Update -> TODO()
  }

  private fun Sink.Insert<*>.columns(): String =
    values.joinToString { it.lens.propertyRef.name }

  private fun Sink.Insert<*>.cValues(): String =
    values.map { wrapInQuotes(it.value) }.joinToString()
}
