package com.github.speky.core

/**
 * Hold [name] and [qualifiedName] of a kotlin class.
 *
 * @property name simpleName of the class
 * @property qualifiedName fully absolute name of the class
 * @param T type of the class
 */
data class ClassRef<T> @PublishedApi internal constructor(
  val name: String,
  val qualifiedName: String
) {
  companion object {

    /**
     * Invoke operator to create new instance of [ClassRef].
     *
     * @param T type of the class to create [ClassRef] for it
     */
    inline operator fun <reified T> invoke(): ClassRef<T> =
      T::class.let { ClassRef(it.simpleName!!, it.qualifiedName!!) }
  }
}
