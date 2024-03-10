@file:OptIn(ExperimentalSettingsApi::class, DelicateCoroutinesApi::class)

package wow.app.core.prefs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.getBooleanFlow
import com.russhwolf.settings.coroutines.getIntFlow
import com.russhwolf.settings.coroutines.getLongFlow
import com.russhwolf.settings.coroutines.getStringFlow
import com.russhwolf.settings.coroutines.getStringOrNullFlow
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

val settings: Settings by lazy { Settings() }
val observableSettings: ObservableSettings by lazy { settings as ObservableSettings }

interface Preference<T> {
    fun setValue(value: T)
    val value: T
    val observableValue: StateFlow<T>
    val key: String
    val defaultValue: T

    @Composable
    fun asState() = observableValue.collectAsState()
}

fun <T,BackingT> customPreference(
    backingPref: Preference<BackingT>,
    defaultValue: T,
    serialize: (T) -> BackingT,
    deserialize: (BackingT) -> T
) = object : Preference<T> {

    override val key = backingPref.key
    override val defaultValue = defaultValue

    override fun setValue(value: T) {
        backingPref.setValue(serialize(value))
    }

    override val value: T
        get() = deserialize(backingPref.value)
    override val observableValue: StateFlow<T>
        get() = backingPref.observableValue
            .map {
                deserialize(it)
            }
            .stateIn(GlobalScope, SharingStarted.Eagerly, value)
}

inline fun <reified T : Enum<T>> enumPreference(
    key: String,
    defaultValue: T
) = object : Preference<T> {

    override val key = key
    override val defaultValue = defaultValue

    override fun setValue(value: T) {
        settings.putInt(key, value.ordinal)
    }

    override val value: T
        get() = enumValues<T>()[settings.getInt(key, defaultValue.ordinal)]
    override val observableValue: StateFlow<T>
        get() = observableSettings.getIntFlow(key, defaultValue.ordinal)
            .map { enumValues<T>()[it] }
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

class BooleanPreference(
    override val key: String,
    override val defaultValue: Boolean
) : Preference<Boolean> {
    override fun setValue(value: Boolean) {
        settings.putBoolean(key, value)
    }

    override val value: Boolean
        get() = settings.getBoolean(key, defaultValue)

    override val observableValue: StateFlow<Boolean>
        get() = observableSettings.getBooleanFlow(key, defaultValue)
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
