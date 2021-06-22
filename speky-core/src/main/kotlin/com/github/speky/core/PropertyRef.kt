package com.github.speky.core

/**
 * A reference to property.
 *
 * @property name name of the property
 * @property classRef [ClassRef] of the property type
 * @property declaringClassRef [ClassRef] of the class that property belongs to that
 *
 * @param T type of the property
 */
data class PropertyRef<T> @PublishedApi internal constructor(
  val name: String,
  val classRef: ClassRef<T>,
  val declaringClassRef: ClassRef<*>,
) {
  companion object {
    /**
     * [Show] instance for [PropertyRef].
     */
    val show: Show<PropertyRef<*>> = Show { "${declaringClassRef.name}.$name" }

    /**
     * Invoke operator to create new Instance of the [PropertyRef].
     *
     * @param name name of the property
     * @param declaringClass [ClassRef] of the class that property belongs to that
     * @param T type of the property to create [PropertyRef] for it
     */
    inline operator fun <reified T> invoke(
      name: String,
      declaringClass: ClassRef<*>
    ): PropertyRef<T> {
      require(name.isNotEmpty()) { "name should be not empty" }
      require(name.isNotBlank()) { "name should be not blank" }

      return PropertyRef(name, ClassRef(), declaringClass)
    }
  }
}
