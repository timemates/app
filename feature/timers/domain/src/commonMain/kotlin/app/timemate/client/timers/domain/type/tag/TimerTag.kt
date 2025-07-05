package app.timemate.client.timers.domain.type.tag

import app.timemate.client.timers.domain.type.tag.value.TimerTagId
import app.timemate.client.timers.domain.type.tag.value.TimerTagName
import kotlin.time.Instant

data class TimerTag(
    val id: TimerTagId,
    val name: TimerTagName,
    val creationTime: Instant,
)
