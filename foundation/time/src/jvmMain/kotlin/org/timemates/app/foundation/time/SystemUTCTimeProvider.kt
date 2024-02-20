package org.timemates.app.foundation.time

import kotlinx.datetime.Instant

class SystemUTCTimeProvider : TimeProvider {
    override fun provide(): Instant {
        return Instant.fromEpochMilliseconds(System.currentTimeMillis())
    }
}