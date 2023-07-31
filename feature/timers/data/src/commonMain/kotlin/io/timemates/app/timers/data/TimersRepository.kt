package io.timemates.app.timers.data

import io.timemates.sdk.common.pagination.PageToken
import io.timemates.sdk.common.pagination.PagesIterator
import io.timemates.sdk.common.types.Empty
import io.timemates.sdk.common.types.value.Count
import io.timemates.sdk.timers.TimersApi
import io.timemates.sdk.timers.getUserTimersPages
import io.timemates.sdk.timers.members.TimerMembersApi
import io.timemates.sdk.timers.members.invites.TimerInvitesApi
import io.timemates.sdk.timers.members.invites.getInvitesPages
import io.timemates.sdk.timers.members.invites.types.Invite
import io.timemates.sdk.timers.members.invites.types.value.InviteCode
import io.timemates.sdk.timers.sessions.TimersSessionsApi
import io.timemates.sdk.timers.types.Timer
import io.timemates.sdk.timers.types.TimerSettings
import io.timemates.sdk.timers.types.value.TimerDescription
import io.timemates.sdk.timers.types.value.TimerId
import io.timemates.sdk.timers.types.value.TimerName
import io.timemates.sdk.users.profile.types.value.UserId
import kotlinx.coroutines.flow.Flow
import io.timemates.app.users.repositories.TimersRepository as TimersRepositoryContract

class TimersRepository(
    private val timersApi: TimersApi,
    private val timerInvitesApi: TimerInvitesApi,
    private val timersSessionsApi: TimersSessionsApi,
    private val timerMembersApi: TimerMembersApi,
) : TimersRepositoryContract {
    override suspend fun getUserTimers(pageToken: PageToken?): PagesIterator<Timer> {
        return timersApi.getUserTimersPages(pageToken)
    }

    override suspend fun getTimer(id: TimerId): Result<Timer> {
        return timersApi.getTimer(id)
    }

    override suspend fun getTimerState(id: TimerId): Result<Flow<Timer.State>> {
        return timersSessionsApi.getTimerState(id)
    }

    override suspend fun getInvites(timerId: TimerId, pageToken: PageToken?): PagesIterator<Invite> {
        return timerInvitesApi.getInvitesPages(timerId, pageToken)
    }

    override suspend fun createInvite(timerId: TimerId, maxUsersToJoin: Count): Result<InviteCode> {
        return timerInvitesApi.create(timerId, maxUsersToJoin)
    }

    override suspend fun createTimer(name: TimerName, description: TimerDescription, settings: TimerSettings): Result<TimerId> {
        return timersApi.createTimer(name, description, settings)
    }

    override suspend fun kickMember(timerId: TimerId, userId: UserId): Result<Empty> {
        return timerMembersApi.kickMember(timerId, userId)
    }

    override suspend fun removeInvite(timerId: TimerId, inviteCode: InviteCode): Result<Empty> {
        return timerInvitesApi.removeInvite(timerId, inviteCode)
    }

    override suspend fun removeTimer(timerId: TimerId): Result<Empty> {
        return timersApi.removeTimer(timerId)
    }

    override suspend fun editTimer(timerId: TimerId, newName: TimerName?, newDescription: TimerDescription?, settings: TimerSettings.Patch?): Result<Empty> {
        return timersApi.editTimer(timerId, newName, newDescription, settings)
    }
}