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

  fun flatten(): List<Single<*>> {
    val result = mutableListOf<Single<*>>()

    when (this) {
      is Single            -> result += this
      is Multiply<*, *, *> -> result += this.left.flatten() + this.right.flatten()
      is JustClassRef      -> TODO()
    }

    return result
  }

  companion object {
    /**
     * [Show] instance for [Alias].
     */
    val show: Show<Alias<*>> = object : Show<Alias<*>> {
      override fun Alias<*>.show(): String = when (this) {
        is Single            -> "${classRef.name} as $value"
        is JustClassRef      -> classRef.name.lowercase()
        is Multiply<*, *, *> -> "${left.show()} & ${right.show()}"
      }
    }

    /**
     * Factory-method to create new [Single] instance.
     */
    inline fun <reified T> of(value: String): Single<T> = Single(ClassRef.of(), value)

    /**
     * Factory-method for creating [Multiply] instance.
     */
    inline fun <T, R, reified TR> of(
      left: Alias<T>,
      right: Alias<R>
    ): Multiply<T, R, TR> = Multiply(ClassRef.of(), left, right)

    /**
     * Factory-method to create new [JustClassRef] instance.
     */
    inline fun <reified T> justClass(): JustClassRef<T> = JustClassRef(ClassRef.of())
  }

  /**
   * Single [Alias] for single [ClassRef].
   *
   * @property value alias value
   */
  class Single<T>(clsRef: ClassRef<T>, val value: String) : Alias<T>(clsRef) {
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
    clsRef: ClassRef<TR>,
    val left: Alias<T>,
    val right: Alias<R>
  ) : Alias<TR>(clsRef) {
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

  /**
   * An [Alias] that just hold [ClassRef] to [T].
   */
  class JustClassRef<T>(clsRef: ClassRef<T>) : Alias<T>(clsRef) {
    override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (other !is JustClassRef<*>) return false

      if (classRef != other.classRef) return false

      return true
    }

    override fun hashCode(): Int = classRef.hashCode()
  }
}
