package com.github.speky.extension

import com.github.speky.core.Lens
import com.github.speky.core.specification.Value
import kotlin.internal.Exact

/**
 * Extension-function on [Lens]. Converts [Lens] to [Value].
 */
infix fun <T, R> Lens<@Exact R, T>.setTo(value: R): Value<T, R> =
  Value(this, value)
