package com.github.speky.postgres.compiler

import com.github.speky.core.specification.Source
import com.github.speky.core.table.TableResolver
import com.github.speky.postgres.compiler.internal.PgTerm
import com.github.speky.postgres.compiler.internal.WithPgTerms

/**
 * Internal compiler for [Source].
 */
internal class SourceCompiler(private val compiler: PgSpecificationCompiler) : WithPgTerms,
  TableResolver by compiler {

  fun Source<*>.compile(): PgTerm = when (this) {
    is Source.Mix.CrossJoin<*, *, *>    ->
      from() + alias.left.tableName() + `as`() + alias.left +
          crossJoin() + alias.right.tableName() + `as`() + alias.right

    is Source.Mix.InnerJoin<*, *, *, *> -> this.compileInnerJoin()

    is Source.Mix.Multiply<*, *, *>     ->
      from() + alias.left.tableName() + `as`() + alias.left +
          comma() + alias.right.tableName() + `as`() + alias.right

    is Source.Single                    ->
      from() + alias.tableName() + `as`() + alias
  }

  private fun Source.Mix.InnerJoin<*, *, *, *>.compileInnerJoin(): PgTerm {
    val leftTable = alias.left.tableName()
    val rightTable = alias.right.tableName()

    return from() + leftTable + `as`() + alias.left +
        innerJoin() + rightTable + `as`() + alias.right +
        on() + alias.left + dot() + leftSelector.propertyRef.name +
        eq() + alias.right + dot() + rightSelector.propertyRef.name
  }
}
