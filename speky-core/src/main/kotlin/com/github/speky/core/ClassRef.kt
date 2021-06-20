package com.github.speky.core

/**
 * Hold [name] and [qualifiedName] of a kotlin class.
 *
 * @property name simpleName of the class
 * @property qualifiedName fully absolute name of the class
 * @param T type of the class
 */
class ClassRef<T> @PublishedApi internal constructor(val name: String, val qualifiedName: String) {

  companion object {

    /**
     * Invoke operator to create new instance of [ClassRef].
     *
     * @param T type of the class to create [ClassRef] for it
     */
    inline operator fun <reified T> invoke(): ClassRef<T> =
      T::class.let { ClassRef(it.simpleName!!, it.qualifiedName!!) }
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is ClassRef<*>) return false

    if (name != other.name) return false
    if (qualifiedName != other.qualifiedName) return false

    return true
  }

  override fun hashCode(): Int {
    var result = name.hashCode()
    result = 31 * result + qualifiedName.hashCode()
    return result
  }

  override fun toString(): String = "ClassRef(name='$name', qualifiedName='$qualifiedName')"
}
