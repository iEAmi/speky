package com.github.speky.compiler.pg

import com.github.speky.compiler.pg.internal.PgTerm
import com.github.speky.compiler.pg.internal.find
import com.github.speky.compiler.pg.resolve.ColumnResolver
import com.github.speky.core.specification.Selected

/**
 * Internal compiler for [Selected].
 */
internal class SelectedCompiler(private val compiler: PgSpecificationCompiler) :
  PgAwareCompiler<Selected<*>>, ColumnResolver by compiler {
  override fun Selected<*>.compile(): PgTerm = when (this) {
    is Selected.All  -> select() + all() + with(compiler) { source.compile() }
    is Selected.Some -> select() + columnNames() + with(compiler) { source.compile() }
  }

  private fun Selected.Some<*>.columnNames(): String = lenses.joinToString(prefix = " ") {
    with(it.propertyRef) { "${alias.find(this).value}.${columnName()}" }
  }
}
