package com.github.speky.core

/**
 * Lens is a tool to point to specific property of a class. Lens<Int, Person> is a pointer to
 * a property of the class Person which has type Int.
 * Lens<Int, Person> = Person -> Int
 * this is like kotlin :: operator but without reflection and built-in support for combining.
 *
 * @property propertyRef [PropertyRef] to hold reference to property
 *
 * @param R type of property [Lens] points to
 * @param T type of class which contains property
 */
sealed class Lens<R, T> private constructor(val propertyRef: PropertyRef<R>) {

  /**
   * Delegate property to [PropertyRef.declaringClassRef].
   *
   * @see [PropertyRef]
   */
  @Suppress("UNCHECKED_CAST")
  val declaringClassRef: ClassRef<T>
    get() = propertyRef.declaringClassRef as ClassRef<T>

  /**
   * Combines this [Lens] to an [other] [Lens].
   */
  infix fun <V> zoom(other: Lens<V, R>): Lens<V, Lens<R, T>> =
    Zoom(this, other, other.propertyRef)

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is Lens<*, *>) return false

    if (propertyRef != other.propertyRef) return false

    return true
  }

  override fun hashCode(): Int = propertyRef.hashCode()

  companion object {

    /**
     * [Show] instance for [Lens].
     */
    val show: Show<Lens<*, *>> = object : Show<Lens<*, *>> {
      override fun Lens<*, *>.show(): String = when (this) {
        is Focus         -> with(PropertyRef.show) { propertyRef.show() }
        is Zoom<*, *, *> -> left.show() + "." + right.propertyRef.name
      }
    }

    /**
     * Factory method to create new instance of [Lens]. this is starting-point of the [Lens].
     *
     * @param name name of the property
     * @param T type of the class that owns property
     * @param R type of the property
     */
    inline fun <reified T, reified R> on(name: String): Lens<R, T> =
      Focus(PropertyRef(name, ClassRef<T>()))
  }

  /**
   * Starting point of the Lens. [Focus] is 1X a [Lens] with 1x zoom.
   */
  class Focus<R, T> @PublishedApi internal constructor(
    propertyRef: PropertyRef<R>
  ) : Lens<R, T>(propertyRef)

  /**
   * A [Lens] that has more than 1x zoom level and combined two lens together.
   *
   * @property left left [Lens]
   * @property right right [Lens] that was combined to [left]
   */
  class Zoom<V, R, T> internal constructor(
    val left: Lens<R, T>,
    val right: Lens<V, R>,
    propertyRef: PropertyRef<V>
  ) : Lens<V, Lens<R, T>>(propertyRef)
}
