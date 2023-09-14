package io.github.gill.rahul.financemanager

import androidx.annotation.StringRes

object StringResources {
    fun get(@StringRes stringRes: Int, vararg formatArgs: Any = emptyArray()): String {
        return FinanceManagerApp.instance.getString(stringRes, *formatArgs)
    }
}
