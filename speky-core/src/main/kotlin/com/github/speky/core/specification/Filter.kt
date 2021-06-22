package com.github.speky.core.specification

import com.github.speky.core.Lens
import com.github.speky.core.Show

/**
 * Data model for modeling binary operation like = , < , > , !=.
 *
 * @property lens [Lens] to property in [T] with type [F]
 * @param T type that filter applied to that
 * @param F type of the filter
 */
sealed class Filter<T, F> private constructor(
  val lens: Lens<F, T>
) {

  companion object {

    /**
     * [Show] instance for [Filter].
     */
    @Suppress("StringLiteralDuplication")
    val show: Show<Filter<*, *>> = Show {
      when (this) {
        is Equal            -> "${with(Lens.show) { lens.show() }} = $value"
        is NotEqual         -> "${with(Lens.show) { lens.show() }} != $value"
        is GreaterThan      -> "${with(Lens.show) { lens.show() }} > $value"
        is GreaterThanEqual -> "${with(Lens.show) { lens.show() }} >= $value"
        is LessThan         -> "${with(Lens.show) { lens.show() }} < $value"
        is LessThanEqual    -> "${with(Lens.show) { lens.show() }} <= $value"
        is EndsWith         -> "${with(Lens.show) { lens.show() }} like '%$value'"
        is Like             -> "${with(Lens.show) { lens.show() }} like '%$value%'"
        is Contains         -> "${with(Lens.show) { lens.show() }} like '%$value%'"
        is StartsWith       -> "${with(Lens.show) { lens.show() }} like '$value%'"
      }
    }

    /**
     * Factory-method to create [Equal] instance.
     */
    fun <T, F> eq(lens: Lens<F, T>, value: F): Equal<T, F> = Equal(lens, value)

    /**
     * Factory-method to create [NotEqual] instance.
     */
    fun <T, F> neq(lens: Lens<F, T>, value: F): NotEqual<T, F> = NotEqual(lens, value)

    /**
     * Factory-method to create [GreaterThan] instance.
     */
    fun <T, F> gt(lens: Lens<F, T>, value: F): GreaterThan<T, F> = GreaterThan(lens, value)

    /**
     * Factory-method to create [GreaterThanEqual] instance.
     */
    fun <T, F> gte(lens: Lens<F, T>, value: F): GreaterThanEqual<T, F> =
      GreaterThanEqual(lens, value)

    /**
     * Factory-method to create [LessThan] instance.
     */
    fun <T, F> lt(lens: Lens<F, T>, value: F): LessThan<T, F> = LessThan(lens, value)

    /**
     * Factory-method to create [LessThanEqual] instance.
     */
    fun <T, F> lte(lens: Lens<F, T>, value: F): LessThanEqual<T, F> = LessThanEqual(lens, value)

    /**
     * Factory-method to create [Like] instance.
     */
    fun <T> like(lens: Lens<String, T>, value: String): Like<T> = Like(lens, value)

    /**
     * Factory-method to create [Contains] instance.
     */
    fun <T> contains(lens: Lens<String, T>, value: String): Contains<T> = Contains(lens, value)

    /**
     * Factory-method to create [StartsWith] instance.
     */
    fun <T> startsWith(lens: Lens<String, T>, value: String): StartsWith<T> =
      StartsWith(lens, value)

    /**
     * Factory-method to create [EndsWith] instance.
     */
    fun <T> endsWith(lens: Lens<String, T>, value: String): EndsWith<T> = EndsWith(lens, value)
  }

  /**
   * Equal binary operation. `=` in sql.
   *
   * @property value right side of operator.
   */
  class Equal<T, F>(lens: Lens<F, T>, val value: F) : Filter<T, F>(lens)

  /**
   * Not equal binary operation. `!=` in sql.
   *
   * @property value right side of operator.
   */
  class NotEqual<T, F>(lens: Lens<F, T>, val value: F) : Filter<T, F>(lens)

  /**
   * Greater than binary operation. `>` in sql.
   *
   * @property value right side of operator.
   */
  class GreaterThan<T, F>(lens: Lens<F, T>, val value: F) : Filter<T, F>(lens)

  /**
   * Greater than or equal binary operation. `>=` in sql.
   *
   * @property value right side of operator.
   */
  class GreaterThanEqual<T, F>(lens: Lens<F, T>, val value: F) : Filter<T, F>(lens)

  /**
   * Less than binary operation. `<` in sql.
   *
   * @property value right side of operator.
   */
  class LessThan<T, F>(lens: Lens<F, T>, val value: F) : Filter<T, F>(lens)

  /**
   * Less than or equal binary operation. `<=` in sql.
   *
   * @property value right side of operator.
   */
  class LessThanEqual<T, F>(lens: Lens<F, T>, val value: F) : Filter<T, F>(lens)

  /**
   * Like in sql.
   *
   * @property value right side of operator.
   */
  class Like<T>(lens: Lens<String, T>, val value: String) : Filter<T, String>(lens)

  /**
   * @see [Like].
   *
   * @property value right side of operator.
   */
  class Contains<T>(lens: Lens<String, T>, val value: String) : Filter<T, String>(lens)

  /**
   * @see [Like].
   *
   * @property value right side of operator.
   */
  class StartsWith<T>(lens: Lens<String, T>, val value: String) : Filter<T, String>(lens)

  /**
   * @see [Like].
   *
   * @property value right side of operator.
   */
  class EndsWith<T>(lens: Lens<String, T>, val value: String) : Filter<T, String>(lens)
}
