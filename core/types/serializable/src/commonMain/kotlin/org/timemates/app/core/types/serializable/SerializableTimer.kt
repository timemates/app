package org.timemates.app.core.types.serializable

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.timemates.sdk.timers.types.Timer
import org.timemates.sdk.timers.types.TimerSettings
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

@Serializable
data class SerializableTimer(
    val timerId: Long,
    val name: String,
    val description: String,
    val ownerId: Long,
    val membersCount: Int,
    val state: State,
    val settings: Settings,
) {
    @Serializable
    sealed class State {
        abstract val endsAt: Instant?
        abstract val publishTime: Instant

        /**
         * Represents a paused state of the TimeMates entity.
         * Paused states do not have an exact time to be expired and are usually paused by force
         * for an indefinite amount of time. They can be resumed only on purpose. The server may
         * decide to expire paused states after some time, but the client shouldn't focus on that
         * and should handle the state accordingly.
         *
         * @property publishTime The time when the paused state was published.
         */
        @SerialName("Paused")
        data class Paused(
            override val publishTime: Instant,
        ) : State() {
            override val endsAt: Instant? = null
        }

        @SerialName("ConfirmationWaiting")
        data class ConfirmationWaiting(
            override val endsAt: Instant,
            override val publishTime: Instant,
        ) : State()

        /**
         * Represents an inactive state of the TimeMates entity.
         *
         * @property publishTime The time when the inactive state was published.
         */
        @SerialName("Inactive")
        data class Inactive(
            override val publishTime: Instant,
        ) : State() {
            override val endsAt: Instant? = null
        }

        /**
         * Represents a running state of the TimeMates entity.
         *
         * @property endsAt The time when the running state will lose its actuality.
         * @property publishTime The time when the running state was published.
         */
        @SerialName("Running")
        data class Running(
            override val endsAt: Instant,
            override val publishTime: Instant,
        ) : State()

        /**
         * Represents a rest state of the TimeMates entity.
         *
         * @property endsAt The time when the rest state will lose its actuality.
         * @property publishTime The time when the rest state was published.
         */
        @SerialName("Rest")
        data class Rest(
            override val endsAt: Instant,
            override val publishTime: Instant,
        ) : State()
    }

    @Serializable
    data class Settings(
        val workTime: Duration = 25.minutes,
        val restTime: Duration = 5.minutes,
        val bigRestTime: Duration = 10.minutes,
        val bigRestEnabled: Boolean = true,
        val bigRestPer: Int = 4,
        val isEveryoneCanPause: Boolean = false,
        val isConfirmationRequired: Boolean = false,
    )
}

fun Timer.serializable(): SerializableTimer {
    return SerializableTimer(
        timerId = timerId.long,
        name = name.string,
        description = description.string,
        ownerId = ownerId.long,
        membersCount = membersCount.int,
        state = state.serializable(),
        settings = settings.serializable(),
    )
}

fun Timer.State.serializable(): SerializableTimer.State {
    return when (this) {
        is Timer.State.ConfirmationWaiting -> SerializableTimer.State.ConfirmationWaiting(
            endsAt = endsAt,
            publishTime = publishTime,
        )

        is Timer.State.Inactive -> SerializableTimer.State.Inactive(
            publishTime = publishTime,
        )

        is Timer.State.Paused -> SerializableTimer.State.Paused(
            publishTime = publishTime,
        )

        is Timer.State.Rest -> SerializableTimer.State.Rest(
            endsAt = endsAt,
            publishTime = publishTime,
        )

        is Timer.State.Running -> SerializableTimer.State.Running(
            endsAt = endsAt,
            publishTime = publishTime,
        )
    }
}

fun TimerSettings.serializable(): SerializableTimer.Settings {
    return SerializableTimer.Settings(
        workTime = workTime,
        restTime = restTime,
        bigRestTime = bigRestTime,
        bigRestEnabled = bigRestEnabled,
        bigRestPer = bigRestPer.int,
        isEveryoneCanPause = isEveryoneCanPause,
        isConfirmationRequired = isConfirmationRequired,
    )
}