package io.github.gill.rahul.financemanager.db

import androidx.compose.ui.graphics.Color
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import io.github.gill.rahul.financemanager.models.AccountUiModel
import io.github.gill.rahul.financemanager.ui.screen.settings.IconsMap
import kotlinx.coroutines.Dispatchers

class AccountsOperations(
    private val db: Database,
    private val q: FinanceManagerQueries
) {
    companion object {
        val instance by lazy {
            AccountsOperations(sqliteDB, dbQueries)
        }
    }
    fun createAccount(
        name: String,
        color: Long,
        icon: String,
        startingBalance: Long,
        includeInTotalBalance: Boolean
    ): Long {
        return db.transactionWithResult {
            q.createUiPropertyForAccount(color, icon)
            val uiPropertiesId = q.lastInsertedRowId().executeAsOne()
            q.createAccout(name, uiPropertiesId, startingBalance, includeInTotalBalance)
            q.lastInsertedRowId().executeAsOne()
        }
    }

    fun deleteAccount(id: Long) = q.deleteAccount(id)

//    fun getAccountById(
//        id: Long
//    ) = q.getAccountById(
//        id,
//        mapper = { _, accountName, startingBalance, currentBalance, includeInTotalBalance,
//                   uiPropertiesId, color, icon, orderNum ->
//            AccountUiModel(
//                id,
//                accountName,
//                startingBalance,
//                currentBalance,
//                includeInTotalBalance,
//                UiProperties(uiPropertiesId, color, icon, orderNum)
//            )
//        }
//    )

    fun getAllAccounts() = q.getAllAccounts(
        mapper = { accountId, accountName, startingBalance, currentBalance, includeInTotalBalance,
                   _, color, icon, orderNum ->
            val separatorIndex= icon.indexOf("//")
            val iconCat = icon.substring(0, separatorIndex)
            val iconKey = icon.substring(separatorIndex + 2)
            AccountUiModel(
                accountId,
                accountName,
                startingBalance,
                currentBalance,
                includeInTotalBalance,
                Color(color.toULong()),
                IconsMap.collection[iconCat]!![iconKey]!!,
                orderNum
            )
        }
    ).asFlow().mapToList(Dispatchers.IO)

    fun updateAccountName(
        accountId: Long,
        updatedName: String,
    ) = q.updateAccountName(
        accountId = accountId,
        updatedName = updatedName
    )

    fun updateAccountIncludedInBalance(
        accountId: Long,
        included: Boolean
    ) = q.updateAccountIncludedInBalance(
        accountId = accountId,
        included = included,
    )

    fun updateAccountUiProperties(
        accountId: Long,
        color: Long,
        icon: String,
        orderNum: Long
    ) = q.updateAccountUiDetails(color, icon, orderNum, accountId)

    fun updateAccountStartingBalance(
        accountId: Long,
        startingBalance: Long
    ) = q.updateAccountStartingBalance(accountId, startingBalance)
}
