package com.github.speky.core.table

import com.github.speky.core.Show
import com.github.speky.core.specification.executor.ExecutionResult

/**
 * A reference to constructor of an object.
 *
 * @property functionRef [FunctionRef] to object constructor function
 * @property parameters array of the columns that need to construct new object. order is matter
 *
 * @param T type of the object
 */
class ConstructorRef<T>(
  private val functionRef: FunctionRef<T>,
  private vararg val parameters: Parameter<T, *>
) : (ExecutionResult) -> T {

  /**
   * Validating parameters.
   */
  init {
    require(functionRef.arity == parameters.size) { "Inconsistent arity" }
  }

  /**
   * Convert [executionResult] to [T]. converts column to corresponding value then transforms.
   */
  override fun invoke(executionResult: ExecutionResult): T {
    val paramValues = parameters.map {
      when (it) {
        is Parameter.EmbeddedParam        -> it.embedded.constructorRef().invoke(executionResult)
        is Parameter.NormalParam<*, *, *> ->
          it.column.transformer.fromSqlValue0(
            SqlValueRetriever(it.column, executionResult).retrieve()
          )
      }
    }

    return functionRef.invoke(paramValues)
  }

  /**
   * [ConstructorRef] parameter.
   */
  sealed class Parameter<T, R> {

    companion object {
      /**
       * [Show] instance for [Parameter].
       */
      val show: Show<Parameter<*, *>> = Show {
        when (this) {
          is NormalParam<*, *, *> -> with(Column.show) { column.show() }
          is EmbeddedParam        -> with(Embedded.show) { embedded.show() }
        }
      }

      /**
       * Factory-method to create new instance of [NormalParam].
       */
      fun <T, R, S : SqlValue> of(column: Column<T, R, S>): NormalParam<T, R, S> =
        NormalParam(column)

      /**
       * Factory-method to create new instance of [EmbeddedParam].
       */
      fun <T, R> of(embedded: Embedded<R, T>): EmbeddedParam<T, R> = EmbeddedParam(embedded)
    }

    /**
     * Normal parameter that hold a [Column].
     *
     * @property column [Column] instance
     */
    data class NormalParam<T, R, S : SqlValue>(val column: Column<T, R, S>) : Parameter<T, R>()

    /**
     * [Parameter] that hold [Embedded].
     *
     * @property embedded [Embedded] instance
     */
    data class EmbeddedParam<T, R>(val embedded: Embedded<R, T>) : Parameter<T, R>()
  }

  private class SqlValueRetriever(
    private val column: Column<*, *, *>,
    private val executionResult: ExecutionResult
  ) {

    @Suppress("ComplexMethod")
    fun retrieve(): SqlValue = when (column.sqlType) {
      SqlType.Array                 -> SqlValue.Array(executionResult.getArray(column.name))
      SqlType.Bigint                -> SqlValue.Bigint(executionResult.getBigint(column.name))
      SqlType.Boolean               -> SqlValue.Boolean(executionResult.getBoolean(column.name))
      SqlType.Decimal               -> SqlValue.Decimal(executionResult.getDecimal(column.name))
      SqlType.Double                -> SqlValue.Double(executionResult.getDouble(column.name))
      SqlType.Float                 -> SqlValue.Float(executionResult.getFloat(column.name))
      SqlType.Integer               -> SqlValue.Int(executionResult.getInteger(column.name))
      SqlType.LongVarchar           -> SqlValue.Text(executionResult.getLongVarchar(column.name))
      SqlType.Varchar               -> SqlValue.Varchar(executionResult.getVarchar(column.name))
      SqlType.Other                 -> SqlValue.UUID(executionResult.getOther(column.name))
      SqlType.Real                  -> SqlValue.Real(executionResult.getReal(column.name))
      SqlType.SmallInt              -> SqlValue.Short(executionResult.getSmallInt(column.name))
      SqlType.Date                  -> SqlValue.Date(executionResult.getDate(column.name))
      SqlType.Time                  -> SqlValue.Time(executionResult.getTime(column.name))
      SqlType.TimeWithTimeZone      ->
        SqlValue.Timez(executionResult.getTimeWithTimeZone(column.name))
      SqlType.Timestamp             ->
        SqlValue.Timestamp(executionResult.getTimestamp(column.name))
      SqlType.TimestampWithTimeZone ->
        SqlValue.Timestampz(executionResult.getTimestampWithTimeZone(column.name))
      SqlType.Numeric               -> SqlValue.Numeric(executionResult.getNumeric(column.name))
      SqlType.Blob                  -> SqlValue.Blob(executionResult.getBlob(column.name))
      SqlType.Binary                -> SqlValue.Binary(executionResult.getBinary(column.name))
    }
  }
}
