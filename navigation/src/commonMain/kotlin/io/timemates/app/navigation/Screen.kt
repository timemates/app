package io.timemates.app.navigation

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {

    @Serializable
    data object Startup : Screen() {
        private fun readResolve(): Any = Startup
    }

    @Serializable
    data object InitialAuthorizationScreen : Screen() {
        private fun readResolve(): Any = InitialAuthorizationScreen
    }

    @Serializable
    data object StartAuthorization : Screen() {
        private fun readResolve(): Any = StartAuthorization
    }

    @Serializable
    data class AfterStart(val verificationHash: String) : Screen()

    @Serializable
    data class ConfirmAuthorization(val verificationHash: String) : Screen()

    @Serializable
    data class NewAccountInfo(val verificationHash: String) : Screen()

    @Serializable
    data class NewAccount(val verificationHash: String) : Screen()

    @Serializable
    data object TimersList : Screen() {
        private fun readResolve(): Any = TimersList
    }

    @Serializable
    data object TimerCreation : Screen() {
        private fun readResolve(): Any = TimerCreation
    }

    @Serializable
    data object TimerSettings : Screen() {
        private fun readResolve(): Any = TimerSettings
    }
}