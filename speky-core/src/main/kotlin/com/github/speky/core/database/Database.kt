package com.github.speky.core.database

import com.github.speky.core.ClassRef
import com.github.speky.core.specification.Alias
import com.github.speky.core.table.Table
import com.github.speky.core.table.TableResolver
import java.util.concurrent.ConcurrentHashMap

/**
 * Database contains [Table]. all [Table] should registered to [Database].
 */
abstract class Database : TableResolver {
  private val tables = ConcurrentHashMap<ClassRef<*>, Table<*>>()

  /**
   * Register new [table] associated with [classRef].
   */
  fun <T> registerTable(table: Table<T>, classRef: ClassRef<T>) {
    tables[classRef] = table
  }

  override fun resolveTableName(alias: Alias<*>): Table<*>? = tables[alias.classRef]
}
