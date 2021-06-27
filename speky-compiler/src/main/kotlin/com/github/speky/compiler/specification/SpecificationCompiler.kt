package com.github.speky.compiler.specification

import com.github.speky.compiler.Compiler
import com.github.speky.core.specification.Specification

/**
 * Special [Compiler] that compiles [Specification] to [R].
 */
interface SpecificationCompiler<R> : Compiler<Specification<*>, R>
