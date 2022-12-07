package com.y9vad9.pomodoro.client.types.value

@JvmInline
value class Name(val string: String) {
    init {
        require(string.length <= 50) {
            "Name length should not more than 50, but got ${string.length}"
        }
    }
}