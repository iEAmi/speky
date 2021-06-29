package com.github.speky.core.specification

import com.github.speky.core.specification.Filtered.WithOrderable

/**
 * Any [Specification] could filtered and ordered
 */
internal sealed interface FilterableWithOrderable<T, Spec : Specification<T>> : Orderable<T, Spec> {

  /**
   * Filter [Spec] with [filter].
   */
  @Suppress("UNCHECKED_CAST")
  fun <R> filter(filter: Filter<T, R>): WithOrderable<T, R> = WithOrderable(this as Spec, filter)
}
