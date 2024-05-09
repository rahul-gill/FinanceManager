package io.github.gill.rahul.financemanager.ui.nav

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed interface Screen : Parcelable {
    @Parcelize
    data object Tabs : Screen

    @Parcelize
    data object Settings : Screen

    @Parcelize
    data object CreateTxn : Screen

    @Parcelize
    data object CreateCategory : Screen

    @Parcelize
    data object Categories : Screen

    @Parcelize
    data object Accounts : Screen

    @Parcelize
    data object CreateAccount : Screen

    @Parcelize
    data class TxnDetails(val txnId: Long) : Screen

}


enum class TabScreen {
    Dashboard, Budget, Stats, More
}