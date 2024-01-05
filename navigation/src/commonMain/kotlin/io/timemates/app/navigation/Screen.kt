package io.timemates.app.navigation

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

sealed class Screen : Parcelable {

    @Parcelize
    data object Startup : Screen() {
        private fun readResolve(): Any = Startup
    }

    @Parcelize
    data object InitialAuthorizationScreen : Screen() {
        private fun readResolve(): Any = InitialAuthorizationScreen
    }

    @Parcelize
    data object StartAuthorization : Screen() {
        private fun readResolve(): Any = StartAuthorization
    }

    @Parcelize
    data class AfterStart(val verificationHash: String) : Screen()

    @Parcelize
    data class ConfirmAuthorization(val verificationHash: String) : Screen()

    @Parcelize
    data class NewAccountInfo(val verificationHash: String) : Screen()

    @Parcelize
    data class NewAccount(val verificationHash: String) : Screen()

    @Parcelize
    data object TimersList : Screen() {
        private fun readResolve(): Any = TimersList
    }

    @Parcelize
    data object TimerCreation : Screen() {
        private fun readResolve(): Any = TimerCreation
    }

    @Parcelize
    data object TimerSettings : Screen() {
        private fun readResolve(): Any = TimerSettings
    }
}