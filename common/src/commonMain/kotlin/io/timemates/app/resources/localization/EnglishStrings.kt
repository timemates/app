package io.timemates.app.resources.localization

object EnglishStrings : Strings {
    override val email: String = "Email"
    override val profile: String = "Profile"
    override val timers: String = "Timers"
    override val settings: String = "Settings"
    override val createNew: String = "Create new"
    override val joinExisting: String = "Join existing"
    override val restTime: String = "Rest time"
    override val workingTime: String = "Working time"
    override val bigRest: String = "Big rest"
    override val startConfirmation: String = "Start confirmation"
    override val members: String = "Members"
    override val restTimeDesc: String = "Specify amount of time for rest."
    override val workingTimeDesc: String = "Specify amount of time for your work."
    override val bigRestDesc: String = "Specify amount of time, regularity of big rest."
    override val startConfirmationDesc: String = "Check readiness of members to start."
    override val membersDesc: String = "Manage members of the timer"

    override fun minutes(value: Int): String {
        return when(value) {
            0 -> "Less than minute"
            1 -> "1 minute"
            else -> "$value minutes"
        }
    }
}