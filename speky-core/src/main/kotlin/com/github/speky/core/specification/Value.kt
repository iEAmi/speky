package com.github.speky.core.specification

import com.github.speky.core.Lens
import com.github.speky.core.Show

/**
 * A Pair of [lens] and [value] of it.
 */
data class Value<T, R>(val lens: Lens<R, T>, val value: R) {

  companion object {
    /**
     * [Show] instance for [Value].
     */
    val show: Show<Value<*, *>> = Show { "${with(Lens.show) { lens.show() }} = $value" }

    /**
     * Factory-method to create [Value] instance.
     */
    inline fun <reified T, reified R> of(name: String, value: R): Value<T, R> =
      Value(Lens.on(name), value)
  }
}
