package com.github.speky.core.table

import com.github.speky.core.PropertyRef
import com.github.speky.core.table.ColumnResolver.ResolveResult.EmbeddedColumns
import com.github.speky.core.table.ColumnResolver.ResolveResult.EmbeddedWithoutColumn
import com.github.speky.core.table.ColumnResolver.ResolveResult.NotFound
import com.github.speky.core.table.ColumnResolver.ResolveResult.SingleColumn

/**
 * Converts a [PropertyRef] to corresponding column.
 */
interface ColumnResolver {

  companion object {
    /**
     * Factory-method to convert [column] to [ResolveResult].
     */
    fun column(column: Column<*, *, *>): ResolveResult = SingleColumn(column)

    /**
     * Factory-method to convert [embedded] to [ResolveResult].
     */
    fun embedded(embedded: Embedded<*, *>): ResolveResult = with(embedded.columns()) {
      if (isEmpty()) EmbeddedWithoutColumn(embedded)
      else EmbeddedColumns(embedded.columns().toList())
    }

    /**
     * Just for simplicity.
     */
    fun notfound(): ResolveResult = NotFound
  }

  /**
   * Data model that presets resolving result
   */
  sealed class ResolveResult private constructor() {

    /**
     * Supply new [ResolveResult] if this is [NotFound].
     *
     * @param f Supplier that invoked when this be [NotFound]
     */
    fun ifNotfound(f: () -> ResolveResult): ResolveResult {
      if (isFound()) return this

      return f()
    }

    /**
     * Checks result was succeeded or not.
     */
    fun isFound(): Boolean = this !is NotFound

    /**
     * Single [Column] was found for [PropertyRef].
     *
     * @property column found column
     */
    data class SingleColumn(val column: Column<*, *, *>) : ResolveResult()

    /**
     * [Embedded] column was found for [PropertyRef].
     *
     * @property columns all columns registered in [Embedded].
     */
    data class EmbeddedColumns(val columns: List<Column<*, *, *>>) : ResolveResult()

    /**
     * An [Embedded] column was found but there is no [Column] in it.
     *
     * @property embedded found embedded
     */
    data class EmbeddedWithoutColumn(val embedded: Embedded<*, *>) : ResolveResult()

    /**
     * Nothing found.
     */
    object NotFound : ResolveResult()
  }

  /**
   * Converts [prop] to corresponding columns name.
   */
  fun resolveColumns(prop: PropertyRef<*>): ResolveResult

  /**
   * Extension-function for [PropertyRef].
   *
   * @receiver [PropertyRef]
   *
   * @see [resolveColumns]
   */
  fun PropertyRef<*>.columnsName(): List<String> = when (val result = resolveColumns(this)) {
    is EmbeddedColumns       -> result.columns.map { it.name }
    is SingleColumn          -> listOf(result.column.name)
    NotFound                 ->
      throw NoSuchElementException(
        "No column or embedded registered for " +
            "'${this.declaringClassRef.qualifiedName}.${this.name}'"
      )
    is EmbeddedWithoutColumn ->
      throw NoSuchElementException(
        "Embedded '${result.embedded.classRef.name}' was found. " +
            "but no column registered for this embedded type"
      )
  }
}
