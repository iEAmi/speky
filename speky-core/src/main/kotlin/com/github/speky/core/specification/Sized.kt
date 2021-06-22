package com.github.speky.core.specification

/**
 * A [Decorator] that add limit and offset to [Ordered].
 *
 * @property limit like limit in sql
 * @property offset like offset in sql
 */
class Sized<T, V>(ordered: Ordered<T, V>, val limit: Int, val offset: Int) : Decorator<T>(ordered)
