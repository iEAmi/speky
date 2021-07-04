package com.github.speky.postgres.executor

import com.github.speky.core.specification.Sink
import com.github.speky.core.specification.Specification
import com.github.speky.core.specification.compiler.SpecificationCompiler
import com.github.speky.core.specification.executor.SpecificationExecutor
import org.postgresql.ds.PGSimpleDataSource
import java.sql.DriverManager
import java.sql.ResultSet
import javax.sql.DataSource

/**
 * [SpecificationExecutor] that execute [Specification] against postgres database.
 */
class PgSpecificationExecutor(
  private val connectionStr: String,
  private val compiler: SpecificationCompiler<String>,
  private val dataSource: DataSource = PGSimpleDataSource().apply { setURL(connectionStr) }
) : SpecificationExecutor<ResultSet> {

  override fun Specification<*>.execute(): ResultSet {
    val compiled = with(compiler) { compile() }

    return dataSource.connection.createStatement().executeQuery(compiled)
  }

  override fun Sink<*>.execute() {
    val compiled = with(compiler) { compile() }

    dataSource.connection.createStatement().execute(compiled)
  }
}
