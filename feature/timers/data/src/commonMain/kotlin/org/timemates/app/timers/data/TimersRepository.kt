package org.timemates.app.timers.data

import org.timemates.sdk.common.pagination.PageToken
import org.timemates.sdk.common.pagination.PagesIterator
import org.timemates.sdk.common.types.Empty
import org.timemates.sdk.common.types.value.Count
import org.timemates.sdk.timers.TimersApi
import org.timemates.sdk.timers.getUserTimersPages
import org.timemates.sdk.timers.members.TimerMembersApi
import org.timemates.sdk.timers.members.invites.TimerInvitesApi
import org.timemates.sdk.timers.members.invites.getInvitesPages
import org.timemates.sdk.timers.members.invites.types.Invite
import org.timemates.sdk.timers.members.invites.types.value.InviteCode
import org.timemates.sdk.timers.sessions.TimersSessionsApi
import org.timemates.sdk.timers.types.Timer
import org.timemates.sdk.timers.types.TimerSettings
import org.timemates.sdk.timers.types.value.TimerDescription
import org.timemates.sdk.timers.types.value.TimerId
import org.timemates.sdk.timers.types.value.TimerName
import org.timemates.sdk.users.profile.types.value.UserId
import kotlinx.coroutines.flow.Flow
import org.timemates.app.users.repositories.TimersRepository as TimersRepositoryContract

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