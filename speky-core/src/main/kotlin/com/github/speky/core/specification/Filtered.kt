package com.github.speky.core.specification

/**
 * A Filtered [Specification] which filtered by [filter].
 */
sealed class Filtered<T, F> private constructor(
  delegate: Specification<T>,
  val filter: Filter<T, F>
) : Decorator<T>(delegate) {

  /**
   * [Filtered] without ordering ability.
   */
  class JustFiltered<T, F>(delegate: Specification<T>, filter: Filter<T, F>) :
    Filtered<T, F>(delegate, filter)

  /**
   * [Filtered] with order ability.
   */
  class WithOrderable<T, F>(delegate: Specification<T>, filter: Filter<T, F>) :
    Filtered<T, F>(delegate, filter), Orderable<T, Filtered<T, F>>
}
