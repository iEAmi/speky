package com.github.speky.compiler.pg

import com.github.speky.compiler.pg.internal.PgTerm
import com.github.speky.compiler.pg.resolve.TableResolver
import com.github.speky.core.specification.Source

/**
 * Internal compiler for [Source].
 */
internal class SourceCompiler(private val compiler: PgSpecificationCompiler) :
  PgAwareCompiler<Source<*>>, TableResolver by compiler {

  override fun Source<*>.compile(): PgTerm = when (this) {
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
