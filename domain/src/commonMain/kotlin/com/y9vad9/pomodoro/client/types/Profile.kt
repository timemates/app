package com.y9vad9.pomodoro.client.types

import com.y9vad9.pomodoro.client.types.value.AvatarUrl
import com.y9vad9.pomodoro.client.types.value.Name
import com.y9vad9.pomodoro.client.types.value.UserId

data class Profile(
    val userId: UserId,
    val userName: Name,
    val avatarUrl: AvatarUrl
)