package com.github.speky.core.specification

import com.github.speky.core.Show
import com.github.speky.core.specification.Alias.JustClassRef

/**
 * A [Specification] which write to new values to it or updated available values in it.
 */
sealed class Sink<T> private constructor(
  override val alias: JustClassRef<T>
) : Specification<T>(alias) {

  companion object {
    /**
     * [Show] instance for [Sink].
     */
    val show: Show<Sink<*>> = Show {
      when (this) {
        is Insert -> "Insert into ${with(Alias.show) { alias.show() }}"
        is Update -> "Update ${with(Alias.show) { alias.show() }}"
      }
    }

    /**
     * Factory-method to create new [Insert] instance.
     */
    inline fun <reified T> insert(values: List<Value<T, *>>): Insert<T> =
      Insert(Alias.justClass(), values)

    /**
     * Factory-method to create new [Update] instance.
     */
    inline fun <reified T> update(values: Set<Value<T, *>>): Update<T> =
      Update(Alias.justClass(), values)
  }

  /**
   * Insertable [Sink].
   *
   * @property values to insert into this [Sink]. [values] is a [List] so duplication is valid,
   * this make [Insert] suitable for batch insert
   */
  class Insert<T>(alias: JustClassRef<T>, val values: List<Value<T, *>>) : Sink<T>(alias) {
    init {
      val group = values.groupBy { it.lens.propertyRef.name }.mapValues { it.value.size }
      val max = group.maxOf { it.value }
      val invalidLenses = group.filter { it.value < max }.map { it.key }
      if (invalidLenses.isNotEmpty()) {
        throw IllegalArgumentException(
          "inconsistent value count for columns. " +
              "max value count is $max, but ${invalidLenses.joinToString()} have less count."
        )
      }
    }
  }

  /**
   * Updatable [Sink].
   *
   * @property values to update this [Sink] with them. [values] is [Set] so all duplication wil be
   * ignored
   */
  class Update<T>(alias: JustClassRef<T>, val values: Set<Value<T, *>>) : Sink<T>(alias),
    Filterable<T, Update<T>>
}
