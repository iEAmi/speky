package com.github.speky.core.specification

import com.github.speky.core.ClassRef
import com.github.speky.core.Show

/**
 * Name alias for a [Specification]. two different [Alias] could be combined together.
 *
 * @property classRef [ClassRef] of the class
 * @param T type of the class which [Alias] has been created for that
 */
sealed class Alias<T> private constructor(val classRef: ClassRef<T>) {

  internal companion object {

    /**
     * [Show] instance for [Alias].
     */
    internal val show: Show<Alias<*>> = object : Show<Alias<*>> {
      override fun Alias<*>.show(): String = when (this) {
        is Single            -> "${classRef.name} as $value"
        is Multiply<*, *, *> -> "${left.show()} & ${right.show()}"
      }
    }

    /**
     * Invoke() operator to create new [Single] instance.
     */
    internal inline operator fun <reified T> invoke(
      value: String = T::class.simpleName!!.lowercase()
    ): Single<T> = Single(ClassRef(), value)

    /**
     * Invoke operator for creating [Multiply] instance.
     */
    internal inline operator fun <T, R, reified TR> invoke(
      first: Alias<T>,
      second: Alias<R>
    ): Multiply<T, R, TR> = Multiply(ClassRef(), first, second)
  }

  /**
   * Single [Alias] for single [ClassRef].
   *
   * @property value alias value
   */
  class Single<T> internal constructor(clsRef: ClassRef<T>, val value: String) : Alias<T>(clsRef)

  /**
   * Combined [Alias] for [left] and [right].
   */
  class Multiply<T, R, TR> internal constructor(
    classRef: ClassRef<TR>,
    val left: Alias<T>,
    val right: Alias<R>
  ) : Alias<TR>(classRef)
}
