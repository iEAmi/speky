package com.github.speky.example.domain

data class Employee(
  val id: Long,
  val name: String,
  val family: String,
  val address: Address,
  val jobId: Long
)
