package com.github.speky.core.table

import com.github.speky.core.ClassRef
import com.github.speky.core.PropertyRef

/**
 * Presents a table in relational databases.
 *
 * @property tableName name of the table in database.
 *
 * @param T type of the class which it belong to
 */
abstract class Table<T>(val tableName: String) : ColumnDefinition<T>(), Constructible<T>,
  EmbeddedResolver {
  private val embeds: MutableSet<Embedded<*, T>> = mutableSetOf()

  /**
   * Registers new [Embedded].
   */
  fun <E, EE : Embedded<E, T>> embedded(embedded: EE): EE =
    embedded.apply { registerEmbedded(this) }

  override fun resolveColumns(prop: PropertyRef<*>): ColumnResolver.ResolveResult =
    super.resolveColumns(prop)
      .ifNotFound {
        val embed = embeds.singleOrNull { it.resolveColumns(prop).isFound() }

        embed?.resolveColumns(prop) ?: ColumnResolver.notFound()
      }
      .ifNotFound {
        val embed = embeds.singleOrNull {
          it.classRef == prop.classRef && it.lens.propertyRef.name == prop.name
        }

        if (embed == null) ColumnResolver.notFound() else ColumnResolver.embedded(embed)
      }

  override fun isEmbedded(clsRef: ClassRef<*>): Boolean =
    embeds.any { it.classRef == clsRef }

  @PublishedApi
  internal fun registerEmbedded(embedded: Embedded<*, T>) {
    require(embeds.any {
      it.classRef == embedded.classRef && it.columnPrefix == embedded.columnPrefix
    }.not()) { "Duplicate embedded '${embedded.classRef.name}'" }

    embeds.add(embedded)
  }

  override fun toString(): String = "Table(tableName='$tableName')"
}
