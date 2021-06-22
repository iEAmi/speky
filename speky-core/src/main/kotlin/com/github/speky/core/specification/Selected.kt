package com.github.speky.core.specification

import com.github.speky.core.Lens

/**
 * A [Decorator] that decorate [source] and add Select expression to that.
 *
 * @property source [Source] specification to decorate
 */
sealed class Selected<T> private constructor(val source: Source<T>) :
  Decorator<T>(source), Orderable<T, Selected<T>>/*, Filterable<T, Selected<T>>*/ {

  /**
   * Like <code>Select * from table as alias</code>.
   */
  class All<T>(source: Source<T>) : Selected<T>(source)

  /**
   * Like <code>Select alias.column1, alias.column2 from table as alias</code> in sql.
   *
   * @property lenses a set of all selected property
   */
  class Some<T>(source: Source<T>, val lenses: Set<Lens<*, T>>) : Selected<T>(source)
}
