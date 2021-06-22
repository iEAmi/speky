package com.github.speky.core.specification

/**
 * An ordered [Specification] which ordered by [order].
 */
class Ordered<T, O>(delegate: Specification<T>, val order: Order<T, O>) : Decorator<T>(delegate) {

  /**
   * Convert this [Ordered] to [Sized] by specifying [limit] and [offset].
   */
  fun size(limit: Int, offset: Int): Sized<T, O> = Sized(this, limit, offset)
}
