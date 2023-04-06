package io.timemates.client.galaxia.buttons

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import io.timemates.client.galaxia.context.GalaxiaContext
import io.timemates.client.galaxia.context.GalaxiaTheme
import io.timemates.client.galaxia.text.AtomText
import io.timemates.client.galaxia.text.Text
import io.timemates.client.galaxia.text.text

@Composable
fun Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: Text,
    primary: Boolean,
    enabled: Boolean,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(GalaxiaContext.sizes.xxs))
            .background(if(primary) GalaxiaContext.colors.bgAccent else GalaxiaContext.colors.bgHighest)
            .padding(vertical = GalaxiaContext.sizes.xs, horizontal = GalaxiaContext.sizes.s)
            .clickable(enabled, onClick = onClick)
    ) {
        AtomText(
            modifier = Modifier.align(Alignment.Center),
            text = text,
            textStyle = GalaxiaContext.typography.button,
            textColor = if(primary) GalaxiaContext.colors.figureInverted else GalaxiaContext.colors.figureBase,
        )
    }
}

@Preview
@Composable
private fun ButtonPreview() {
    Column(
        modifier = Modifier.fillMaxSize().padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        GalaxiaTheme(darkTheme = false) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {},
                text = text("Apply"),
                primary = true,
                enabled = true,
            )

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {},
                text = text("Cancel"),
                primary = false,
                enabled = true,
            )
        }

        GalaxiaTheme(darkTheme = true) {
            Column(
                modifier = Modifier
                .fillMaxSize()
                .background(GalaxiaContext.colors.bgBase)
                .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {},
                    text = text("Apply"),
                    primary = true,
                    enabled = true,
                )

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {},
                    text = text("Cancel"),
                    primary = false,
                    enabled = true,
                )
            }
        }
    }
}