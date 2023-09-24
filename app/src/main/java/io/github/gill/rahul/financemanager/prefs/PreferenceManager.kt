package io.github.gill.rahul.financemanager.prefs

enum class ThemeType {
    System,
    Light,
    Dark
}
object PreferenceManager {
    val themePref = enumPreference(
        key = "app_theme",
        defaultValue = ThemeType.System
    )
}
