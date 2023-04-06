package io.timemates.app.resources.localization

object UkrainianStrings : Strings {
    override val email: String = "Електронна пошта"
    override val profile: String = "Профіль"
    override val timers: String = "Таймери"
    override val settings: String = "Налаштування"
    override val createNew: String = "Створити новий"
    override val joinExisting: String = "Приєднатися до існуючого"
    override val restTime: String = "Час відпочинку"
    override val workingTime: String = "Час роботи"
    override val bigRest: String = "Великий відпочинок"
    override val startConfirmation: String = "Підтвердження початку"
    override val members: String = "Учасники"
    override val restTimeDesc: String = "Вкажіть тривалість часу для відпочинку."
    override val workingTimeDesc: String = "Вкажіть тривалість часу для роботи."
    override val bigRestDesc: String = "Вкажіть тривалість часу, регулярність великого відпочинку."
    override val startConfirmationDesc: String = "Перевірте готовність учасників до початку."
    override val membersDesc: String = "Керуйте учасниками таймера"

    override fun minutes(value: Int): String {
        return when(value) {
            0 -> "Менше хвилини"
            1 -> "1 хвилина"
            in 2..4 -> "$value хвилини"
            else -> "$value хвилин"
        }
    }

}