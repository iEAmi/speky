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

  /**
   * Not implemented yet
   */
  override fun invoke(table: Table<T>): T = functionRef.invoke()

  /**
   * [ConstructorRef] parameter.
   */
  sealed class Parameter<T, R> {

    companion object {
      /**
       * [Show] instance for [Parameter].
       */
      val show: Show<Parameter<*, *>> = Show {
        when (this) {
          is NormalParam<*, *, *> -> with(Column.show) { column.show() }
          is EmbeddedParam        -> with(Embedded.show) { embedded.show() }
        }
      }

      /**
       * Factory-method to create new instance of [NormalParam].
       */
      fun <T, R, S : SqlValue> of(column: Column<T, R, S>): NormalParam<T, R, S> =
        NormalParam(column)

      /**
       * Factory-method to create new instance of [EmbeddedParam].
       */
      fun <T, R> of(embedded: Embedded<R, T>): EmbeddedParam<T, R> = EmbeddedParam(embedded)
    }

    /**
     * Normal parameter that hold a [Column].
     *
     * @property column [Column] instance
     */
    data class NormalParam<T, R, S : SqlValue>(val column: Column<T, R, S>) : Parameter<T, R>()

    /**
     * [Parameter] that hold [Embedded].
     *
     * @property embedded [Embedded] instance
     */
    data class EmbeddedParam<T, R>(val embedded: Embedded<R, T>) : Parameter<T, R>()
  }
}
