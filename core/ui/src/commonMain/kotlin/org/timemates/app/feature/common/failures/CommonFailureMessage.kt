package org.timemates.app.feature.common.failures

import androidx.compose.runtime.Stable
import org.timemates.app.localization.Strings
import org.timemates.sdk.common.exceptions.AlreadyExistsException
import org.timemates.sdk.common.exceptions.InvalidArgumentException
import org.timemates.sdk.common.exceptions.NotFoundException
import org.timemates.sdk.common.exceptions.TimeMatesException
import org.timemates.sdk.common.exceptions.TooManyRequestsException
import org.timemates.sdk.common.exceptions.UnauthorizedException
import org.timemates.sdk.common.exceptions.UnavailableException
import org.timemates.sdk.common.exceptions.UnsupportedException

/**
 * Parses given [Throwable] and converts it into translated message
 * to display for users.
 *
 * Should be used only when documented failures are already handled and you need
 * to display the least informative message to user.
 */
@Stable
fun Throwable.getDefaultDisplayMessage(strings: Strings): String {
    if (this !is TimeMatesException)
        return strings.unknownFailure

    return when (this) {
        is AlreadyExistsException -> strings.alreadyExists
        is InvalidArgumentException -> strings.invalidArgument
        is NotFoundException -> strings.notFound
        is TooManyRequestsException -> strings.tooManyAttempts
        is UnauthorizedException -> strings.unauthorized
        is UnavailableException -> strings.unavailable
        is UnsupportedException -> strings.unsupported
        else -> strings.internalError(message)
    }
}