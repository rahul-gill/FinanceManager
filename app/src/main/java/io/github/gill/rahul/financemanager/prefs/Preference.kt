@file:OptIn(ExperimentalSettingsApi::class, DelicateCoroutinesApi::class)

package io.github.gill.rahul.financemanager.prefs

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.getIntFlow
import com.russhwolf.settings.coroutines.getLongFlow
import com.russhwolf.settings.coroutines.getStringFlow
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

private val settings: Settings by lazy { Settings() }
private val observableSettings: ObservableSettings by lazy { settings as ObservableSettings }

interface Preference<T> {
    fun setValue(value: T)
    val value: T
    val observableValue: StateFlow<T>
    val key: String
    val defaultValue: T
}

class IntInternalPreference<T>(
    override val key: String,
    override val defaultValue: T,
    val fromInt: (Int) -> T,
    val toInt: (T) -> Int
): Preference<T> {
    override fun setValue(value: T) {
        settings.putInt(key, toInt(value))
    }

    override val value: T
        get() = fromInt(settings.getInt(key, toInt(defaultValue)))
    override val observableValue: StateFlow<T>
        get() = observableSettings.getIntFlow(key, toInt(defaultValue))
            .map(fromInt)
            .stateIn(GlobalScope, SharingStarted.Eagerly, value)

}

class IntPreference(
    override val key: String,
    override val defaultValue: Int
) : Preference<Int> {
    override fun setValue(value: Int) {
        settings.putInt(key, value)
    }

    override val value: Int
        get() = settings.getInt(key, defaultValue)

    override val observableValue: StateFlow<Int>
        get() = observableSettings.getIntFlow(key, defaultValue)
            .stateIn(GlobalScope, SharingStarted.Eagerly, value)
}

class LongPreference(
    override val key: String,
    override val defaultValue: Long
) : Preference<Long> {
    override fun setValue(value: Long) {
        settings.putLong(key, value)
    }

    override val value: Long
        get() = settings.getLong(key, defaultValue)

    override val observableValue: StateFlow<Long>
        get() = observableSettings.getLongFlow(key, defaultValue)
            .stateIn(GlobalScope, SharingStarted.Eagerly, value)
}

class StringPreference(
    override val key: String,
    override val defaultValue: String
) : Preference<String> {
    override fun setValue(value: String) {
        settings.putString(key, value)
    }

    override val value: String
        get() = settings.getString(key, defaultValue)

    override val observableValue: StateFlow<String>
        get() = observableSettings.getStringFlow(key, defaultValue)
            .stateIn(GlobalScope, SharingStarted.Eagerly, value)
}
