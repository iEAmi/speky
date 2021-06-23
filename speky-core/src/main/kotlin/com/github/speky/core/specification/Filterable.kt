package com.github.speky.core.specification

import com.github.speky.core.specification.Filtered.JustFiltered

/**
 * Any [Specification] could filtered.
 */
internal sealed interface Filterable<T, Spec : Specification<T>> {

  /**
   * Filter [Spec] with [filter].
   */
  @Suppress("UNCHECKED_CAST")
  fun <R> filter(filter: Filter<T, R>): JustFiltered<T, R> = JustFiltered(this as Spec, filter)
}
