package com.github.speky.postgres

import com.github.speky.core.database.GenericDatabase
import com.github.speky.core.specification.Sink
import com.github.speky.core.specification.Specification
import com.github.speky.core.table.Table
import com.github.speky.postgres.compiler.PgSpecificationCompiler
import com.github.speky.postgres.executor.ExecutionResultFromResultSet
import com.github.speky.postgres.executor.PgSpecificationExecutor
import java.sql.ResultSet

/**
 * [GenericDatabase] implementation for postgres.
 */
abstract class Database(private val connectionStr: String) : GenericDatabase<String, ResultSet>() {
  private val compiler: PgSpecificationCompiler by lazy {
    PgSpecificationCompiler(this, this)
  }
  private val executor: PgSpecificationExecutor by lazy {
    PgSpecificationExecutor(connectionStr, compiler)
  }

  init {
    this.onRegisterTable()
  }

  override fun <T> compile(spec: Specification<T>): String = with(compiler) { spec.compile() }
  override fun <T> execute(spec: Specification<T>): ResultSet = with(executor) { spec.execute() }
  override fun <T> execute(spec: Sink<T>) = with(executor) { spec.execute() }

  @Suppress("UNCHECKED_CAST")
  override fun <T> Specification<T>.list(): List<T> {
    val resultSet = execute(this)

    val list = mutableListOf<T>()
    while (resultSet.next()) {
      val constructorRef = (resolveTable(alias)!! as Table<T>).constructorRef()

      list += constructorRef.invoke(ExecutionResultFromResultSet(resultSet))
    }

    return list
  }

  /**
   * Starting-point to register tables.
   */
  abstract fun onRegisterTable()
}
