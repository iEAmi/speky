package com.github.speky.core.table

import com.github.speky.core.ClassRef
import com.github.speky.core.Lens
import com.github.speky.core.Show

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
@Suppress("UnusedPrivateMember")
abstract class Embedded<E, T>(
  internal val columnPrefix: String,
  internal val lens: Lens<E, T>,
  internal val classRef: ClassRef<E>,
) : ColumnDefinition<E>(), Constructible<E> {

  override fun columns(): Set<Column<E, *, *>> =
    super.columns().map { it.copy(name = columnPrefix + it.name) }.toSet()

  companion object {
    /**
     * [Show] instance for [Embedded].
     */
    val show: Show<Embedded<*, *>> =
      Show { "Embedded ${lens.propertyRef.name}: ${classRef.name}" }
  }
}
