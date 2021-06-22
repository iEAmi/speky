package com.github.speky.core.specification

/**
 * A Filtered [Specification] which filtered by [filter].
 */
class Filtered<T, F>(delegate: Specification<T>, val filter: Filter<T, F>) : Decorator<T>(delegate),
  Orderable<T, Filtered<T, F>>
