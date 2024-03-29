package org.timemates.app.localization

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Immutable
interface Strings {
    val appName: String

    val start: String

    val nextStep: String

    val authorization: String

    val authorizationDescription: String

    val confirmation: String

    val confirmationCode: String

    val confirmationDescription: String

    val email: String

    val changeEmail: String

    val emailSizeIsInvalid: String

    val emailIsInvalid: String

    val codeSizeIsInvalid: String

    val codeIsInvalid: String

    val unknownFailure: String

    val confirmationAttemptFailed: String

    val tooManyAttempts: String

    val dismiss: String

    val done: String

    val almostDone: String

    val configureNewAccountDescription: String

    val aboutYou: String

    val yourName: String

    val nameSizeIsInvalid: String

    val nameIsInvalid: String

    val aboutYouSizeIsInvalid: String

    val timerSettings: String

    val description: String

    val name: String

    val workTime: String

    val restTime: String

    val every: String

    val minutes: String

    val advancedRestSettingsDescription: String

    val publicManageTimerStateDescription: String

    val confirmationRequiredDescription: String

    val timerNameSizeIsInvalid: String

    val timerDescriptionSizeIsInvalid: String

    val save: String
    
    val welcome: String

    val welcomeDescription: String

    val letsStart: String

    val timerCreation: String

    val noTimers: String

    val confirmationWaitingTimerDescription: String

    val alreadyExists: String

    val invalidArgument: String

    val notFound: String

    val unauthorized: String

    val unavailable: String

    val unsupported: String

    val fieldCannotBeEmpty: String

    @Stable
    fun minValueFailure(min: Number): String

    @Stable
    fun lengthExactFailure(size: Int): String

    @Stable
    fun lengthRangeFailure(range: IntRange): String

    @Stable
    fun valueRangeFailure(first: Number, last: Number): String

    val patternFailure: String

    @Stable
    fun internalError(message: String): String

    @Stable
    fun inactiveTimerDescription(daysSincePaused: Int): String

    @Stable
    fun runningTimerDescription(people: Int): String
}
