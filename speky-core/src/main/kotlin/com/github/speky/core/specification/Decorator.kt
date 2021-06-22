package com.github.speky.core.specification

/**
 * Decorator pattern for [Specification].
 *
 * @property delegate original [Specification]
 */
sealed class Decorator<T>(val delegate: Specification<T>) : Specification<T>(delegate.alias)
