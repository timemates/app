package io.timemates.app.localization

import io.timemates.sdk.authorization.sessions.types.value.ConfirmationCode
import io.timemates.sdk.users.profile.types.value.UserDescription
import io.timemates.sdk.users.profile.types.value.UserName

object UkrainianStrings : Strings {
    override val appName: String = "TimeMates"
    override val start: String = "Почати"
    override val nextStep: String = "Наступний крок"
    override val authorization: String = "Авторизація"
    override val authorizationDescription: String = """
        Вітаємо в $appName! Будь ласка, вкажіть свою електронну адресу, щоб продовжити 
        процес авторизації. Ми надішлемо код підтвердження на вашу електронну адресу,
         щоб підтвердити вашу особу.
        """.trimIndent()
    override val confirmationDescription: String = "Ми щойно надіслали лист із кодом підтвердження на вашу електронну адресу. Ми робимо це, щоб підтвердити, що ви є власником цієї електронної адреси."
    override val confirmation: String = "Підтвердження"
    override val confirmationCode: String = "Код підтвердження"
    override val email: String = "Email address"
    override val changeEmail: String = "Змінити пошту"
    override val emailSizeIsInvalid: String = "Розмір електронної пошти має бути від 5 до 200 символів"
    override val emailIsInvalid: String = "Ваша електронна пошта неправильна."
    override val codeSizeIsInvalid: String = "Розмір має бути ${ConfirmationCode.SIZE} символів."
    override val codeIsInvalid: String = "Код має містити лише [a-Z] символи та цифри."
    override val unknownFailure: String = "Сталась невідома помилка"
    override val confirmationAttemptFailed: String = "Код неправильний, перевірьте будь-ласка код та спробуйте знову."
    override val tooManyAttempts: String = "Занадто багато спроб"
    override val dismiss: String = "Прибрати"
    override val done: String = "Готово"
    override val almostDone: String = "Майже готово"
    override val configureNewAccountDescription: String = "Ласкаво просимо до TimeMates! Давайте почнемо нашу подорож із налаштування даних вашого профілю."
    override val aboutYou: String = "Про тебе"
    override val yourName: String = "Твоє ім'я"
    override val nameSizeIsInvalid: String = "Ім'я має бути в межах ${UserName.SIZE_RANGE.first} й ${UserName.SIZE_RANGE.last} символів."
    override val nameIsInvalid: String = "Ім'я містить заборонені символи."
    override val aboutYouSizeIsInvalid: String = "Опис має бути в межах ${UserDescription.SIZE_RANGE.first} й ${UserDescription.SIZE_RANGE.last} символів."
    override val welcome: String = "Ласкаво просимо до TimeMates"
    override val welcomeDescription: String = "Розкрийте Свою Продуктивність: Легко Організовуйте Завдання, Співпрацюйте Без Зусиль і Досягніть своїх Цілей."
    override val letsStart: String = "Давайте розпочнемо"
}