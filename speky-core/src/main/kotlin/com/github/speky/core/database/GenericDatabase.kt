package com.github.speky.core.database

import com.github.speky.core.ClassRef
import com.github.speky.core.PropertyRef
import com.github.speky.core.specification.Alias
import com.github.speky.core.specification.Specification
import com.github.speky.core.table.ColumnResolver
import com.github.speky.core.table.Table
import com.github.speky.core.table.TableResolver
import java.util.concurrent.ConcurrentHashMap

/**
 * Database contains [Table]. all [Table] should registered to [GenericDatabase].
 */
abstract class GenericDatabase<R, V> : TableResolver, ColumnResolver {
  private val tables = ConcurrentHashMap<ClassRef<*>, Table<*>>()

  /**
   * Register new [table] associated with [classRef].
   */
  fun <T> registerTable(table: Table<T>, classRef: ClassRef<T>) {
    tables[classRef] = table
  }

  override fun resolveTable(alias: Alias<*>): Table<*>? = tables[alias.classRef]

  override fun resolveColumns(prop: PropertyRef<*>): ColumnResolver.ResolveResult {
    val table = tables.values.singleOrNull { it.resolveColumns(prop).isFound() }
      ?: return ColumnResolver.notfound()

    return table.resolveColumns(prop)
  }

  /**
   * Compiles [spec] to [R].
   */
  abstract fun <T> compile(spec: Specification<T>): R

  /**
   * Executes [spec] against database and return [V].
   */
  abstract fun <T> execute(spec: Specification<T>): V

  /**
   * Compiles, Executes [Specification] and return single result.
   */
  abstract fun <T> Specification<T>.single(): T?

  /**
   * Compiles, Executes [Specification] and return first result.
   */
  abstract fun <T> Specification<T>.first(): T?

  /**
   * Compiles, Executes [Specification] and return list result.
   */
  abstract fun <T> Specification<T>.list(): List<T>
}
