package com.github.speky.core.table

import kotlin.jvm.functions.FunctionN

/**
 * A reference to a function.
 *
 * @property arity count of function parameters
 * @property delegate delegate function
 *
 * @param T type of the return of the function
 */
class FunctionRef<T>(override val arity: Int, private val delegate: (Array<out Any?>) -> T) :
  FunctionN<T> {
  override fun invoke(vararg args: Any?): T = delegate.invoke(args)

  /**
   * Simplest version of [invoke].
   */
  @Suppress("SpreadOperator")
  internal fun invoke(list: List<Any?>): T = invoke(*list.toTypedArray())
}
