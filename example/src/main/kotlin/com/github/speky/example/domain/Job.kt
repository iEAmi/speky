package com.github.speky.example.domain

import java.time.LocalTime

data class Job(
  val id: Long,
  val title: String,
  val startTime: LocalTime,
  val endTime: LocalTime,
  val companyId: Long
)
