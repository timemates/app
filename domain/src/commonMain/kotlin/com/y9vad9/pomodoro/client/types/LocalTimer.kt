package com.y9vad9.pomodoro.client.types

import com.y9vad9.pomodoro.client.types.value.*

data class LocalTimer(
    val id: TimerId,
    val name: TimerName,
    val lastTimeUsed: DateTime,
    val settings: Settings
) {
    class Settings(
        val workTime: Milliseconds = Milliseconds(1500000L),
        val restTime: Milliseconds = Milliseconds(300000),
        val bigRestTime: Milliseconds = Milliseconds(600000),
        val isBigRestEnabled: Boolean = true,
        val bigRestPer: Regularity = Regularity(4),
        val isStartConfirmationRequired: Boolean = false
    ) {
        class Patch(
            val workTime: Milliseconds? = null,
            val restTime: Milliseconds? = null,
            val bigRestTime: Milliseconds? = null,
            val isBigRestEnabled: Boolean? = null,
            val bigRestPer: Regularity? = null,
            val isStartConfirmationRequired: Boolean? = null
        )
    }
}