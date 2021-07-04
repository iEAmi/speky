package com.github.speky.extension

import com.github.speky.core.Lens
import com.github.speky.core.specification.Filter
import com.github.speky.core.specification.Order
import com.github.speky.core.specification.Value
import kotlin.internal.Exact
import kotlin.reflect.KProperty1 as KProp

/**
 * Extension-function on [KProp].
 *
 * @see [Lens.on]
 */
inline fun <reified T, reified A> KProp<T, A>.on(): Lens<A, T> = Lens.on(name)

/**
 * Extension-function on [KProp].
 *
 * @see [Order.asc]
 */
inline fun <reified T, reified R> KProp<T, R>.asc(): Order.Ascending<T, R> = Order.asc(on())

/**
 * Extension-function on [KProp].
 *
 * @see [Order.desc]
 */
inline fun <reified T, reified R> KProp<T, R>.desc(): Order.Descending<T, R> = Order.desc(on())

/**
 * Extension-function on [KProp].
 *
 * @see [Filter.eq]
 */
inline infix fun <reified T, reified R> KProp<T, @Exact R>.eq(value: R): Filter.Equal<T, R> =
  Filter.eq(on(), value)

/**
 * Extension-function on [KProp].
 *
 * @see [Filter.neq]
 */
inline infix fun <reified T, reified R> KProp<T, @Exact R>.neq(value: R): Filter.NotEqual<T, R> =
  Filter.neq(on(), value)

/**
 * Extension-function on [KProp].
 *
 * @see [Filter.gt]
 */
inline infix fun <reified T, reified R> KProp<T, @Exact R>.gt(value: R): Filter.GreaterThan<T, R> =
  Filter.gt(on(), value)

/**
 * Extension-function on [KProp].
 *
 * @see [Filter.gte]
 */
inline infix fun <reified T, reified R> KProp<T, @Exact R>.gte(
  value: R
): Filter.GreaterThanEqual<T, R> = Filter.gte(on(), value)

/**
 * Extension-function on [KProp].
 *
 * @see [Filter.lt]
 */
inline infix fun <reified T, reified R> KProp<T, @Exact R>.lt(value: R): Filter.LessThan<T, R> =
  Filter.lt(on(), value)

/**
 * Extension-function on [KProp].
 *
 * @see [Filter.lte]
 */
inline infix fun <reified T, reified R> KProp<T, @Exact R>.lte(
  value: R
): Filter.LessThanEqual<T, R> = Filter.lte(on(), value)

/**
 * Extension-function on [KProp].
 *
 * @see [Filter.like]
 */
inline infix fun <reified T> KProp<T, String>.like(value: String): Filter.Like<T> =
  Filter.like(on(), value)

/**
 * Extension-function on [KProp].
 *
 * @see [Filter.contains]
 */
inline infix fun <reified T> KProp<T, String>.contains(value: String): Filter.Contains<T> =
  Filter.contains(on(), value)

/**
 * Extension-function on [KProp].
 *
 * @see [Filter.startsWith]
 */
inline infix fun <reified T> KProp<T, String>.startsWith(value: String): Filter.StartsWith<T> =
  Filter.startsWith(on(), value)

/**
 * Extension-function on [KProp].
 *
 * @see [Filter.endsWith]
 */
inline infix fun <reified T> KProp<T, String>.endsWith(value: String): Filter.EndsWith<T> =
  Filter.endsWith(on(), value)

/**
 * Extension-function on [KProp]. Converts a [KProp] to [Value].
 */
inline infix fun <reified T, reified R> KProp<T, @Exact R>.setTo(value: R): Value<T, R> =
  Value.of(name, value)

/**
 * Extension-function on [KProp]. simpler version of [Lens.zoom].
 *
 * @receiver [KProp]
 */
inline infix fun <reified T, reified R, reified V> KProp<T, @Exact R>.zoom(
  other: KProp<R, V>
): Lens<V, Lens<R, T>> =  Lens.on<T, R>(name).zoom(Lens.on(other.name))
