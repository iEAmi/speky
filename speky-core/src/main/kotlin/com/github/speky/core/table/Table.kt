package com.github.speky.core.table

import com.github.speky.core.PropertyRef

/**
 * Presents a table in relational databases.
 *
 * @property tableName name of the table in database.
 *
 * @param T type of the class which it belong to
 */
abstract class Table<T>(val tableName: String) : ColumnDefinition<T>(), Constructible<T> {
  private val embeds: MutableSet<Embedded<*, T>> = mutableSetOf()

  /**
   * Registers new [Embedded].
   */
  fun <E, EE : Embedded<E, T>> embedded(embedded: EE): EE =
    embedded.apply { registerEmbedded(this) }

  override fun resolveColumns(prop: PropertyRef<*>): List<Column<*, *, *>> {
    val column = super.resolveColumns(prop)
      .ifEmpty { embeds.singleOrNull { it.resolveColumns(prop).any() }?.resolveColumns(prop) }

    if (column == null) {
      // search in embeds
      val embed = embeds.singleOrNull {
        it.classRef == prop.classRef && it.lens.propertyRef.name == prop.name
      } ?: return emptyList()

      return embed.columns().toList()
    }

    return column
  }

  @PublishedApi
  internal fun registerEmbedded(embedded: Embedded<*, T>) {
    require(embeds.any {
      it.classRef == embedded.classRef && it.columnPrefix == embedded.columnPrefix
    }.not()) { "Duplicate embedded '${embedded.classRef.name}'" }

    embeds.add(embedded)
  }

  override fun toString(): String = "Table(tableName='$tableName')"
}
