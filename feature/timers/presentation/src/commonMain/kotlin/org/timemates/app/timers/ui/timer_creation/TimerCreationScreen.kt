package org.timemates.app.timers.ui.timer_creation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.channels.consumeEach
import org.timemates.app.feature.common.failures.getDefaultDisplayMessage
import org.timemates.app.foundation.mvi.MVI
import org.timemates.app.localization.compose.LocalStrings
import org.timemates.app.style.system.appbar.AppBar
import org.timemates.app.style.system.button.ButtonWithProgress
import org.timemates.app.style.system.text_field.SizedOutlinedTextField
import org.timemates.app.style.system.theme.AppTheme
import org.timemates.app.timers.ui.timer_creation.mvi.TimerCreationScreenComponent.Effect
import org.timemates.app.timers.ui.timer_creation.mvi.TimerCreationScreenComponent.Event
import org.timemates.app.timers.ui.timer_creation.mvi.TimerCreationScreenComponent.State
import org.timemates.sdk.timers.types.value.TimerDescription
import org.timemates.sdk.timers.types.value.TimerName
import kotlin.time.Duration.Companion.minutes
import kotlin.time.DurationUnit


@Composable
fun TimerCreationScreen(
    mvi: MVI<State, Event, Effect>,
    navigateToTimersScreen: () -> Unit,
) {
    val state by mvi.state.collectAsState()
    val snackbarData = remember { SnackbarHostState() }

    val nameSize = remember(state.name) { state.name.length }
    val descriptionSize = remember(state.description) { state.description.length }

    val strings = LocalStrings.current

    LaunchedEffect(Unit) {
        mvi.effects.consumeEach { effect ->
            when (effect) {
                is Effect.Failure ->
                    snackbarData.showSnackbar(
                        message = effect.throwable.getDefaultDisplayMessage(strings)
                    )

                Effect.NavigateToTimersScreen ->
                    navigateToTimersScreen()
            }
        }
    }

    Scaffold(
        topBar = {
            AppBar(
                title = LocalStrings.current.timerCreation,
                navigationIcon = {
                    IconButton(
                        onClick = { navigateToTimersScreen() },
                    ) {
                        Icon(Icons.Rounded.ArrowBack, contentDescription = null)
                    }
                },
            )
        }
    ) { rootPaddings ->

        val nameSupportText = when {
            state.isNameSizeInvalid -> LocalStrings.current.timerNameSizeIsInvalid
            else -> null
        }
        val descriptionYouSupportText = when {
            state.isDescriptionSizeInvalid -> LocalStrings.current.timerDescriptionSizeIsInvalid
            else -> null
        }

        Column(
            modifier = Modifier.fillMaxSize()
                .padding(rootPaddings)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Spacer(Modifier.height(4.dp))

            SizedOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.name,
                onValueChange = { mvi.dispatchEvent(Event.NameIsChanged(it)) },
                label = { Text(LocalStrings.current.name) },
                isError = state.isNameSizeInvalid || nameSize > TimerName.SIZE_RANGE.last,
                singleLine = true,
                supportingText = {
                    if (nameSupportText != null) {
                        Text(nameSupportText)
                    }
                },
                size = IntRange(nameSize, TimerName.SIZE_RANGE.last),
                enabled = !state.isLoading,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
            )

            SizedOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.description,
                onValueChange = { mvi.dispatchEvent(Event.DescriptionIsChanged(it)) },
                label = { Text(LocalStrings.current.description) },
                isError = state.isDescriptionSizeInvalid || descriptionSize > TimerDescription.SIZE_RANGE.last,
                maxLines = 5,
                supportingText = {
                    if (descriptionYouSupportText != null) {
                        Text(descriptionYouSupportText)
                    }
                },
                size = IntRange(descriptionSize, TimerDescription.SIZE_RANGE.last),
                enabled = !state.isLoading,
            )

            HorizontalDivider(
                color = AppTheme.colors.secondary,
                thickness = 1.dp,
                modifier = Modifier
                    .padding(8.dp)
                    .width(60.dp)
                    .align(Alignment.CenterHorizontally),
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .weight(1f),
                    value = state.workTime.toInt(unit = DurationUnit.MINUTES).toString(),
                    onValueChange = {
                        mvi.dispatchEvent(
                            Event.WorkTimeIsChanged(it.toIntOrNull()?.minutes ?: state.workTime)
                        )
                    },
                    label = { Text(LocalStrings.current.workTime) },
                    singleLine = true,
                    enabled = !state.isLoading,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                )

                OutlinedTextField(
                    modifier = Modifier
                        .weight(1f),
                    value = state.restTime.toInt(unit = DurationUnit.MINUTES).toString(),
                    onValueChange = { mvi.dispatchEvent(Event.RestTimeIsChanged(it.toInt().minutes)) },
                    label = { Text(LocalStrings.current.restTime) },
                    singleLine = true,
                    enabled = !state.isLoading,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Checkbox(
                    checked = state.bigRestEnabled,
                    onCheckedChange = { mvi.dispatchEvent(Event.BigRestModeIsChanged(!state.bigRestEnabled)) },
                    modifier = Modifier.align(Alignment.CenterVertically),
                    colors = CheckboxDefaults.colors(checkedColor = AppTheme.colors.primary),
                    enabled = !state.isLoading,
                )

                Text(
                    text = LocalStrings.current.advancedRestSettingsDescription,
                    modifier = Modifier.align(Alignment.CenterVertically),
                    color = if(!state.isLoading) AppTheme.colors.primary else AppTheme.colors.secondary,
                )
            }

            if (state.bigRestEnabled) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    OutlinedTextField(
                        modifier = Modifier.weight(1f),
                        value = state.bigRestPer.toString(),
                        onValueChange = { mvi.dispatchEvent(Event.BigRestPerIsChanged(it.toInt())) },
                        label = { Text(LocalStrings.current.every) },
                        singleLine = true,
                        enabled = !state.isLoading,
                    )

                    OutlinedTextField(
                        modifier = Modifier.weight(1f),
                        value = state.bigRestTime.toInt(unit = DurationUnit.MINUTES).toString(),
                        onValueChange = { mvi.dispatchEvent(Event.BigRestTimeIsChanged(it.toInt().minutes)) },
                        label = { Text(LocalStrings.current.minutes) },
                        singleLine = true,
                        enabled = !state.isLoading,
                    )
                }
            }

            Spacer(modifier = Modifier.padding(8.dp))

            HorizontalDivider(
                color = AppTheme.colors.secondary,
                thickness = 1.dp,
                modifier = Modifier
                    .width(60.dp)
                    .align(Alignment.CenterHorizontally),
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Checkbox(
                    checked = state.isEveryoneCanPause,
                    onCheckedChange = { mvi.dispatchEvent(Event.TimerPauseControlAccessIsChanged(!state.isEveryoneCanPause)) },
                    modifier = Modifier.align(Alignment.CenterVertically),
                    colors = CheckboxDefaults.colors(checkedColor = AppTheme.colors.primary),
                    enabled = !state.isLoading,
                )

                Text(
                    text = LocalStrings.current.publicManageTimerStateDescription,
                    modifier = Modifier.align(Alignment.CenterVertically),
                    color = if(!state.isLoading) AppTheme.colors.primary else AppTheme.colors.secondary,
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Checkbox(
                    checked = state.isConfirmationRequired,
                    onCheckedChange = { mvi.dispatchEvent(Event.ConfirmationRequirementChanged(!state.isConfirmationRequired)) },
                    modifier = Modifier.align(Alignment.CenterVertically),
                    colors = CheckboxDefaults.colors(checkedColor = AppTheme.colors.primary),
                    enabled = !state.isLoading,
                )

                Text(
                    text = LocalStrings.current.confirmationRequiredDescription,
                    modifier = Modifier.align(Alignment.CenterVertically),
                    color = if(!state.isLoading) AppTheme.colors.primary else AppTheme.colors.secondary,
                )
            }

            Spacer(Modifier.weight(1f))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                SnackbarHost(
                    hostState = snackbarData
                ) {
                    Snackbar(it)
                }

                ButtonWithProgress(
                    primary = true,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { mvi.dispatchEvent(Event.OnDoneClicked) },
                    enabled = !state.isLoading,
                    isLoading = state.isLoading
                ) {
                    Text(LocalStrings.current.save)
                }
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}