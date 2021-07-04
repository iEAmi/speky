package com.github.speky.extension

import com.github.speky.core.Lens
import com.github.speky.core.specification.Value
import kotlin.internal.Exact

/**
 * Extension-function on [Lens]. Converts [Lens] to [Value].
 */
infix fun <T, R> Lens<@Exact R, T>.setTo(value: R): Value<T, R> =
  Value(this, value)

/**
 * Extension-function on [Lens]. Converts [Lens] to [Value].
 */
@Suppress("UNCHECKED_CAST")
@JvmName("setToVRT")
infix fun <T, R, V> Lens<@Exact V, Lens<@Exact R, T>>.setTo(value: V): Value<T, V> =
  Value(this as Lens<V, T>, value)

