package io.timemates.app.core.localization

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
    override val confirmation: String = "Підтвердження"
    override val email: String = "Email address"
    override val emailSizeIsInvalid: String = "Розмір електронної пошти має бути від 5 до 200 символів"
    override val emailIsInvalid: String = "Ваша електронна пошта неправильна."
    override val unknownFailure: String = "Сталась невідома помилка"
    override val tooManyAttempts: String = "Занадто багато спроб"
    override val dismiss: String = "Прибрати"
}