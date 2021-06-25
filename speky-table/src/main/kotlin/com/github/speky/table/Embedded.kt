package com.github.speky.table

import com.github.speky.core.ClassRef
import com.github.speky.core.Lens

/**
 * Embedded type in table. [Embedded] only could defined in context of a [Table].
 *
 * @property columnPrefix a prefix for columns
 * @property lens to point to the property of type [E] in [T]
 * @property classRef [ClassRef] of the [E]
 *
 * @param E type of the Embedded
 * @param T type of the table
 */
abstract class Embedded<E, T>(
  private val columnPrefix: String,
  private val lens: Lens<E, T>,
  internal val classRef: ClassRef<E>,
) : ColumnDefinition<E>(), Constructible<E>
