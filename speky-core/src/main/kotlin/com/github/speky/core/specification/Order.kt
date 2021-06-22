package com.github.speky.core.specification

import com.github.speky.core.Lens
import com.github.speky.core.Show

/**
 * Data model to model ordering.
 *
 * @property lens [Lens] to property in [T] with type [O]
 * @param T type that order applied to that
 * @param O type of the property that [lens] points to that
 */
sealed class Order<T, O> private constructor(
  val lens: Lens<O, T>
) {

  companion object {
    /**
     * [Show] instance for [Order].
     */
    val show: Show<Order<*, *>> = Show {
      when (this) {
        is Ascending  -> "order by ${with(Lens.show) { lens.show() }} asc"
        is Descending -> "order by ${with(Lens.show) { lens.show() }} desc"
      }
    }

    /**
     * Factory-method to create [Ascending] instance.
     */
    fun <T, O> asc(lens: Lens<O, T>): Ascending<T, O> = Ascending(lens)

    /**
     * Factory-method to create [Ascending] instance.
     */
    @Suppress("UNCHECKED_CAST")
    @JvmName("ascWithLens2")
    fun <T1, T2, O> asc(lens: Lens<O, Lens<T2, T1>>): Ascending<T1, O> =
      Ascending(lens as Lens<O, T1>)

    /**
     * Factory-method to create [Descending] instance.
     */
    fun <T, O> desc(lens: Lens<O, T>): Descending<T, O> = Descending(lens)

    /**
     * Factory-method to create [Descending] instance.
     */
    @Suppress("UNCHECKED_CAST")
    @JvmName("descWithLens2")
    fun <T1, T2, O> desc(lens: Lens<O, Lens<T2, T1>>): Descending<T1, O> =
      Descending(lens as Lens<O, T1>)
  }

  /**
   * Ascending order.
   */
  class Ascending<T, O>(lens: Lens<O, T>) : Order<T, O>(lens)

  /**
   * Descending order.
   */
  class Descending<T, O>(lens: Lens<O, T>) : Order<T, O>(lens)
}
