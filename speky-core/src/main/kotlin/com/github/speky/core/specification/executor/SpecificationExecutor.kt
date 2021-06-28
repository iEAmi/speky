package com.github.speky.core.specification.executor

import com.github.speky.core.specification.Specification

/**
 * Execute [Specification] against database and returns [R].
 */
interface SpecificationExecutor<R> {
  /**
   * Extension-function for [Specification].
   *
   * @receiver [Specification].
   */
  fun Specification<*>.execute(): R
}
