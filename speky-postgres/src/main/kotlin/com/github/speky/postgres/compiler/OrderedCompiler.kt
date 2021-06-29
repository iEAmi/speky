package com.github.speky.postgres.compiler

import com.github.speky.core.specification.Alias
import com.github.speky.core.specification.Order
import com.github.speky.core.specification.Order.Ascending
import com.github.speky.core.specification.Order.Descending
import com.github.speky.core.specification.Ordered
import com.github.speky.core.table.ColumnResolver
import com.github.speky.postgres.compiler.internal.PgTerm
import com.github.speky.postgres.compiler.internal.WithPgTerms
import com.github.speky.postgres.compiler.internal.find

/**
 * Internal compiler for [Ordered].
 */
internal class OrderedCompiler(private val compiler: PgSpecificationCompiler) :
  WithPgTerms, ColumnResolver by compiler {

  fun Ordered<*, *>.compile(): PgTerm =
    PgTerm(with(compiler) { delegate.compile() }) + orderBy() + order.compile(alias)

  private fun Order<*, *>.compile(alias: Alias<*>): PgTerm =
    when (this) {
      is Ascending  ->
        PgTerm(alias.find(lens.propertyRef).value) + dot() + lens.propertyRef.columnsName().first()

      is Descending ->
        PgTerm(alias.find(lens.propertyRef).value) + dot() +
            lens.propertyRef.columnsName().first() + desc()
    }
}
