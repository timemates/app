package com.y9vad9.pomodoro.client.repositories.integration

import com.y9vad9.pomodoro.client.db.LocalTimersQueries
import com.y9vad9.pomodoro.client.repositories.LocalTimersRepository
import com.y9vad9.pomodoro.client.types.LocalTimer
import com.y9vad9.pomodoro.client.types.value.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DbLocalTimersRepository(
    private val localTimersQueries: LocalTimersQueries
) : LocalTimersRepository {
    override suspend fun createTimer(name: TimerName, settings: LocalTimer.Settings, creationTime: DateTime) {
        withContext(Dispatchers.IO) {
            localTimersQueries.insert(
                name.string,
                creationTime.long,
                creationTime.long,
                settings.workTime.long,
                settings.restTime.long,
                settings.bigRestPer.int,
                settings.isBigRestEnabled,
                settings.isStartConfirmationRequired
            )
        }
    }

    override suspend fun getTimers(): List<LocalTimer> {
        return withContext(Dispatchers.IO) {
            localTimersQueries.selectAll(Long.MAX_VALUE, 0)
                .executeAsList()
                .map { it.external() }
        }
    }

    override suspend fun setLastTimeUsage(timerId: TimerId, dateTime: DateTime) {
        withContext(Dispatchers.IO) {
            localTimersQueries.updateLastUsedTime(dateTime.long, timerId.int)
        }
    }

    override suspend fun setTimerSettings(timerId: TimerId, patch: LocalTimer.Settings.Patch): Unit = with(patch) {
        withContext(Dispatchers.IO) {
            workTime?.let { localTimersQueries.setWorkTime(it.long, timerId.int) }
            restTime?.let { localTimersQueries.setRestTime(it.long, timerId.int) }
            bigRestTime?.let { localTimersQueries.setBigRestTime(it.long, timerId.int) }
            bigRestPer?.let { localTimersQueries.setBigRestPer(it.int, timerId.int) }
            isBigRestEnabled?.let { localTimersQueries.setBigRestEnabled(it, timerId.int) }
            isStartConfirmationRequired?.let { localTimersQueries.setConfirmationEnabled(it, timerId.int) }
        }
    }

    override suspend fun removeTimer(timerId: TimerId) {
        withContext(Dispatchers.IO) {
            localTimersQueries.delete(timerId.int)
        }
    }

    private fun com.y9vad9.pomodoro.client.db.LocalTimer.external(): LocalTimer {
        return LocalTimer(
            TimerId(id),
            TimerName(name),
            DateTime(lastUsedTime),
            LocalTimer.Settings(
                Milliseconds(workTime),
                Milliseconds(restTime),
                Milliseconds(bigRestTime),
                isBigRestEnabled,
                Regularity(bigRestPer),
                isConfirmationRequired
            )
        )
    }

}