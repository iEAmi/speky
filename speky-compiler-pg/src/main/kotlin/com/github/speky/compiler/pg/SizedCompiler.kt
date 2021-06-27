package com.github.speky.compiler.pg

import com.github.speky.compiler.pg.internal.PgTerm
import com.github.speky.core.specification.Sized

/**
 * Internal compiler for [Sized].
 */
internal class SizedCompiler(private val compiler: PgSpecificationCompiler) :
  PgAwareCompiler<Sized<*, *>> {
  override fun Sized<*, *>.compile(): PgTerm =
    PgTerm(with(compiler) { delegate.compile() }) + limit() + limit + offset() + offset
}
