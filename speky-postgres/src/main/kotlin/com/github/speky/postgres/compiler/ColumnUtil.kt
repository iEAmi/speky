package com.github.speky.postgres.compiler

internal interface ColumnUtil {

  fun wrapInQuotes(a: Any?): Any? = if (a is String? || a is String) "'$a'" else a
}
