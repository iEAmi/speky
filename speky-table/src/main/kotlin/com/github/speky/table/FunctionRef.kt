package com.github.speky.table

import kotlin.jvm.functions.FunctionN

/**
 * A reference to a function.
 *
 * @property arity count of function parameters
 * @property f delegate function
 *
 * @param T type of the return of the function
 */
class FunctionRef<T>(override val arity: Int, private val f: (Array<out Any?>) -> T) :
  FunctionN<T> {
  override fun invoke(vararg args: Any?): T = f.invoke(args)
}
