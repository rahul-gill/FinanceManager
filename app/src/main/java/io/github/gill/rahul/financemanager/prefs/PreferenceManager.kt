package io.github.gill.rahul.financemanager.prefs

enum class ThemeType {
    System,
    Light,
    Dark
}
object PreferenceManager {
    val themePref = IntInternalPreference(
        key = "app_theme",
        defaultValue = ThemeType.System,
        toInt = { it.ordinal },
        fromInt = { ThemeType.entries[it] }
    )
}
