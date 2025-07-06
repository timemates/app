package app.timemate.client.tasks.domain.type.value

import com.y9vad9.ktiny.kotlidator.ValueFactory
import kotlin.jvm.JvmInline
import com.y9vad9.ktiny.kotlidator.factory
import com.y9vad9.ktiny.kotlidator.rule.StringLengthRangeValidationRule

/**
 * Represents a validated task status name within the domain.
 *
 * This value class encapsulates a [String] and ensures it adheres to the defined length constraints.
 * It also provides helper methods to determine whether the name corresponds to a built-in status.
 *
 * Use [factory] to create validated instances of [TaskStatusName].
 *
 * @property string The underlying string value representing the status name.
 */
@JvmInline
value class TaskStatusName private constructor(
    val string: String,
) {
    companion object {
        /** The minimum allowed length of a status name. */
        const val MIN_LENGTH: Int = 1

        /** The maximum allowed length of a status name. */
        const val MAX_LENGTH: Int = 100

        /** The inclusive length range that status names must fall within. */
        val LENGTH_RANGE: IntRange = MIN_LENGTH..MAX_LENGTH

        /** Built-in status name representing a planned task. */
        val PLANNED: TaskStatusName = TaskStatusName("Planed")

        /** Built-in status name representing a task in progress. */
        val IN_PROGRESS: TaskStatusName = TaskStatusName("In Progress")

        /** Built-in status name representing a paused task. */
        val PAUSED: TaskStatusName = TaskStatusName("Paused")

        /** Built-in status name representing a completed task. */
        val DONE: TaskStatusName = TaskStatusName("Done")

        /**
         * A list of all built-in status names.
         *
         * Used internally to detect name collisions with custom status names.
         */
        val BUILTIN_NAMES: List<TaskStatusName> = listOf(
            PLANNED, IN_PROGRESS, PAUSED, DONE,
        )

        /**
         * A [ValueFactory] for safely constructing validated instances of [TaskStatusName].
         *
         * Applies string length validation and enforces immutability.
         */
        val factory: ValueFactory<TaskStatusName, String> = factory(
            rules = listOf(StringLengthRangeValidationRule(LENGTH_RANGE)),
            constructor = { TaskStatusName(it) },
        )
    }

    /**
     * Returns `true` if this status name is one of the built-in names (case-insensitive).
     */
    fun isBuiltin(): Boolean {
        return BUILTIN_NAMES.any { name -> string.equals(name.string, ignoreCase = true) }
    }

    /**
     * Returns `true` if this status name does **not** match any built-in names (case-insensitive).
     */
    fun isNotBuiltin(): Boolean = !isBuiltin()
}
