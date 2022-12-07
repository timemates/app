package com.y9vad9.pomodoro.client.repositories.integration

import com.y9vad9.pomodoro.client.repositories.LocalSessionsRepository
import com.y9vad9.pomodoro.client.types.value.TimerId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emptyFlow

class InMemoryLocalSessionsRepository : LocalSessionsRepository {
    private val sessions = HashMap<TimerId, Session>()

    override suspend fun startSession(timerId: TimerId) {
        sessions[timerId] = Session()
    }

    override suspend fun updatesOf(timerId: TimerId): Flow<LocalSessionsRepository.TimerUpdate> {
        return sessions[timerId]?.updates ?: emptyFlow()
    }

    override suspend fun sendUpdate(timerId: TimerId, update: LocalSessionsRepository.TimerUpdate) {
        sessions[timerId]?.updates?.emit(update)
    }

    override suspend fun stopSession(timerId: TimerId) {
        sessions[timerId]?.updates?.emit(LocalSessionsRepository.TimerUpdate.TimerFinished)
        sessions.remove(timerId)
    }

    override suspend fun createConfirmation(timerId: TimerId) {
        sessions[timerId]?.isConfirmationState = true
    }

    override suspend fun isConfirmationAvailable(timerId: TimerId): Boolean {
        return sessions[timerId]?.isConfirmationState ?: false
    }

    override suspend fun confirm(timerId: TimerId) {
        TODO("Not yet implemented")
    }

    private inner class Session(
        val updates: MutableSharedFlow<LocalSessionsRepository.TimerUpdate> =
            MutableSharedFlow()
    ) {
        var isConfirmationState: Boolean = false
    }
}