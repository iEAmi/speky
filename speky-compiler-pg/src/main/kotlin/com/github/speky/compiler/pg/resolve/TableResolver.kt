package com.github.speky.compiler.pg.resolve

import com.github.speky.core.specification.Alias

/**
 * Converts an [Alias] to corresponding table.
 */
interface TableResolver {

  /**
   * Converts [alias] to corresponding table name.
   */
  fun resolveTableName(alias: Alias<*>): String

  /**
   * Extension-function for [Alias].
   *
   * @receiver [Alias]
   *
   * @see [resolveTableName]
   */
  fun Alias<*>.tableName(): String = resolveTableName(this)
}
