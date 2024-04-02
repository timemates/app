package org.timemates.app.timers.data.paging.user_timers

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.timemates.app.foundation.time.TimeProvider
import org.timemates.app.timers.data.datasource.DatabaseTimersSource
import org.timemates.app.timers.data.toDbTimer
import org.timemates.sdk.common.pagination.PagesIterator
import org.timemates.sdk.timers.TimersApi
import org.timemates.sdk.timers.getUserTimersPages
import org.timemates.sdk.timers.types.Timer
import kotlin.properties.Delegates

@OptIn(ExperimentalPagingApi::class)
class UsersTimersRemoteMediator(
    private val timersApi: TimersApi,
    private val dbTimers: DatabaseTimersSource,
    private val timeProvider: TimeProvider,
) : RemoteMediator<Int, DatabaseTimersSource.FullTimer>() {
    // will be initialized at the REFRESH event
    private var iterator: PagesIterator<Timer> by Delegates.notNull()
    private val mutex = Mutex()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, DatabaseTimersSource.FullTimer>): MediatorResult = mutex.withLock {
        return@withLock when (loadType) {
            LoadType.REFRESH -> {
                iterator = timersApi.getUserTimersPages()
                loadTimers()
            }

            LoadType.PREPEND -> MediatorResult.Success(true)

            LoadType.APPEND -> {
                loadTimers()
            }
        }
    }

    private suspend fun loadTimers(): MediatorResult {
        if (!iterator.hasNext()) return MediatorResult.Success(true)

        return iterator.next()
            .onSuccess { list ->
                val mapped = list.map { it.toDbTimer(timeProvider.provide()) }

                predictAndRemoveTimers(mapped)

                if (!iterator.hasNext() && mapped.any()) {
                    dbTimers.getTimersBefore(mapped.minOf { it.state.publishTime })
                        .map { it.timer.id }
                        .let { dbTimers.remove(it) }
                }

                mapped.forEach {
                    dbTimers.addOrReplaceTimer(it.timer, it.state)
                }
            }
            .map { MediatorResult.Success(!iterator.hasNext()) }
            .getOrElse { MediatorResult.Error(it) }
    }

    private suspend fun predictAndRemoveTimers(mapped: List<DatabaseTimersSource.FullTimer>) {
        val loadedMap = mapped.associateBy { it.timer.id }
        val minTime = mapped.minOfOrNull { it.state.publishTime }
        val maxTime = mapped.maxOfOrNull { it.state.publishTime }

        if (minTime != null && maxTime != null) {
            dbTimers.getTimersInRange(minTime..maxTime)
                .filter { !loadedMap.containsKey(it.timer.id) }
                .map { it.timer.id }
                .let { dbTimers.remove(it) }
        }
    }
}