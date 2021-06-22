package com.github.speky.core.specification

/**
 * Any [Specification] could ordered.
 *
 * @param T type that [Specification] wrote on that
 * @param Spec type of the [Specification]
 */
internal sealed interface Orderable<T, Spec : Specification<T>> {

  /**
   * Order [Spec] with [order].
   */
  @Suppress("UNCHECKED_CAST")
  fun <R> order(order: Order<T, R>): Ordered<T, R> = Ordered(this as Spec, order)
}
