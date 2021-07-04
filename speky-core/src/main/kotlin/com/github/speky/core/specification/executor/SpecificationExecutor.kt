package com.github.speky.core.specification.executor

import com.github.speky.core.specification.Specification
import com.github.speky.core.specification.Sink

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

  /**
   * Extension-function for [Sink].
   *
   * @receiver [Sink].
   */
  fun Sink<*>.execute()
}
