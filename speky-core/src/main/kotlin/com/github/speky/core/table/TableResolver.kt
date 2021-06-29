package com.github.speky.core.table

import com.github.speky.core.specification.Alias

/**
 * Converts an [Alias] to corresponding table.
 */
interface TableResolver {

  /**
   * Converts [alias] to corresponding table name.
   */
  fun resolveTable(alias: Alias<*>): Table<*>?

  /**
   * Extension-function for [Alias].
   *
   * @receiver [Alias]
   *
   * @see [resolveTable]
   */
  fun Alias<*>.tableName(): String =
    resolveTable(this)?.tableName ?: throw NoSuchElementException()
}
