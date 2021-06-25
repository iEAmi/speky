package com.github.speky.table

/**
 * Presets everyThings could be constructed be a [ConstructorRef].
 *
 * @param T type of object that constructed
 */
interface Constructible<T> {

  /**
   * Gets [ConstructorRef].
   */
  fun constructorRef() : ConstructorRef<T>
}
