package com.github.speky.example.infra.table

import com.github.speky.core.ClassRef
import com.github.speky.core.specification.Value
import com.github.speky.core.table.ConstructorRef
import com.github.speky.core.table.ConstructorRef.Parameter
import com.github.speky.example.domain.Job
import com.github.speky.extension.RichTable
import com.github.speky.extension.insertOn
import java.time.LocalTime

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

  val initValues = insertOn<Job>(
    Value.of("id", 1),
    Value.of("title", "Software programmer"),
    Value.of("startTime", LocalTime.of(8, 0)),
    Value.of("endTime", LocalTime.of(16, 0)),
    Value.of("companyId", 1L), // google

    Value.of("id", 2L),
    Value.of("title", "DevOps programmer"),
    Value.of("startTime", LocalTime.of(8, 0)),
    Value.of("endTime", LocalTime.of(16, 0)),
    Value.of("companyId", 2L), // apple

    Value.of("id", 3L),
    Value.of("title", "Android programmer"),
    Value.of("startTime", LocalTime.of(8, 0)),
    Value.of("endTime", LocalTime.of(16, 0)),
    Value.of("companyId", 3L), // amazon,

    Value.of("id", 4L),
    Value.of("title", "iOS programmer"),
    Value.of("startTime", LocalTime.of(8, 0)),
    Value.of("endTime", LocalTime.of(16, 0)),
    Value.of("companyId", 4L) // uber,
  )
}
