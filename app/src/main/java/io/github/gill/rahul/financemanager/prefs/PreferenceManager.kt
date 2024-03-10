package io.github.gill.rahul.financemanager.prefs

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import wow.app.core.prefs.BooleanPreference
import wow.app.core.prefs.IntPreference
import wow.app.core.prefs.LongPreference
import wow.app.core.prefs.Preference
import wow.app.core.prefs.customPreference
import wow.app.core.prefs.enumPreference
import wow.app.core.prefs.observableSettings
import wow.app.core.prefs.settings
import wow.app.core.ui.DarkThemeType
import wow.app.core.ui.ThemeConfig

object PreferenceManager {
    val themeConfig = enumPreference(
        key = "theme_config",
        defaultValue = ThemeConfig.FollowSystem
    )
    val darkThemeType = enumPreference(
        key = "dark_theme_type",
        defaultValue = DarkThemeType.Dark
    )
    val followSystemColors = BooleanPreference(key = "follow_system_colors", defaultValue = true)
    val colorSchemeSeed = customPreference(
        backingPref = LongPreference("color_scheme_type", 0),
        defaultValue = Color(0xFF9867C5),
        serialize = { color -> color.value.toLong() },
        deserialize = { if(it == 0L)  Color(0xFF9867C5) else  Color(it.toULong()) }
    )
}
