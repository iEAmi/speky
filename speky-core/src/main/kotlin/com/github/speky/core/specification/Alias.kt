package com.github.speky.core.specification

import com.github.speky.core.ClassRef
import com.github.speky.core.Show

/**
 * Name alias for a [Specification]. two different [Alias] could be combined together.
 *
 * @property classRef [ClassRef] of the class
 * @param T type of the class which [Alias] has been created for that
 */
sealed class Alias<T> private constructor(open val classRef: ClassRef<T>) {

  companion object {
    /**
     * [Show] instance for [Alias].
     */
    val show: Show<Alias<*>> = object : Show<Alias<*>> {
      override fun Alias<*>.show(): String = when (this) {
        is Single            -> "${classRef.name} as $value"
        is Multiply<*, *, *> -> "${left.show()} & ${right.show()}"
      }
    }

    /**
     * Invoke operator to create new [Single] instance.
     */
    inline operator fun <reified T> invoke(
      value: String = T::class.simpleName!!.lowercase()
    ): Single<T> = Single(ClassRef(), value)

    /**
     * Invoke operator for creating [Multiply] instance.
     */
    inline operator fun <T, R, reified TR> invoke(
      left: Alias<T>,
      right: Alias<R>
    ): Multiply<T, R, TR> = Multiply(ClassRef(), left, right)
  }

  /**
   * Single [Alias] for single [ClassRef].
   *
   * @property value alias value
   */
  class Single<T>(override val classRef: ClassRef<T>, val value: String) : Alias<T>(classRef) {
    override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (other !is Single<*>) return false

      if (classRef != other.classRef) return false
      if (value != other.value) return false

      return true
    }

    override fun hashCode(): Int {
      var result = classRef.hashCode()
      result = 31 * result + value.hashCode()
      return result
    }
  }

  /**
   * Combined [Alias] for [left] and [right].
   */
  class Multiply<T, R, TR>(
    override val classRef: ClassRef<TR>,
    val left: Alias<T>,
    val right: Alias<R>
  ) : Alias<TR>(classRef) {
    override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (other !is Multiply<*, *, *>) return false

      if (classRef != other.classRef) return false
      if (left != other.left) return false
      if (right != other.right) return false

      return true
    }

    override fun hashCode(): Int {
      var result = classRef.hashCode()
      result = 31 * result + left.hashCode()
      result = 31 * result + right.hashCode()
      return result
    }
  }
}
