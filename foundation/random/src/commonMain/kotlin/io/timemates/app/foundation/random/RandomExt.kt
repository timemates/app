package io.timemates.app.foundation.random

import kotlin.random.Random

private val alphabet: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

/**
 * Generates a random string of the specified size using the characters from the alphabet.
 *
 * @param size The desired size of the random string.
 * @return A random string of the specified size.
 */
fun Random.nextString(size: Int): String {
    return List(size) { alphabet.random(this) }.joinToString("")
}
