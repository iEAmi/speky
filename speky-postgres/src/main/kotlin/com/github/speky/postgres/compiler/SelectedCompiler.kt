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
    is Selected.Some -> select() + findColumnNames() + with(compiler) { source.compile() }
  }

  private fun Selected.Some<*>.findColumnNames(): String {
    return lenses.joinToString(prefix = " ") {
      with(it.propertyRef) {
        val aliasValue = alias.find(this).value
        val names = columnsName()

        if (names.size == 1) "$aliasValue.${names.first()}"
        else names.joinToString { name -> "$aliasValue.$name" }
      }
    }
  }
}
