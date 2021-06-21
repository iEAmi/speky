package com.github.speky.core

/**
 * Type-class that convert [T] to string.
 */
fun interface Show<T> {

  /**
   * Extension method for type [T].
   */
  fun T.show(): String
}
