package com.github.speky.compiler

/**
 * Compiling (converting) [T] to [R]
 */
interface Compiler<T, R> {

  /**
   * Extension-function for [T].
   */
  fun T.compile(): R
}
