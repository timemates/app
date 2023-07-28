package io.timemates.app.localization

import io.timemates.sdk.authorization.sessions.types.value.ConfirmationCode
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
}