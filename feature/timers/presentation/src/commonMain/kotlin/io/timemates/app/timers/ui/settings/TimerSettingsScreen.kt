package io.timemates.app.timers.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import io.timemates.app.localization.compose.LocalStrings
import io.timemates.app.style.system.appbar.AppBar
import io.timemates.app.style.system.text_field.SizedOutlinedTextField
import io.timemates.app.style.system.theme.AppTheme
import io.timemates.app.timers.ui.settings.mvi.TimerSettingsStateMachine
import io.timemates.app.timers.ui.settings.mvi.TimerSettingsStateMachine.Event
import io.timemates.sdk.timers.types.value.TimerDescription
import io.timemates.sdk.timers.types.value.TimerName

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerSettingsScreen(
    stateMachine: TimerSettingsStateMachine
) {
    val state by stateMachine.state.collectAsState()

    val nameSize = remember(state.name) { state.name.length }
    val descriptionSize = remember(state.description) { state.description.length }

    Scaffold(
        topBar = {
            AppBar(
                title = LocalStrings.current.timerSettings,
                navigationIcon = {
                    IconButton(
                        onClick = {  },
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

        TimerDescription

        Column(
            modifier = Modifier.fillMaxSize()
                .padding(rootPaddings)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            SizedOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.name,
                onValueChange = { stateMachine.dispatchEvent(Event.NameIsChanged(it)) },
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
                onValueChange = { stateMachine.dispatchEvent(Event.DescriptionIsChanged(it)) },
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

            Divider(
                color = AppTheme.colors.secondary,
                thickness = 1.dp,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .width(59.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .width(150.dp)
                        .padding(8.dp),
                    value = state.workTime.toString(),
                    onValueChange = { stateMachine.dispatchEvent(Event.WorkTimeIsChanged(it.toInt())) },
                    label = { Text(LocalStrings.current.workTime) },
                    singleLine = true,
                    enabled = !state.isLoading,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                )
                OutlinedTextField(
                    modifier = Modifier
                        .width(150.dp)
                        .padding(8.dp),
                    value = state.restTime.toString(),
                    onValueChange = { stateMachine.dispatchEvent(Event.RestTimeIsChanged(it.toInt())) },
                    label = { Text(LocalStrings.current.restTime) },
                    singleLine = true,
                    enabled = !state.isLoading,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                )
            }

            Row(
                modifier = Modifier
                    .width(316.dp)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(
                    checked = state.isAdvancedRestSettingsOn,
                    onCheckedChange = null,
                    modifier = Modifier.padding(4.dp),
                    enabled = true,
                    colors = CheckboxDefaults.colors(checkedColor = AppTheme.colors.primary)
                )
                Text(
                    text = LocalStrings.current.advancedRestSettingsDescription,
                    modifier = Modifier,
                    color = AppTheme.colors.primary,
                )
                Box(modifier = Modifier.width(24.dp))
            }
            if (state.isAdvancedRestSettingsOn) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .width(150.dp)
                            .padding(8.dp),
                        value = state.period.toString(),
                        onValueChange = { stateMachine.dispatchEvent(Event.PeriodIsChanged(it.toInt())) },
                        label = { Text(LocalStrings.current.every) },
                        singleLine = true,
                        enabled = !state.isLoading,
                    )
                    OutlinedTextField(
                        modifier = Modifier
                            .width(150.dp)
                            .padding(8.dp),
                        value = state.pauseTime.toString(),
                        onValueChange = { stateMachine.dispatchEvent(Event.PauseTimeIsChanged(it.toInt())) },
                        label = { Text(LocalStrings.current.minutes) },
                        singleLine = true,
                        enabled = !state.isLoading,
                    )
                }
            }
            Divider(
                color = AppTheme.colors.secondary,
                thickness = 1.dp,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .width(59.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Row(
                modifier = Modifier
                    .width(316.dp)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(
                    checked = state.isPublicManageTimerStateOn,
                    onCheckedChange = null,
                    modifier = Modifier.padding(4.dp),
                    enabled = true,
                    colors = CheckboxDefaults.colors(checkedColor = AppTheme.colors.primary)
                )
                Text(
                    text = LocalStrings.current.publicManageTimerStateDescription,
                    modifier = Modifier,
                    color = AppTheme.colors.primary,
                )
                Box(modifier = Modifier.width(24.dp))
            }
        }
    }
}
