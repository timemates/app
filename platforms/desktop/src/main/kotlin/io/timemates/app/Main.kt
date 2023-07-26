package io.timemates.app

import kotlinx.coroutines.channels.Channel

fun main() {
    // used for navigation to auth when authorization expired / deactivated
    val authorizationFailedChannel: Channel<Unit> = Channel()

    initializeDependencies(authorizationFailedChannel)
    startUi(authorizationFailedChannel)
}