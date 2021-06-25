package com.github.speky.table

/**
 * Presents a table in relational databases.
 *
 * @property tableName name of the table in database.
 *
 * @param T type of the class which it belong to
 */
abstract class Table<T>(private val tableName: String) : ColumnDefinition<T>(), Constructible<T> {
  private val embeds: MutableSet<Embedded<*, T>> = mutableSetOf()

  /**
   * Register
   */
  fun <E> embedded(embedded: Embedded<E, T>): Embedded<E, T> =
    embedded.apply { registerEmbedded(this) }

  @PublishedApi
  internal fun registerEmbedded(embedded: Embedded<*, T>) {
    require(embeds.any { it.classRef == embedded.classRef }.not()) {
      "Duplicate embedded '${embedded.classRef.name}'"
    }

    embeds.add(embedded)
  }

  override fun toString(): String = "Table(tableName='$tableName')"
}
