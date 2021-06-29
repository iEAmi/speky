package com.github.speky.core.table

import com.github.speky.core.Lens
import com.github.speky.core.Show

/**
 * Defines column in relational databases.
 *
 * @property name of the column in database
 * @property lens [Lens] points to property of this column
 * @property sqlType type of the column in database
 * @property transformer [ValueTransformer] for transforming value to/from sql
 *
 * @param T type of the class this column belongs to that
 * @param R type of the column
 */
data class Column<T, R, V : SqlValue>(
  val name: String,
  val lens: Lens<R, T>,
  val sqlType: SqlType,
  val transformer: ValueTransformer<R, V>
) {

  companion object {
    /**
     * [Show] instance for [Column].
     */
    val show: Show<Column<*, *, *>> = Show { "$name: ${with(SqlType.show) { sqlType.show() }}" }
  }
}
