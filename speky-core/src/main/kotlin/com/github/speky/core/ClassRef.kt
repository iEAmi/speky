package com.github.speky.core

/**
 * Hold [name] and [qualifiedName] of a kotlin class.
 *
 * @property name simpleName of the class
 * @property qualifiedName fully absolute name of the class
 *
 * @param T type of the class
 */
data class ClassRef<T>(
  val name: String,
  val qualifiedName: String
) {
  companion object {
    /**
     * [Show] instance for [ClassRef].
     */
    val show: Show<ClassRef<*>> = Show { "$name @ $qualifiedName" }

    /**
     * Invoke operator to create new instance of [ClassRef].
     *
     * @param T type of the class to create [ClassRef] for it
     */
    inline fun <reified T> of(): ClassRef<T> =
      T::class.let { ClassRef(it.simpleName!!, it.qualifiedName!!) }
  }
}
