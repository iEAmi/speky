package com.github.speky.core.specification

/**
 * Any [Specification] could filtered.
 */
internal sealed interface Filterable<T, Spec : Specification<T>> {

  /**
   * Filter [Spec] with [filter].
   */
  @Suppress("UNCHECKED_CAST")
  fun <R> filter(filter: Filter<T, R>): Filtered<T, R> = Filtered(this as Spec, filter)
}
