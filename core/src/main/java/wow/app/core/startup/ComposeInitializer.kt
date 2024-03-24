package wow.app.core.startup

import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.ProcessLifecycleInitializer
import androidx.startup.Initializer

internal class ComposeInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        ComposeView(context)
        Log.i("ComposeInitializer", "init done")
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(ProcessLifecycleInitializer::class.java)
    }
}