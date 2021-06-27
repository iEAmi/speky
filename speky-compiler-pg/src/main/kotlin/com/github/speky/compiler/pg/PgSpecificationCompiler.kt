package com.github.speky.compiler.pg

import com.github.speky.compiler.pg.resolve.ColumnResolver
import com.github.speky.compiler.pg.resolve.TableResolver
import com.github.speky.compiler.specification.SpecificationCompiler
import com.github.speky.core.specification.*

class PgSpecificationCompiler(
  private val tableResolver: TableResolver,
  private val columnResolver: ColumnResolver
) : SpecificationCompiler<String>, TableResolver by tableResolver,
  ColumnResolver by columnResolver {

  private val sourceCompiler: SourceCompiler by lazy { SourceCompiler(this) }

  //  private val sinkCompiler: SinkCompiler<T> by lazy { SinkCompiler(this) }
  //  private val filteredCompiler: FilteredCompiler<T> by lazy { FilteredCompiler(this) }
  private val selectedCompiler: SelectedCompiler by lazy { SelectedCompiler(this) }
  private val orderedCompiler: OrderedCompiler by lazy { OrderedCompiler(this) }
  private val sizedCompiler: SizedCompiler by lazy { SizedCompiler(this) }

  override fun Specification<*>.compile(): String = when (this) {
    //    is Filtered<T, *> -> filteredCompiler.compile(input)
    is Ordered<*, *> -> with(orderedCompiler) { this@compile.compile() }.toString()
    is Selected      -> with(selectedCompiler) { this@compile.compile() }.toString()
    is Sized<*, *>   -> with(sizedCompiler) { this@compile.compile() }.toString()
    //    is Sink           -> sinkCompiler.compile(input)
    is Source        -> with(sourceCompiler) { this@compile.compile() }.toString()
    else             -> TODO()
  }
}
