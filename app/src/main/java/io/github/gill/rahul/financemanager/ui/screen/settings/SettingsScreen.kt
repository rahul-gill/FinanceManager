package io.github.gill.rahul.financemanager.ui.screen.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Colorize
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.github.skydoves.colorpicker.compose.AlphaTile
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import wow.app.core.R
import io.github.gill.rahul.financemanager.prefs.PreferenceManager
import wow.app.core.ui.ColorSchemeType
import wow.app.core.ui.DarkThemeType
import wow.app.core.ui.FinManTheme
import wow.app.core.ui.ThemeConfig
import wow.app.core.ui.components.AlertDialog
import wow.app.core.ui.components.GenericPreference
import wow.app.core.ui.components.PreferenceGroupHeader
import wow.app.core.ui.components.ListPreference
import wow.app.core.ui.components.SwitchPreference


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreSettingsScreen(
    onGoBack: () -> Unit,
    onAddAccount: () -> Unit
) {
    val overallBalance = "$ 10000"
    val followSystemColor = PreferenceManager.followSystemColors.asState()
    val seedColor = PreferenceManager.colorSchemeSeed.asState()
    val theme = PreferenceManager.themeConfig.asState()
    val darkThemeType = PreferenceManager.darkThemeType.asState()
    println("4523423 MoreSettingsScreen followSystemColor:$followSystemColor seedColor:$seedColor theme:$theme darkThemeType: $darkThemeType")
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
            Card {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Wallet, contentDescription = null,
                        modifier = Modifier
                            .size(56.dp)
                            .padding(12.dp)
                            .background(Color.Transparent)
                    )
                    Column(Modifier.weight(1f)) {
                        Text(text = "Accounts", style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = "Overall balance: $overallBalance",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                    TextButton(onClick = onAddAccount) {
                        Text("Add New")
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(10) {
                    AccountCard()
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
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
                title = "Pure black background",
                isChecked = darkThemeType.value == DarkThemeType.Black,
                onCheckedChange = { checked ->
                    PreferenceManager.darkThemeType.setValue(if (checked) DarkThemeType.Black else DarkThemeType.Dark)
                }
            )

            Spacer(modifier = Modifier.height(8.dp))
            SwitchPreference(
                title = "Follow System Colors",
                isChecked = followSystemColor.value,
                onCheckedChange = {
                    println("4523423 current value: ${followSystemColor.value} PreferenceManager.followSystemColors.setValue($it)")
                    PreferenceManager.followSystemColors.setValue(it)

                    println("4523423 after setting value is: ${followSystemColor.value}")
                }
            )
            AnimatedVisibility(visible = !followSystemColor.value) {
                Spacer(modifier = Modifier.height(8.dp))
                val isColorPickerDialogShowing = remember {
                    mutableStateOf(false)
                }
                GenericPreference(
                    title = "Custom Color Scheme Seed",
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

@Composable
fun AccountCard(
    modifier: Modifier = Modifier,
    color: Color = Color.Magenta,
    icon: Painter = rememberVectorPainter(Icons.Default.AccountBalanceWallet),
    isDefault: Boolean = true
) {
    OutlinedCard(
        modifier = Modifier
            .width(200.dp)
            .then(modifier),
        border = BorderStroke(width = 1.dp, color = color)
    ) {
        Column(Modifier.padding(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = icon,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(30.dp)
                        .padding(4.dp),
                    tint = color
                )
                Text(text = "Main Account")
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 4.dp)
            ) {
                Text(
                    text = "- $ 10,49000.48",
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.titleMedium
                )
                if (isDefault) {
                    Text(text = "Default", style = MaterialTheme.typography.labelMedium)
                }
            }

        }
    }
}