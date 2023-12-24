package io.timemates.app.style.system.appbar

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import io.timemates.app.style.system.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: (@Composable () -> Unit)? = null,
    action: (@Composable () -> Unit)? = null,
) {
    CompositionLocalProvider(LocalContentColor provides AppTheme.colors.primary) {
        CenterAlignedTopAppBar(
            title = {
                AppBarText(
                    title = title,
                )
            },
            navigationIcon = { navigationIcon?.invoke() },
            actions = { action?.invoke() },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = AppTheme.colors.background,
                navigationIconContentColor = AppTheme.colors.primary,
                actionIconContentColor = AppTheme.colors.primary,
            ),
        )
    }
}

@Composable
internal fun AppBarText(
    modifier: Modifier = Modifier,
    title: String,
) {
    Text(
        text = title,
        modifier = modifier,
        fontSize = 24.sp,
        fontWeight = FontWeight.ExtraBold,
        color = AppTheme.colors.primary,
    )
}