package wow.app.core.util

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

object Singleton {

    fun <T> build(creator: () -> T) = object : ReadOnlyProperty<Any?, T> {
        private var instance: T? = null

        override fun getValue(thisRef: Any?, property: KProperty<*>): T =
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = creator()
                    }
                    instance!!
                }
            } else {
                instance!!
            }
    }
}
