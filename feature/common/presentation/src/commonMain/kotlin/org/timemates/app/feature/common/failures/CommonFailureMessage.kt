package org.timemates.app.feature.common.failures

import org.timemates.app.localization.Strings
import io.timemates.sdk.common.exceptions.AlreadyExistsException
import io.timemates.sdk.common.exceptions.InvalidArgumentException
import io.timemates.sdk.common.exceptions.NotFoundException
import io.timemates.sdk.common.exceptions.TimeMatesException
import io.timemates.sdk.common.exceptions.TooManyRequestsException
import io.timemates.sdk.common.exceptions.UnauthorizedException
import io.timemates.sdk.common.exceptions.UnavailableException
import io.timemates.sdk.common.exceptions.UnsupportedException

/**
 * Parses given [Throwable] and converts it into translated message
 * to display for users.
 *
 * Should be used only when documented failures are already handled and you need
 * to display the least informative message to user.
 */
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