package com.github.speky.postgres.executor

import com.github.speky.core.specification.Specification
import com.github.speky.core.specification.compiler.SpecificationCompiler
import com.github.speky.core.specification.executor.SpecificationExecutor
import com.github.speky.core.table.ColumnResolver
import com.github.speky.core.table.TableResolver
import com.github.speky.postgres.compiler.PgSpecificationCompiler
import org.postgresql.ds.PGSimpleDataSource
import java.sql.ResultSet
import javax.sql.DataSource

/**
 * [SpecificationExecutor] that execute [Specification] against postgres database.
 */
class PgSpecificationExecutor(
  private val columnResolver: ColumnResolver,
  private val tableResolver: TableResolver,
  private val dataSource: DataSource = PGSimpleDataSource(),
  private val compiler: SpecificationCompiler<String> =
    PgSpecificationCompiler(tableResolver, columnResolver)
) : SpecificationExecutor<ResultSet> {

  override fun Specification<*>.execute(): ResultSet {
    val compiled = with(compiler) { compile() }

    return dataSource.connection.createStatement().executeQuery(compiled)
  }
}
