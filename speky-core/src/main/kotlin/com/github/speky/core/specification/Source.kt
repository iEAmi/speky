package com.github.speky.core.specification

import com.github.speky.core.Lens
import com.github.speky.core.specification.Selected.All
import com.github.speky.core.specification.Selected.Some

/**
 * Source of the [Specification]. this is equals to <code>From</code> in sql.
 */
sealed class Source<T> private constructor(override val alias: Alias<T>) : Specification<T>(alias) {

  /**
   * Convert this [Source] to [Selected.All].
   */
  fun select(): All<T> = All(this)

  /**
   * Convert this [Source] to [Selected.Some].
   */
  fun select(vararg lenses: Lens<*, T>): Some<T> = Some(this, lenses.toSet())

  /**
   * Present single [Source] [Specification] without any join.
   * like <code>From table as alias</code> in sql.
   */
  class Single<T>(override val alias: Alias.Single<T>) : Source<T>(alias) {

    /**
     * CrossJoin this source with [R]
     *
     * @param rightAlias alias for right side of the join
     * @param R type of the right side
     * @param TR type of the combined.
     */
    inline fun <reified R, reified TR> crossJoin(rightAlias: String): Mix.CrossJoin<T, R, TR> =
      Mix.CrossJoin(Alias.of(alias, Alias.of(rightAlias)))

    /**
     * Combine [T] with [R].
     *
     * @param rightAlias alias for right side of the join
     * @param R type of the right side
     * @param TR type of the combined.
     */
    inline fun <reified R, reified TR> and(rightAlias: String): Mix.Multiply<T, R, TR> =
      Mix.Multiply(Alias.of(alias, Alias.of(rightAlias)))

    /**
     * CrossJoin this source with [R] on [A]
     *
     * @param rightAlias alias for right side of the join
     * @param leftSelector [Lens] to property of type [A] on [T]
     * @param rightSelector [Lens] to property of type [A] on [R]
     * @param R type of the right side
     * @param TR type of the combined.
     * @param A type of the property these specifications joined on that
     */
    inline fun <reified R, reified TR, A> innerJoin(
      rightAlias: String,
      leftSelector: Lens<A, T>,
      rightSelector: Lens<A, R>
    ): Mix.InnerJoin<T, R, TR, A> =
      Mix.InnerJoin(Alias.of(alias, Alias.of(rightAlias)), leftSelector, rightSelector)
  }

  /**
   * Multiply [Source] that combined together.
   *
   * @param T type of the first class
   * @param R type of the second class
   * @param TR type of the combined class. a class which has an instance of [T] and [R].
   */
  sealed class Mix<T, R, TR> private constructor(
    override val alias: Alias.Multiply<T, R, TR>
  ) : Source<TR>(alias) {

    /**
     * Like <code>From table1, table2</code> in sql.
     */
    class Multiply<T, R, TR>(alias: Alias.Multiply<T, R, TR>) : Mix<T, R, TR>(alias)

    /**
     * Like <code>From table1 cross join table2</code>
     */
    class CrossJoin<T, R, TR>(alias: Alias.Multiply<T, R, TR>) : Mix<T, R, TR>(alias)

    /**
     * Inner join of the [T] and [R] on a property with type [A].
     * Like <code>From table1 inner join table2 on table1.a = table2.a</code>
     *
     * @property leftSelector [Lens] to property of type [A] on [T]
     * @property rightSelector [Lens] to property of type [A] on [R]
     * @param A type of the property this [T] and [R] joined on that
     */
    class InnerJoin<T, R, TR, A>(
      alias: Alias.Multiply<T, R, TR>,
      val leftSelector: Lens<A, T>,
      val rightSelector: Lens<A, R>
    ) : Mix<T, R, TR>(alias)
  }
}
