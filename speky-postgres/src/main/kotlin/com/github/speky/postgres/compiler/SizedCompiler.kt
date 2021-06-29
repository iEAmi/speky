package com.github.speky.postgres.compiler

import com.github.speky.core.specification.Sized
import com.github.speky.postgres.compiler.internal.PgTerm
import com.github.speky.postgres.compiler.internal.WithPgTerms

/**
 * Internal compiler for [Sized].
 */
internal class SizedCompiler(private val compiler: PgSpecificationCompiler) : WithPgTerms {
  fun Sized<*, *>.compile(): PgTerm =
    PgTerm(with(compiler) { delegate.compile() }) + limit() + limit + offset() + offset
}
