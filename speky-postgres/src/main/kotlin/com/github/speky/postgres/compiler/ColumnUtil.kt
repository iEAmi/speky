package com.github.speky.postgres.compiler

import java.time.LocalTime

internal interface ColumnUtil {

  fun wrapInQuotes(a: Any?): Any? = when (a) {
    is String?, is String       -> "'$a'"
    is LocalTime?, is LocalTime -> "'$a'"
    else                        -> a
  }
}
