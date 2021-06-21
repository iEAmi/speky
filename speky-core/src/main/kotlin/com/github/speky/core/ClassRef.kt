package com.github.speky.core

/**
 * Hold [name] and [qualifiedName] of a kotlin class.
 *
 * @property name simpleName of the class
 * @property qualifiedName fully absolute name of the class
 *
 * @param T type of the class
 */
data class ClassRef<T> internal constructor(
  val name: String,
  val qualifiedName: String
) {
  internal companion object {

    /**
     * [Show] instance for [ClassRef].
     */
    internal val show: Show<ClassRef<*>> = Show { "$name @ $qualifiedName" }

    /**
     * Invoke operator to create new instance of [ClassRef].
     *
     * @param T type of the class to create [ClassRef] for it
     */
    internal inline operator fun <reified T> invoke(): ClassRef<T> =
      T::class.let { ClassRef(it.simpleName!!, it.qualifiedName!!) }
  }
}
