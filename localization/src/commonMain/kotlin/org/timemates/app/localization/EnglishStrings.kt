package org.timemates.app.localization

import io.timemates.sdk.authorization.sessions.types.value.ConfirmationCode
import io.timemates.sdk.timers.types.value.TimerDescription
import io.timemates.sdk.timers.types.value.TimerName
import io.timemates.sdk.users.profile.types.value.UserDescription
import io.timemates.sdk.users.profile.types.value.UserName

object EnglishStrings : Strings {
    override val appName: String = "TimeMates"
    override val start: String = "Start"
    override val nextStep: String = "Next step"
    override val authorization: String = "Authorization"
    override val authorizationDescription: String = """
        Welcome to $appName! Please provide your email address to proceed with the 
        authorization process. We will send a verification code to your email to confirm your
        identity.
    """.trimIndent()
    override val confirmation: String = "Confirmation"
    override val confirmationCode: String = "Confirmation code"
    override val confirmationDescription: String = "We’ve just sent a letter with confirmation code to your email address. We do this to confirm that you’re the owner of this email."
    override val email: String = "Email address"
    override val changeEmail: String = "Change email"
    override val emailSizeIsInvalid: String = "Email address size should be in range of 5 and 200 symbols"
    override val emailIsInvalid: String = "Email address is invalid."
    override val codeSizeIsInvalid: String = "Code size should be ${ConfirmationCode.SIZE} symbols length"
    override val codeIsInvalid: String = "Confirmation code should consist only from [a-Z] and [0-9]"
    override val unknownFailure: String = "Unknown failure happened"
    override val confirmationAttemptFailed: String = "Confirmation code is invalid. Recheck and try again."
    override val tooManyAttempts: String = "Too many attempts."
    override val dismiss: String = "Dismiss"
    override val done: String = "Done"
    override val almostDone: String = "Almost done"
    override val configureNewAccountDescription: String = "Welcome to TimeMates! Let’s start our journey by configuring your profile details."
    override val aboutYou: String = "About you"
    override val yourName: String = "Your name"
    override val nameSizeIsInvalid: String = "Name size should be in range of ${UserName.SIZE_RANGE.first} to ${UserName.SIZE_RANGE.last} symbols."
    override val nameIsInvalid: String = "Name consists from illegal characters."
    override val aboutYouSizeIsInvalid: String = "User description should be in range of ${UserDescription.SIZE_RANGE.first} and ${UserDescription.SIZE_RANGE.last} symbols."
    override val timerSettings: String = "Edit timer"
    override val description: String = "Description"
    override val name: String = "Name"
    override val workTime: String = "Work time (min)"
    override val restTime: String = "Rest time (min)"
    override val every: String = "Every"
    override val minutes: String = "Minutes"
    override val advancedRestSettingsDescription: String = "Enable big rest time (extended rest every X rounds)."
    override val publicManageTimerStateDescription: String = "Everyone can manage timer state"
    override val confirmationRequiredDescription: String = "Always require confirmation before round start"
    override val timerNameSizeIsInvalid: String = "Name size should be in range of ${TimerName.SIZE_RANGE.first} to ${TimerName.SIZE_RANGE.last} symbols."
    override val timerDescriptionSizeIsInvalid: String = "Timer description should be in range of ${TimerDescription.SIZE_RANGE.first} and ${TimerDescription.SIZE_RANGE.last} symbols."
    override val save: String = "Save"
    override val welcome: String = "Welcome to TimeMates"
    override val welcomeDescription: String = "Unlock Your Productivity: Seamlessly Organize Tasks, Collaborate Effortlessly, and Achieve your Goals."
    override val letsStart: String = "Let’s start"
    override val timerCreation: String = "Add timer"
    override val noTimers: String = "You don't have any timers yet."
    override val confirmationWaitingTimerDescription: String = "Waiting for confirmation."
    override val alreadyExists: String = "It already exists or functionality isn't supposed to be used twice."
    override val invalidArgument: String = "Invalid input information."
    override val notFound: String = "Entity is not found."
    override val unauthorized: String = "Your authorized was whether terminated or expired, please relogin."
    override val unavailable: String = "Service is not available at the moment, please try again later."
    override val unsupported: String = "This functionality is not yet supported."

    override fun internalError(message: String): String {
        return "Internal server failure has happened, details: $message"
    }

    override fun inactiveTimerDescription(daysSincePaused: Int): String {
        return if (daysSincePaused == 0) "Last activity was today" else "Last activity was $daysSincePaused days ago"
    }
    override fun runningTimerDescription(people: Int): String {
        return if (people == 0) "You are using this timer." else "You and $people other people are using this timer."
    }
}
