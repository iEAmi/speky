package com.github.speky.compiler.pg

import com.github.speky.compiler.Compiler
import com.github.speky.compiler.pg.internal.PgTerm
import com.github.speky.compiler.pg.internal.WithPgTerms

internal interface PgAwareCompiler<T> : Compiler<T, PgTerm>, WithPgTerms
