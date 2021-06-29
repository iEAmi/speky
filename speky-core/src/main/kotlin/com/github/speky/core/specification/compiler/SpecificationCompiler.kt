package com.github.speky.core.specification.compiler

import com.github.speky.core.specification.Specification

/**
 * Compiles [Specification] to [R].
 */
interface SpecificationCompiler<R> {
  /**
   * Extension-function for [Specification].
   *
   * @receiver [Specification].
   */
  fun Specification<*>.compile(): R
}
