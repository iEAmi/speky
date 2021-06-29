@file:Suppress("NoUnusedImports", "UnusedImports", "CommentSpacing", "SpacingAroundComma")

package com.github.speky.extension

//abstract class RichTable<T>(tableName: String, val classRef: ClassRef<T>) : Table<T>(tableName) {
//  inline fun <reified R> KProperty1<T, R>.`as`(
//    sqlType: SqlType,
//    columnName: String = name
//  ): Column<T, R> = Column(columnName, lensOf(), sqlType)
//
//  fun KProperty1<T, Int>.asInt(columnName: String = name): Column<T, Int> =
//    `as`(SqlType.Integer, columnName)
//
//  fun KProperty1<T, Long>.asBigint(columnName: String = name): Column<T, Long> =
//    `as`(SqlType.Bigint, columnName)
//
//  fun KProperty1<T, String>.asVarchar(columnName: String = name): Column<T, String> =
//    `as`(SqlType.Varchar, columnName)
//
//  fun KProperty1<T, String>.asText(columnName: String = name): Column<T, String> =
//    `as`(SqlType.LongVarchar, columnName)
//
//  fun KProperty1<T, Boolean>.asBoolean(columnName: String = name): Column<T, Boolean> =
//    `as`(SqlType.Boolean, columnName)
//
//  fun KProperty1<T, UUID>.asUUID(columnName: String = name): Column<T, UUID> =
//    `as`(SqlType.Other, columnName)
//
//  fun KProperty1<T, LocalDateTime>.asTimestamp(
//    columnName: String = name
//  ): Column<T, LocalDateTime> = `as`(SqlType.Timestamp, columnName)
//
//  fun KProperty1<T, LocalDateTime>.asTimestampz(
//    columnName: String = name
//  ): Column<T, LocalDateTime> = `as`(SqlType.TimestampWithTimeZone, columnName)
//
//  @PublishedApi
//  internal inline fun <reified R> KProperty1<T, R>.lensOf(): Lens<R, T> =
//    Lens.Focus(PropertyRef.of(name, classRef))
//}
