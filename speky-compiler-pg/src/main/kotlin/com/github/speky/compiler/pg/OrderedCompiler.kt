package com.github.speky.compiler.pg

import com.github.speky.compiler.pg.internal.PgTerm
import com.github.speky.compiler.pg.internal.find
import com.github.speky.compiler.pg.resolve.ColumnResolver
import com.github.speky.core.specification.Alias
import com.github.speky.core.specification.Order
import com.github.speky.core.specification.Order.Ascending
import com.github.speky.core.specification.Order.Descending
import com.github.speky.core.specification.Ordered

internal class OrderedCompiler(private val compiler: PgSpecificationCompiler) :
  PgAwareCompiler<Ordered<*, *>>, ColumnResolver by compiler {

  override fun Ordered<*, *>.compile(): PgTerm =
    PgTerm(with(compiler) { delegate.compile() }) + orderBy() + order.compile(alias)

  private fun Order<*, *>.compile(alias: Alias<*>): PgTerm =
    when (this) {
      is Ascending  ->
        PgTerm(alias.find(lens.propertyRef).value) + dot() + lens.propertyRef.columnName()

      is Descending ->
        PgTerm(alias.find(lens.propertyRef).value) + dot() + lens.propertyRef.columnName() + desc()
    }
}
