package io.timemates.app.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {

    @Serializable
    data object Startup : Screen()

    @Serializable
    data object InitialAuthorizationScreen : Screen()

    @Serializable
    data object StartAuthorization : Screen()

    @Serializable
    data class AfterStart(val verificationHash: String) : Screen()

    @Serializable
    data class ConfirmAuthorization(val verificationHash: String) : Screen()

    @Serializable
    data class NewAccountInfo(val verificationHash: String) : Screen()

    @Serializable
    data class NewAccount(val verificationHash: String) : Screen()

    @Serializable
    data object TimersList : Screen()

    @Serializable
    data object TimerCreation : Screen()

    @Serializable
    data object TimerSettings : Screen()
}