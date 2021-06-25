package com.github.speky.table

import com.github.speky.core.Show

/**
 * A reference to constructor of an object.
 *
 * @property functionRef [FunctionRef] to object constructor function
 * @property parameters array of the columns that need to construct new object. order is matter
 *
 * @param T type of the object
 */
class ConstructorRef<T>(
  private val functionRef: FunctionRef<T>,
  private vararg val parameters: Parameter<T, *>
) : (Table<T>) -> T {

  /**
   * Validating parameters.
   */
  init {
    require(functionRef.arity == parameters.size) { "Inconsistent arity" }
  }

  override fun invoke(table: Table<T>): T = functionRef.invoke()

  sealed class Parameter<T, R> {

    companion object {
      val show: Show<Parameter<*, *>> = Show {
        when (this) {
          is NormalParam   -> with(Column.show) { column.show() }
          is EmbeddedParam -> with(Embedded.show) { embedded.show() }
        }
      }

      fun <T, R> of(column: Column<T, R>): NormalParam<T, R> = NormalParam(column)
      fun <T, R> of(embedded: Embedded<R, T>): EmbeddedParam<T, R> = EmbeddedParam(embedded)
    }

    data class NormalParam<T, R>(val column: Column<T, R>) : Parameter<T, R>()
    data class EmbeddedParam<T, R>(val embedded: Embedded<R, T>) : Parameter<T, R>()
  }
}
