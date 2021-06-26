package com.github.speky.compiler.pg

import com.github.speky.core.specification.Alias

interface TableResolver {
  fun resolveTableName(alias: Alias<*>): String

  fun Alias<*>.tableName(): String = resolveTableName(this)
}
