package io.timemates.app.navigation

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

sealed class Screen : Parcelable {
    @Parcelize
    object InitialAuthorizationScreen : Screen()

    @Parcelize
    object StartAuthorization : Screen()

    @Parcelize
    data class AfterStart(val verificationHash: String) : Screen()

    @Parcelize
    data class ConfirmAuthorization(val verificationHash: String) : Screen()

    @Parcelize
    data class NewAccountInfo(val verificationHash: String) : Screen()

    @Parcelize
    data class NewAccount(val verificationHash: String) : Screen()

    @Parcelize
    data class TimersList(val verificationHash: String) : Screen()

    @Parcelize
    data class TimerCreation(val verificationHash: String) : Screen()

    @Parcelize
    data class TimerSettings(val verificationHash: String) : Screen()
}