package com.github.speky.postgres

import com.github.speky.core.database.GenericDatabase
import com.github.speky.core.specification.Specification
import com.github.speky.postgres.compiler.PgSpecificationCompiler
import com.github.speky.postgres.executor.PgSpecificationExecutor
import java.sql.ResultSet

/**
 * [GenericDatabase] implementation for postgres.
 */
abstract class Database : GenericDatabase<String, ResultSet>() {
  private val compiler: PgSpecificationCompiler by lazy { PgSpecificationCompiler(this, this) }
  private val executor: PgSpecificationExecutor by lazy { PgSpecificationExecutor(compiler) }

  init {
    this.onRegisterTable()
  }

  override fun <T> compile(spec: Specification<T>): String = with(compiler) { spec.compile() }
  override fun <T> execute(spec: Specification<T>): ResultSet = with(executor) { spec.execute() }

  override fun <T> Specification<T>.single(): T? = null

  override fun <T> Specification<T>.first(): T? = null

  override fun <T> Specification<T>.list(): List<T> = emptyList()

  /**
   * Starting-point to register tables.
   */
  abstract fun onRegisterTable()
}
