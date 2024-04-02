package org.timemates.app.timers.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.map
import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import io.timemates.data.database.DbInvite
import io.timemates.data.database.DbTimer
import io.timemates.data.database.DbTimerState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import org.timemates.app.foundation.time.TimeProvider
import org.timemates.app.timers.data.datasource.DatabaseTimersSource
import org.timemates.app.timers.data.db.StateType
import org.timemates.app.timers.data.paging.user_timers.UsersTimersRemoteMediator
import org.timemates.sdk.common.constructor.createOrThrow
import org.timemates.sdk.common.pagination.PageToken
import org.timemates.sdk.common.pagination.PagesIterator
import org.timemates.sdk.common.types.Empty
import org.timemates.sdk.common.types.value.Count
import org.timemates.sdk.timers.TimersApi
import org.timemates.sdk.timers.members.invites.types.Invite
import org.timemates.sdk.timers.sessions.TimersSessionsApi
import org.timemates.sdk.timers.types.Timer
import org.timemates.sdk.timers.types.TimerSettings
import org.timemates.sdk.timers.types.value.TimerDescription
import org.timemates.sdk.timers.types.value.TimerId
import org.timemates.sdk.timers.types.value.TimerName
import org.timemates.sdk.users.profile.types.value.UserId
import kotlin.time.Duration.Companion.seconds
import org.timemates.app.users.repositories.TimersRepository as TimersRepositoryContract

class TimersRepository(
    private val timersApi: TimersApi,
    private val timersSessionsApi: TimersSessionsApi,
    private val dbTimersSource: DatabaseTimersSource,
    private val timeProvider: TimeProvider,
) : TimersRepositoryContract {

    @OptIn(ExperimentalPagingApi::class)
    override fun getUserTimers(pageToken: PageToken?): Flow<PagingData<Timer>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
            ),
            initialKey = null,
            pagingSourceFactory = { dbTimersSource.getTimers() },
            remoteMediator = UsersTimersRemoteMediator(timersApi, dbTimersSource, timeProvider),
        ).flow.map { list -> list.map { it.toDomainTimer() } }
    }

    override suspend fun getTimer(id: TimerId): Flow<Timer> = channelFlow {
        launch {
            dbTimersSource.getTimer(id.long).collect {
                send(it?.toDomainTimer() ?: return@collect)
            }
        }

        launch {
            timersApi.getTimer(id).onSuccess { timer ->
                val db = timer.toDbTimer(timeProvider.provide())
                dbTimersSource.addOrReplaceTimer(db.timer, db.state)
            }.getOrThrow()
        }
    }

    override suspend fun getTimerState(id: TimerId): Result<Flow<Timer.State>> {
        return timersSessionsApi.getTimerState(id)
    }

    override suspend fun createTimer(name: TimerName, description: TimerDescription, settings: TimerSettings): Result<TimerId> {
        return timersApi.createTimer(name, description, settings).onSuccess {
            val remoteTimer = timersApi.getTimer(it)
                .getOrNull()
                ?: return@onSuccess
            val (timer, type) = remoteTimer.toDbTimer(timeProvider.provide())
            dbTimersSource.addOrReplaceTimer(timer, type)
        }
    }

    override suspend fun removeTimer(timerId: TimerId): Result<Empty> {
        return timersApi.removeTimer(timerId).onSuccess {
            dbTimersSource.remove(timerId.long)
        }
    }

    override suspend fun editTimer(timerId: TimerId, newName: TimerName?, newDescription: TimerDescription?, settings: TimerSettings.Patch?): Result<Empty> {
        return timersApi.editTimer(timerId, newName, newDescription, settings).onSuccess { _ ->
            val dbTimer = dbTimersSource.getTimer(timerId.long).first()!!
            val changedTimer = dbTimer.copy(
                timer = dbTimer.timer.copy(
                    name = newName?.string ?: dbTimer.timer.name,
                    description = newDescription?.string ?: dbTimer.timer.description,
                    workTimeInSeconds = settings?.workTime?.inWholeSeconds
                        ?: dbTimer.timer.workTimeInSeconds,
                    restTimeInSeconds = settings?.restTime?.inWholeSeconds
                        ?: dbTimer.timer.restTimeInSeconds,
                    bigRestTimeInSeconds = settings?.bigRestTime?.inWholeSeconds
                        ?: dbTimer.timer.bigRestTimeInSeconds,
                    bigRestPer = settings?.bigRestPer?.int ?: dbTimer.timer.bigRestPer,
                    isBigRestEnabled = settings?.bigRestEnabled ?: dbTimer.timer.isBigRestEnabled,
                    isEveryoneCanPause = settings?.isEveryoneCanPause
                        ?: dbTimer.timer.isEveryoneCanPause,
                    isConfirmationRequired = settings?.isConfirmationRequired
                        ?: dbTimer.timer.isConfirmationRequired,
                    lastUpdateTime = timeProvider.provide().toEpochMilliseconds(),
                )
            )

            dbTimersSource.addOrReplaceTimer(changedTimer.timer, changedTimer.state)
        }
    }
}

