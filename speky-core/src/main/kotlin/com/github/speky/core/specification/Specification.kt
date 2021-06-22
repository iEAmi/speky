package com.github.speky.core.specification

import com.github.speky.core.ClassRef
import com.github.speky.core.specification.Source.Single

/**
 * Definition of the specification. each specification is wrote for specific [ClassRef].
 *
 * @property alias alias for [ClassRef]
 * @param T type of the class which specification is wrote for that.
 */
@Suppress("NON_PRIVATE_OR_PROTECTED_CONSTRUCTOR_IN_SEALED")
sealed class Specification<T> internal constructor(open val alias: Alias<T>) {

  /**
   * [ClassRef] of the specification class.
   */
  val classRef: ClassRef<T> get() = alias.classRef

  companion object {

    /**
     * Invoke operator to create new [Specification] instance.
     *
     * @param alias specification alias
     */
    operator fun <T> invoke(alias: Alias<T>): Single<T> = Single(alias)

    /**
     * @see [invoke].
     */
    inline operator fun <reified T> invoke(alias: String): Single<T> = invoke(Alias(alias))
  }
}
