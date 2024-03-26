package io.github.gill.rahul.financemanager.ui.screen.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Colorize
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.skydoves.colorpicker.compose.AlphaTile
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import io.github.gill.rahul.financemanager.prefs.PreferenceManager
import wow.app.core.R
import wow.app.core.ui.ColorSchemeType
import wow.app.core.ui.DarkThemeType
import wow.app.core.ui.FinManTheme
import wow.app.core.ui.ThemeConfig
import wow.app.core.ui.components.AlertDialog
import wow.app.core.ui.components.GenericPreference
import wow.app.core.ui.components.ListPreference
import wow.app.core.ui.components.PreferenceGroupHeader
import wow.app.core.ui.components.SwitchPreference


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onGoBack: () -> Unit
) {
    val followSystemColor = PreferenceManager.followSystemColors.asState()
    val seedColor = PreferenceManager.colorSchemeSeed.asState()
    val theme = PreferenceManager.themeConfig.asState()
    val darkThemeType = PreferenceManager.darkThemeType.asState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.settings)) },
                navigationIcon = {
                    IconButton(onClick = onGoBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(
                                id = R.string.go_back_screen
                            )
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(paddingValues)
        ) {
            PreferenceGroupHeader(title = stringResource(id = R.string.look_and_feel))
            Spacer(modifier = Modifier.height(8.dp))
            ListPreference(
                title = stringResource(id = R.string.app_theme),
                items = ThemeConfig.entries.toList(),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Palette, contentDescription = null)
                },
                selectedItem = theme.value,
                onItemSelection = PreferenceManager.themeConfig::setValue,
                itemToDescription = { theme ->
                    stringResource(
                        id = when (theme) {
                            ThemeConfig.FollowSystem -> R.string.follow_system
                            ThemeConfig.Light -> R.string.light
                            ThemeConfig.Dark -> R.string.dark
                        }
                    )
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            SwitchPreference(
                title = stringResource(R.string.pure_black_background),
                isChecked = darkThemeType.value == DarkThemeType.Black,
                onCheckedChange = { checked ->
                    PreferenceManager.darkThemeType.setValue(if (checked) DarkThemeType.Black else DarkThemeType.Dark)
                }
            )

            Spacer(modifier = Modifier.height(8.dp))
            SwitchPreference(
                title = stringResource(R.string.follow_system_colors),
                isChecked = followSystemColor.value,
                onCheckedChange = {
                    PreferenceManager.followSystemColors.setValue(it)
                }
            )
            AnimatedVisibility(visible = !followSystemColor.value) {
                Spacer(modifier = Modifier.height(8.dp))
                val isColorPickerDialogShowing = remember {
                    mutableStateOf(false)
                }
                GenericPreference(
                    title = stringResource(R.string.custom_color_scheme_seed),
                    onClick = {
                        isColorPickerDialogShowing.value = true
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Colorize, contentDescription = null)
                    },
                    trailingContent = {
                        Surface(
                            modifier = Modifier
                                .background(
                                    color = Color.Transparent,
                                    shape = CircleShape
                                ),
                            color = seedColor.value,
                            shape = CircleShape,
                            content = {}
                        )
                    }
                )
                if (isColorPickerDialogShowing.value) {
                    val pickedColor = remember {
                        mutableStateOf(PreferenceManager.colorSchemeSeed.value)
                    }
                    val colorController = rememberColorPickerController()
                    FinManTheme(
                        colorSchemeType = if (followSystemColor.value) ColorSchemeType.Dynamic else ColorSchemeType.WithSeed(
                            pickedColor.value
                        ),
                        themeConfig = theme.value,
                        darkThemeType = darkThemeType.value
                    ) {
                        AlertDialog(
                            onDismissRequest = { isColorPickerDialogShowing.value = false },
                            title = {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(text = "Theme seed color")
                                    AlphaTile(
                                        modifier = Modifier
                                            .size(30.dp)
                                            .clip(CircleShape),
                                        controller = colorController
                                    )
                                }
                            },
                            body = {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    HsvColorPicker(
                                        modifier = Modifier.height(450.dp),
                                        controller = colorController,
                                        initialColor = pickedColor.value,
                                        onColorChanged = { envelope ->
                                            pickedColor.value = envelope.color
                                        }
                                    )
                                }
                            },
                            buttonBar = {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    TextButton(onClick = {
                                        isColorPickerDialogShowing.value = false
                                    }) {
                                        Text(text = stringResource(id = R.string.cancel))
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    TextButton(onClick = {
                                        PreferenceManager.colorSchemeSeed.setValue(pickedColor.value)
                                        isColorPickerDialogShowing.value = false
                                    }) {
                                        Text(text = stringResource(id = R.string.ok))
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}