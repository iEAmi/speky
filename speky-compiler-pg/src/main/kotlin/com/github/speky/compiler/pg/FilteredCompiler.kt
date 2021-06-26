package com.github.speky.compiler.pg

import com.github.speky.compiler.Compiler
import com.github.speky.core.specification.Filtered

//internal class FilteredCompiler<T>(private val compiler: PgSpecificationCompiler<T>) :
//  Compiler<Filtered<T, *>, String> {
//  override fun compile(input: Filtered<T, *>): String = when (input) {
//    is Filtered.JustFiltered<*, *>  -> TODO()
//    is Filtered.WithOrderable<*, *> -> TODO()
//  }
//}