internal fun DatabaseTimersSource.FullTimer.toDomainTimer(): Timer {
    return Timer(
        timerId = TimerId.factory.createOrThrow(timer.id),
        name = TimerName.factory.createOrThrow(timer.name),
        description = TimerDescription.factory.createOrThrow(timer.description),
        ownerId = UserId.factory.createOrThrow(timer.ownerId),
        membersCount = Count.factory.createOrThrow(timer.membersCount),
        state = state.toDomainTimerState(),
        settings = TimerSettings(
            workTime = timer.workTimeInSeconds.seconds,
            restTime = timer.workTimeInSeconds.seconds,
            bigRestTime = timer.workTimeInSeconds.seconds,
            bigRestEnabled = timer.isBigRestEnabled,
            bigRestPer = Count.factory.createOrThrow(timer.bigRestPer),
            isEveryoneCanPause = timer.isEveryoneCanPause,
            isConfirmationRequired = timer.isConfirmationRequired,
        )
    )
}

internal fun DbTimerState.toDomainTimerState(): Timer.State {
    val publishTime = Instant.fromEpochMilliseconds(publishTime)
    val endsAt = Instant.fromEpochMilliseconds(endsAt)

    return when (type) {
        StateType.INACTIVE -> Timer.State.Inactive(publishTime)
        StateType.PAUSED -> Timer.State.Paused(publishTime)
        StateType.CONFIRMATION_WAITING -> Timer.State.ConfirmationWaiting(endsAt, publishTime)
        StateType.RUNNING -> Timer.State.Running(endsAt, publishTime)
        StateType.REST -> Timer.State.Rest(endsAt, publishTime)
    }
}

private fun <E> List<E>.asPagesIterator(elementsPerPage: Int = 20): PagesIterator<E> {
    val originIterator = iterator()

    return object : PagesIterator<E> {
        override fun hasNext(): Boolean {
            return originIterator.hasNext()
        }

        override suspend fun next(): Result<List<E>> {
            return Result.success(originIterator.next(elementsPerPage))
        }
    }
}

private fun <E> Iterator<E>.next(count: Int): List<E> {
    var total = 0
    val list: MutableList<E> = mutableListOf()

    while (hasNext()) {
        if (total == count) break
        list += next()
        total++
    }

    return list
}

internal fun Timer.toDbTimer(time: Instant): DatabaseTimersSource.FullTimer {
    return DatabaseTimersSource.FullTimer(
        timer = DbTimer(
            id = timerId.long,
            name = name.string,
            description = description.string,
            ownerId = ownerId.long,
            membersCount = membersCount.int,
            workTimeInSeconds = settings.workTime.inWholeSeconds,
            restTimeInSeconds = settings.restTime.inWholeSeconds,
            bigRestTimeInSeconds = settings.bigRestTime.inWholeSeconds,
            bigRestPer = settings.bigRestPer.int,
            isBigRestEnabled = settings.bigRestEnabled,
            isEveryoneCanPause = settings.isEveryoneCanPause,
            isConfirmationRequired = settings.isConfirmationRequired,
            lastUpdateTime = time.toEpochMilliseconds(),
        ),
        state = state.toDbState(timerId, time),
    )
}

private fun Timer.State.toDbState(timerId: TimerId, time: Instant): DbTimerState {
    val type = when (this) {
        is Timer.State.ConfirmationWaiting -> StateType.CONFIRMATION_WAITING
        is Timer.State.Inactive -> StateType.INACTIVE
        is Timer.State.Paused -> StateType.PAUSED
        is Timer.State.Rest -> StateType.REST
        is Timer.State.Running -> StateType.RUNNING
    }

    return DbTimerState(
        timerId = timerId.long,
        endsAt = endsAt?.toEpochMilliseconds() ?: Long.MAX_VALUE,
        publishTime = publishTime.toEpochMilliseconds(),
        type = type,
        lastUpdateTime = time.toEpochMilliseconds(),
    )
}

private fun Invite.toDbInvite(timerId: Long): DbInvite {
    return DbInvite(
        timerId = timerId,
        code = inviteCode.string,
        count = limit.int,
        creationTime = creationTime.toEpochMilliseconds(),
    )
}