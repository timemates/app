package io.timemates.client.galaxia.image

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import io.kamel.image.KamelImage
import io.kamel.image.lazyPainterResource
import io.ktor.http.*
import androidx.compose.material.Icon as MaterialIcon

/**
 * Sealed interface representing an Image that can be displayed in the app.
 */
@Immutable
sealed interface Image {
    /**
     * A value class representing an image loaded from a URL.
     * @param url The URL of the image.
     */
    @JvmInline
    value class Url(val url: String) : Image

    /**
     * A value class representing a vector image.
     * @param vector The vector image.
     */
    @JvmInline
    value class Icon(val vector: ImageVector) : Image
}

/**
 * Returns an [Image.Icon] object representing a vector image.
 * @param vector The vector image.
 * @return An [Image.Icon] object representing the vector image.
 */
@Stable
fun image(vector: ImageVector): Image.Icon = Image.Icon(vector)

/**
 * Returns an [Image.Url] object representing an image loaded from a URL.
 * @param url The URL of the image.
 * @return An [Image.Url] object representing the image loaded from the URL.
 */
@Stable
fun image(url: String): Image.Url = Image.Url(url)


/**
 * A composable function that displays a Material icon.
 * @param modifier The modifier to be applied to the icon.
 * @param icon The icon to be displayed as an [Image.Icon].
 * @param contentDescription A description of the icon for accessibility purposes.
 */
@Composable
fun AtomIcon(
    modifier: Modifier = Modifier,
    icon: Image.Icon,
    tint: Color,
    contentDescription: String? = null,
) {
    MaterialIcon(
        modifier = modifier,
        imageVector = icon.vector,
        contentDescription = contentDescription,
        tint = tint,
    )
}

@Composable
fun AtomImage(
    modifier: Modifier,
    url: Image.Url,
    contentDescription: String? = null,
) {
    KamelImage(
        modifier = modifier,
        resource = lazyPainterResource(data = Url(url.url)),
        contentDescription = contentDescription,
    )
}