package com.y9vad9.pomodoro.client.android.di

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalContext
import com.y9vad9.pomodoro.client.db.DatabaseDriverFactory
import com.y9vad9.pomodoro.client.db.Pomodoro
import com.y9vad9.pomodoro.client.repositories.integration.DbLocalTimersRepository
import com.y9vad9.pomodoro.client.usecases.timers.local.GetLocalTimersUseCase


val GetLocalTimersUseCase = compositionLocalOf {
    GetLocalTimersUseCase(DbLocalTimersRepository(
        Pomodoro.invoke(
            DatabaseDriverFactory(LocalContext.current).createDriver()
        ).localTimersQueries
    ))
}

