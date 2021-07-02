package com.github.speky.example.infra.table

import com.github.speky.core.ClassRef
import com.github.speky.core.table.ConstructorRef
import com.github.speky.core.table.ConstructorRef.Parameter
import com.github.speky.example.domain.Job
import com.github.speky.extension.RichTable

object Jobs : RichTable<Job>("jobs", ClassRef.of()) {
  private val id = Job::id.asBigint()
  private val title = Job::title.asVarchar()
  private val startTime = Job::startTime.asTimez("start_time")
  private val endTime = Job::endTime.asTimez("end_time")
  private val companyId = Job::companyId.asBigint("company_id")

  override fun constructorRef(): ConstructorRef<Job> =
    ::Job.ctorRef(
      Parameter.of(id),
      Parameter.of(title),
      Parameter.of(startTime),
      Parameter.of(endTime),
      Parameter.of(companyId)
    )
}
