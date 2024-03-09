package org.timemates.app.users.repositories

import org.timemates.sdk.common.pagination.PageToken
import org.timemates.sdk.common.pagination.PagesIterator
import org.timemates.sdk.common.types.Empty
import org.timemates.sdk.common.types.value.Count
import org.timemates.sdk.timers.members.invites.types.Invite
import org.timemates.sdk.timers.members.invites.types.value.InviteCode
import org.timemates.sdk.timers.types.Timer
import org.timemates.sdk.timers.types.TimerSettings
import org.timemates.sdk.timers.types.value.TimerDescription
import org.timemates.sdk.timers.types.value.TimerId
import org.timemates.sdk.timers.types.value.TimerName
import org.timemates.sdk.users.profile.types.value.UserId
import kotlinx.coroutines.flow.Flow

interface TimersRepository {
    suspend fun getUserTimers(pageToken: PageToken? = null): PagesIterator<Timer>

    suspend fun getTimer(id: TimerId): Result<Timer>

    suspend fun getTimerState(id: TimerId): Result<Flow<Timer.State>>

    suspend fun getInvites(timerId: TimerId, pageToken: PageToken? = null): PagesIterator<Invite>

    suspend fun createInvite(timerId: TimerId, maxUsersToJoin: Count): Result<InviteCode>

    suspend fun createTimer(
        name: TimerName,
        description: TimerDescription,
        settings: TimerSettings,
    ): Result<TimerId>

    suspend fun kickMember(timerId: TimerId, userId: UserId): Result<Empty>

    suspend fun removeInvite(timerId: TimerId, inviteCode: InviteCode): Result<Empty>

    suspend fun removeTimer(timerId: TimerId): Result<Empty>

    suspend fun editTimer(
        timerId: TimerId,
        newName: TimerName? = null,
        newDescription: TimerDescription? = null,
        settings: TimerSettings.Patch? = null,
    ): Result<Empty>
}