package com.github.speky.compiler.pg

import com.github.speky.compiler.specification.SpecificationCompiler
import com.github.speky.core.specification.Source
import com.github.speky.core.specification.Specification

class PgSpecificationCompiler(private val tableResolver: TableResolver) :
  SpecificationCompiler<String>, TableResolver by tableResolver {

  private val sourceCompiler: SourceCompiler by lazy { SourceCompiler(this) }
  //  private val sinkCompiler: SinkCompiler<T> by lazy { SinkCompiler(this) }
  //  private val filteredCompiler: FilteredCompiler<T> by lazy { FilteredCompiler(this) }
  //  private val selectedCompiler: SelectedCompiler<T> by lazy { SelectedCompiler(this) }
  //  private val orderedCompiler: OrderedCompiler<T> by lazy { OrderedCompiler(this) }
  //  private val sizedCompiler: SizedCompiler<T> by lazy { SizedCompiler(this) }

  override fun Specification<*>.compile(): String = when (this) {
    //    is Filtered<T, *> -> filteredCompiler.compile(input)
    //    is Ordered<T, *>  -> orderedCompiler.compile(input)
    //    is Selected       -> selectedCompiler.compile(input)
    //    is Sized<T, *>    -> sizedCompiler.compile(input)
    //    is Sink           -> sinkCompiler.compile(input)
    is Source -> with(sourceCompiler) { this@compile.compile() }
    else      -> TODO()
  }.toString()
}
