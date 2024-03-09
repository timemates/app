package org.timemates.app.localization

import org.timemates.sdk.authorization.sessions.types.value.ConfirmationCode
import org.timemates.sdk.timers.types.value.TimerDescription
import org.timemates.sdk.timers.types.value.TimerName
import org.timemates.sdk.users.profile.types.value.UserDescription
import org.timemates.sdk.users.profile.types.value.UserName

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
    override val timerSettings: String = "Редагувати таймер"
    override val description: String = "Описання"
    override val name: String = "Найменування"
    override val workTime: String = "Час роботи (хв)"
    override val restTime: String = "Час відпочинку (хв)"
    override val every: String = "Кожні"
    override val minutes: String = "Хвилин"
    override val advancedRestSettingsDescription: String = "Увімкнути подовжений відпочинок (кожні X раундів)."
    override val publicManageTimerStateDescription: String = "Кожен може керувати станом таймера"
    override val confirmationRequiredDescription: String = "Завжди вимагати підтвердження перед початком раунду"
    override val timerNameSizeIsInvalid: String = "Ім'я має бути в межах ${TimerName.SIZE_RANGE.first} й ${TimerName.SIZE_RANGE.last} символів."
    override val timerDescriptionSizeIsInvalid: String = "Опис має бути в межах ${TimerDescription.SIZE_RANGE.first} й ${TimerDescription.SIZE_RANGE.last} символів."
    override val save: String = "Зберегти"
    override val welcome: String = "Ласкаво просимо до TimeMates"
    override val welcomeDescription: String = "Розкрийте Свою Продуктивність: Легко Організовуйте Завдання, Співпрацюйте Без Зусиль і Досягніть своїх Цілей."
    override val letsStart: String = "Розпочнімо"
    override val timerCreation: String = "Додати таймер"
    override val noTimers: String = "У вас ще немає таймерів."
    override val confirmationWaitingTimerDescription: String = "Очікування підтвердження."
    override val alreadyExists: String = "Сущність вже існує або функціональність не передбачає повторного використання."
    override val invalidArgument: String = "Неправильні дані."
    override val notFound: String = "Не знайдено."
    override val unauthorized: String = "Ваша авторизація була видалена або закінчився строк її дії."
    override val unavailable: String = "Сервіс недоступний, спробуйте будь-ласка пізніше."
    override val unsupported: String = "Дана функціональність ще не підтримується на сервері або клієнті, зв'яжіться з розробником."
    override val fieldCannotBeEmpty: String = "Це поле не може бути пустим."

    override fun minValueFailure(min: Int): String {
        return "Мінімальне значення має відповідати $min."
    }

    override fun sizeExactFailure(size: Int): String {
        return "Довжина тексту має бути мінімум $size."
    }

    override fun sizeRangeFailure(range: IntRange): String {
        return "Довжина тексту має бути в межах від ${range.first} до ${range.last} символів."
    }

    override val patternFailure: String = "Неправильний формат даних."

    override fun internalError(message: String): String {
        return "Внутрішня помилка серверу, деталі: $message."
    }

    override fun inactiveTimerDescription(daysSincePaused: Int): String {
        return if (daysSincePaused == 0) "Остання активність була сьогодні" else "Остання активність була $daysSincePaused дні тому"
    }
    override fun runningTimerDescription(people: Int): String {
        return if (people == 0) "Ви використовуєте цей таймер." else "Ви та ще $people людини використовуєте цей таймер."
    }
}
