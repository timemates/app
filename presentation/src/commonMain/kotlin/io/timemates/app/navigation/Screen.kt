package io.timemates.app.navigation

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

sealed class Screen : Parcelable {
    @Parcelize
    object StartAuthorization : Screen()

    @Parcelize
    data class ConfirmAuthorization(val verificationHash: String) : Screen()
}