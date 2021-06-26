package com.github.speky.compiler

interface Compiler<T, R> {
  fun T.compile(): R
}
