package org.timemates.app.timers.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.timemates.app.feature.common.MVI
import org.timemates.app.feature.common.failures.getDefaultDisplayMessage
import org.timemates.app.feature.common.getFailuresIfPresent
import org.timemates.app.feature.common.isInvalid
import org.timemates.app.localization.compose.LocalStrings
import org.timemates.app.style.system.appbar.AppBar
import org.timemates.app.style.system.button.ButtonWithProgress
import org.timemates.app.style.system.text_field.SizedOutlinedTextField
import org.timemates.app.style.system.theme.AppTheme
import org.timemates.app.timers.ui.settings.mvi.TimerSettingsScreenComponent.*
import org.timemates.sdk.common.constructor.createOrThrow
import org.timemates.sdk.common.types.value.Count
import org.timemates.sdk.timers.types.value.TimerDescription
import org.timemates.sdk.timers.types.value.TimerName
import pro.respawn.flowmvi.essenty.compose.subscribe
import kotlin.time.Duration.Companion.minutes
import kotlin.time.DurationUnit

@Composable
fun TimerSettingsScreen(
    mvi: MVI<State, Intent, Action>,
    navigateToTimersScreen: () -> Unit,
) {
    val snackbarData = remember { SnackbarHostState() }
    val strings = LocalStrings.current

    val state by mvi.subscribe { action ->
        when (action) {
            is Action.ShowFailure ->
                snackbarData.showSnackbar(message = action.throwable.getDefaultDisplayMessage(strings))

            Action.NavigateToTimersScreen -> navigateToTimersScreen()
        }
    }

    val nameSize = remember(state.name) { state.name.value.length }
    val descriptionSize = remember(state.description) { state.description.value.length }


    Scaffold(
        topBar = {
            AppBar(
                title = LocalStrings.current.timerSettings,
                navigationIcon = {
                    IconButton(
                        onClick = { navigateToTimersScreen() },
                    ) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = null)
                    }
                },
            )
        }
    ) { rootPaddings ->
        val nameSupportText = state.name.getFailuresIfPresent(strings)
        val descriptionYouSupportText = state.description.getFailuresIfPresent(strings)

        Column(
            modifier = Modifier.fillMaxSize()
                .padding(rootPaddings)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            SizedOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.name.value,
                onValueChange = { mvi.store.intent(Intent.NameIsChanged(it)) },
                label = { Text(LocalStrings.current.name) },
                isError = state.name.isInvalid() || nameSize > TimerName.LENGTH_RANGE.last,
                singleLine = true,
                supportingText = {
                    if (nameSupportText != null) {
                        Text(nameSupportText)
                    }
                },
                size = IntRange(nameSize, TimerName.LENGTH_RANGE.last),
                enabled = !state.isLoading,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
            )

            SizedOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.description.value,
                onValueChange = { mvi.store.intent(Intent.DescriptionIsChanged(it)) },
                label = { Text(LocalStrings.current.description) },
                isError = state.description.isInvalid() || descriptionSize > TimerDescription.LENGTH_RANGE.last,
                maxLines = 5,
                supportingText = {
                    if (descriptionYouSupportText != null) {
                        Text(descriptionYouSupportText)
                    }
                },
                size = IntRange(descriptionSize, TimerDescription.LENGTH_RANGE.last),
                enabled = !state.isLoading,
            )

            Divider(
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
                    onValueChange = { mvi.store.intent(Intent.WorkTimeIsChanged(it.toInt().minutes)) },
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
                    onValueChange = { mvi.store.intent(Intent.RestTimeIsChanged(it.toInt().minutes)) },
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
                    onCheckedChange = { mvi.store.intent(Intent.BigRestModeIsChanged(!state.bigRestEnabled)) },
                    modifier = Modifier.align(Alignment.CenterVertically),
                    colors = CheckboxDefaults.colors(checkedColor = AppTheme.colors.primary),
                )

                Text(
                    text = LocalStrings.current.advancedRestSettingsDescription,
                    modifier = Modifier.align(Alignment.CenterVertically),
                    color = AppTheme.colors.primary,
                )
            }

            if (state.bigRestEnabled) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    OutlinedTextField(
                        modifier = Modifier.weight(1f),
                        value = state.bigRestPer.value.toString(),
                        onValueChange = { mvi.store.intent(Intent.BigRestPerIsChanged(Count.factory.createOrThrow(it.toInt()))) },
                        label = { Text(LocalStrings.current.every) },
                        singleLine = true,
                        enabled = !state.isLoading,
                    )

                    OutlinedTextField(
                        modifier = Modifier.weight(1f),
                        value = state.bigRestTime.toInt(unit = DurationUnit.MINUTES).toString(),
                        onValueChange = { mvi.store.intent(Intent.BigRestTimeIsChanged(it.toInt().minutes)) },
                        label = { Text(LocalStrings.current.minutes) },
                        singleLine = true,
                        enabled = !state.isLoading,
                    )
                }
            }

            Spacer(modifier = Modifier.padding(8.dp))

            Divider(
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
                    onCheckedChange = { mvi.store.intent(Intent.TimerPauseControlAccessIsChanged(!state.isEveryoneCanPause)) },
                    modifier = Modifier.align(Alignment.CenterVertically),
                    colors = CheckboxDefaults.colors(checkedColor = AppTheme.colors.primary),
                )

                Text(
                    text = LocalStrings.current.publicManageTimerStateDescription,
                    modifier = Modifier.align(Alignment.CenterVertically),
                    color = AppTheme.colors.primary,
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Checkbox(
                    checked = state.isConfirmationRequired,
                    onCheckedChange = { mvi.store.intent(Intent.ConfirmationRequirementChanged(!state.isConfirmationRequired)) },
                    modifier = Modifier.align(Alignment.CenterVertically),
                    colors = CheckboxDefaults.colors(checkedColor = AppTheme.colors.primary),
                )

                Text(
                    text = LocalStrings.current.confirmationRequiredDescription,
                    modifier = Modifier.align(Alignment.CenterVertically),
                    color = AppTheme.colors.primary,
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
                    onClick = { mvi.store.intent(Intent.OnDoneClicked) },
                    enabled = !state.isLoading,
                    isLoading = state.isLoading
                ) {
                    Text(LocalStrings.current.save)
                }
            }
        }
    }
}
