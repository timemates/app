package org.timemates.app.timers.data.datasource

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.paging3.QueryPagingSource
import io.timemates.data.database.DbTimer
import io.timemates.data.database.DbTimerQueries
import io.timemates.data.database.DbTimerState
import io.timemates.data.database.DbTimerStateQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import org.timemates.app.timers.data.db.LongToIntAdapter
import org.timemates.app.timers.data.db.StateType
import org.timemates.app.timers.data.db.StateTypeAdapter

class DatabaseTimersSource(
    private val driver: SqlDriver,
) {
    private val timersQueries: DbTimerQueries = DbTimerQueries(
        driver,
        DbTimer.Adapter(LongToIntAdapter, LongToIntAdapter),
    )
    private val stateQueries: DbTimerStateQueries = DbTimerStateQueries(
        driver, DbTimerState.Adapter(StateTypeAdapter), DbTimer.Adapter(LongToIntAdapter, LongToIntAdapter)
    )

    fun getTimers() = QueryPagingSource(
        countQuery = stateQueries.count(),
        transacter = stateQueries,
        context = Dispatchers.IO,
        queryProvider = { limit: Long, offset: Long ->
            stateQueries.getWithOffset(limit, offset, combinedTimerAndStateMapper)
        },
    )

    fun getTimer(timerId: Long): Flow<FullTimer?> {
        return combine(
            timersQueries.get(timerId).asFlow(),
            stateQueries.get(timerId).asFlow(),
        ) { timer, state ->
            FullTimer(
                timer.executeAsOneOrNull() ?: return@combine null,
                state.executeAsOneOrNull() ?: return@combine null,
            )
        }
    }

    fun getTimers(ids: List<Long>): List<FullTimer> {
        val timers = timersQueries.getList(ids).executeAsList()
        val states = stateQueries.getList(ids).executeAsList()

        // to ensure consistency
        require(timers.size == states.size)

        return timers.mapIndexed { index, timer -> FullTimer(timer, states[index]) }
    }

    fun getTimersBefore(time: Long): List<FullTimer> {
        val states = stateQueries.getBefore(time).executeAsList()
        val timers = timersQueries.getList(states.map(DbTimerState::timerId)).executeAsList()

        // to ensure consistency
        require(timers.size == states.size)

        return timers.mapIndexed { index, timer -> FullTimer(timer, states[index]) }
    }

    fun getTimersAfter(time: Long): List<FullTimer> {
        val states = stateQueries.getAfter(time).executeAsList()
        val timers = timersQueries.getList(states.map(DbTimerState::timerId)).executeAsList()

        // to ensure consistency
        require(timers.size == states.size)

        return timers.mapIndexed { index, timer -> FullTimer(timer, states[index]) }
    }

    fun getTimersInRange(time: LongRange): List<FullTimer> {
        val states = stateQueries.getInTimeRange(time.first, time.last).executeAsList()
        val timers = timersQueries.getList(states.map(DbTimerState::timerId)).executeAsList()

        // to ensure consistency
        require(timers.size == states.size)

        return timers.mapIndexed { index, timer -> FullTimer(timer, states[index]) }
    }

    suspend fun setState(state: DbTimerState) {
        stateQueries.set(state)
    }

    suspend fun replaceAll(mapped: List<FullTimer>) {
        timersQueries.transaction {
            timersQueries.deleteAll()
            stateQueries.deleteAll()

            mapped.forEach {
                addOrReplaceTimer(it.timer, it.state)
            }
        }
    }

    suspend fun addOrReplaceTimer(timer: DbTimer, state: DbTimerState) {
        timersQueries.transaction {
            timersQueries.add(timer)
            stateQueries.set(state)
        }
    }

    suspend fun remove(timerId: Long) {
        timersQueries.transaction {
            timersQueries.remove(timerId)
            stateQueries.remove(timerId)
        }
    }

    suspend fun remove(ids: List<Long>) {
        timersQueries.transaction {
            timersQueries.removeAll(ids)
            stateQueries.removeAll(ids)
        }
    }

    data class FullTimer(
        val timer: DbTimer,
        val state: DbTimerState,
    )
}

private val combinedTimerAndStateMapper: (
    // state-related
    timerId: Long,
    endsAt: Long,
    publishTime: Long,
    type: StateType,
    lastUpdateTime: Long,
    // general
    timerId_: Long,
    name: String,
    description: String,
    ownerId: Long,
    membersCount: Int,
    workTimeInSeconds: Long,
    restTimeInSeconds: Long,
    bigRestTimeInSeconds: Long,
    bigRestPer: Int,
    isBigRestEnabled: Boolean,
    isEveryoneCanPause: Boolean,
    isConfirmationRequired: Boolean,
    lastUpdateTime_: Long,
) -> DatabaseTimersSource.FullTimer = {
    timerId: Long,
    endsAt: Long,
    publishTime: Long,
    type: StateType,
    lastUpdateTime: Long,
    timerId_: Long,
    name: String,
    description: String,
    ownerId: Long,
    membersCount: Int,
    workTimeInSeconds: Long,
    restTimeInSeconds: Long,
    bigRestTimeInSeconds: Long,
    bigRestPer: Int,
    isBigRestEnabled: Boolean,
    isEveryoneCanPause: Boolean,
    isConfirmationRequired: Boolean,
    lastUpdateTime_: Long,
    ->

    DatabaseTimersSource.FullTimer(
        timer = DbTimer(
            id = timerId,
            name = name,
            description = description,
            ownerId = ownerId,
            membersCount = membersCount,
            workTimeInSeconds = workTimeInSeconds,
            restTimeInSeconds = restTimeInSeconds,
            bigRestTimeInSeconds = bigRestTimeInSeconds,
            bigRestPer = bigRestPer,
            isBigRestEnabled = isBigRestEnabled,
            isEveryoneCanPause = isEveryoneCanPause,
            isConfirmationRequired = isConfirmationRequired,
            lastUpdateTime = lastUpdateTime,
        ),
        state = DbTimerState(
            timerId = timerId,
            endsAt = endsAt,
            publishTime = publishTime,
            type = type,
            lastUpdateTime = lastUpdateTime_,
        ),
    )
}