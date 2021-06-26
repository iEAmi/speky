package com.github.speky.compiler.specification

import com.github.speky.compiler.Compiler
import com.github.speky.core.specification.Specification

interface SpecificationCompiler<R> : Compiler<Specification<*>, R>
