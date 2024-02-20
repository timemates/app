package org.timemates.app.foundation.time

import kotlinx.datetime.Instant

/**
 * Interface for providing current time in UTC.
 */
interface TimeProvider {
    /**
     * Provides the current time in UTC.
     *
     * @return The current time as an Instant object.
     */
    fun provide(): Instant
}
