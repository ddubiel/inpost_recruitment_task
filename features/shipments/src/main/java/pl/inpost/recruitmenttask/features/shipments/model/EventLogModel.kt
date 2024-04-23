package pl.inpost.recruitmenttask.features.shipments.model

import java.time.ZonedDateTime

data class EventLogModel(
    val name: String,
    val date: ZonedDateTime,
)
