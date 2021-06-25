package com.github.speky.table

/**
 * A reference to constructor of an object.
 *
 * @property functionRef [FunctionRef] to object constructor function
 * @property columns array of the columns that need to construct new object. order is matter
 *
 * @param T type of the object
 */
class ConstructorRef<T>(
  private val functionRef: FunctionRef<T>,
  private vararg val columns: Column<T, *>
) : (Table<T>) -> T {

  /**
   * Validating parameters
   */
  init {
    require(functionRef.arity == columns.size)
  }

  override fun invoke(table: Table<T>): T = functionRef.invoke()
}
