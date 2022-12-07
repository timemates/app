package com.y9vad9.pomodoro.client.types.value


@JvmInline
value class Regularity(val int: Int) {
    init {
        require(int > 0) {
            "Regularity should always be positive, but got $int."
        }
    }
}