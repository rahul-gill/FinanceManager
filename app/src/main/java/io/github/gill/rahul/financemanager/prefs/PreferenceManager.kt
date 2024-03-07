package io.github.gill.rahul.financemanager.prefs

import androidx.compose.ui.graphics.Color
import wow.app.core.prefs.customPreference
import wow.app.core.prefs.enumPreference
import wow.app.core.ui.ColorSchemeType
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
    val colorSchemeType = customPreference(
        key = "color_scheme_type",
        defaultValue = ColorSchemeType.Dynamic,
        serialize = { scheme ->
            when (scheme) {
                ColorSchemeType.Dynamic -> "dynamic"
                is ColorSchemeType.WithSeed -> "seed${scheme.seed.value}"
            }
        },
        deserialize = { nullableStrValue ->
            if (nullableStrValue == null
                || nullableStrValue.startsWith("dynamic")
                || nullableStrValue.length <= "seed".length
                || nullableStrValue.substring("seed".length).toULongOrNull() == null
            ) {
                ColorSchemeType.Dynamic
            } else try {
                val colorLong = nullableStrValue.substring("seed".length).toULong()
                ColorSchemeType.WithSeed(Color(colorLong))
            } catch (e: Exception) {
                ColorSchemeType.Dynamic
            }
        }
    )
}
