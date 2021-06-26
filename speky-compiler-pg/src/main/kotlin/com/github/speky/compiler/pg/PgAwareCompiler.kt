package com.github.speky.compiler.pg

import com.github.speky.compiler.Compiler
import com.github.speky.compiler.pg.term.PgTerm
import com.github.speky.compiler.pg.term.WithPgTerms

internal interface PgAwareCompiler<T>: Compiler<T, PgTerm>, WithPgTerms
