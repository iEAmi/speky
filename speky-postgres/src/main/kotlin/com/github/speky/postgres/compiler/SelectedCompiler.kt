package com.github.speky.postgres.compiler

import com.github.speky.core.specification.Selected
import com.github.speky.core.table.ColumnResolver
import com.github.speky.postgres.compiler.internal.PgTerm
import com.github.speky.postgres.compiler.internal.WithPgTerms
import com.github.speky.postgres.compiler.internal.find

/**
 * Internal compiler for [Selected].
 */
internal class SelectedCompiler(private val compiler: PgSpecificationCompiler) :
  WithPgTerms, ColumnResolver by compiler {
  fun Selected<*>.compile(): PgTerm = when (this) {
    is Selected.All  -> select() + all() + with(compiler) { source.compile() }
    is Selected.Some -> select() + columnNames() + with(compiler) { source.compile() }
  }

  private fun Selected.Some<*>.columnNames(): String = lenses.joinToString(prefix = " ") {
    with(it.propertyRef) { "${alias.find(this).value}.${columnName()}" }
  }
}
