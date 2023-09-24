package io.github.gill.rahul.financemanager.ui

import androidx.annotation.StringRes
import io.github.gill.rahul.financemanager.FinanceManagerApp

object Strs {
    fun get(@StringRes stringRes: Int, vararg formatArgs: Any = emptyArray()): String {
        return FinanceManagerApp.instance.getString(stringRes, *formatArgs)
    }
}
