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
  class Single<T>(alias: Alias<T>) : Source<T>(alias) {

    //    inline fun <reified R, reified TR> crossJoin(secondAlias: Alias<R>): CrossJoin<T, R, TR> =
    //      CrossJoin(Alias(alias, secondAlias))
    //
    //    inline fun <reified R, reified TR> and(secondAlias: Alias<R>): Multiply<T, R, TR> =
    //      Multiply(Alias(alias, secondAlias))
    //
    //    inline fun <reified R, reified TR, A> innerJoin(
    //      secondAlias: Alias<R>,
    //      onT: Path<T, A>,
    //      onR: Path<R, A>
    //    ): InnerJoin<T, R, TR, A> = InnerJoin(Alias(alias, secondAlias), onT, onR)
    //
    //    inline fun <reified R> with(path: Path<T, R>): WithRefs<T, R> =
    //      WithRefs(
    //        Alias<T, R, Any>(alias, Alias()) as Alias.Multiply<T, R, T>,
    //        ClassRef(),
    //        path
    //      )
    //
    //    inline fun <reified R, reified TR> crossJoin(
    //      secondAlias: String = R::class.simpleName!!.lowercase()
    //    ): CrossJoin<T, R, TR> = crossJoin(Alias(secondAlias))
    //
    //    inline fun <reified R, reified TR> and(
    //      secondAlias: String = R::class.simpleName!!.lowercase()
    //    ): Multiply<T, R, TR> = and(Alias(secondAlias))
    //
    //    inline fun <reified R, reified TR, A> innerJoin(
    //      secondAlias: String = R::class.simpleName!!.lowercase(),
    //      onT: Path<T, A>,
    //      onR: Path<R, A>
    //    ): InnerJoin<T, R, TR, A> = innerJoin(Alias(secondAlias), onT, onR)
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
     * @property tSelector [Lens] to property of type [A] on [T]
     * @property rSelector [Lens] to property of type [A] on [R]
     * @param A type of the property this [T] and [R] joined on that
     */
    class InnerJoin<T, R, TR, A>(
      alias: Alias.Multiply<T, R, TR>,
      val tSelector: Lens<A, T>,
      val rSelector: Lens<A, R>
    ) : Mix<T, R, TR>(alias)
  }
}
