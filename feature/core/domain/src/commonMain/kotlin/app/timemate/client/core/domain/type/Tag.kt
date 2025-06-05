package app.timemate.client.core.domain.type

import app.timemate.client.core.domain.type.value.TagId
import app.timemate.client.core.domain.type.value.TagName
import kotlin.time.Instant

data class Tag(
    val id: TagId,
    val name: TagName,
    val creationTime: Instant,
)