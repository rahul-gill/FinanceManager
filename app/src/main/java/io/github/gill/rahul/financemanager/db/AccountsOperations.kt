package io.github.gill.rahul.financemanager.db

import io.github.gill.rahul.financemanager.models.Account

class AccountsOperations(
    private val db: Database,
    private val q: FinanceManagerQueries
) {
    fun createAccount(
        name: String,
        color: Long,
        icon: String,
        orderNum: Double,
        startingBalance: Long,
        includeInTotalBalance: Boolean
    ): Long {
        return db.transactionWithResult {
            q.createUiProperty(color, icon, orderNum)
            val uiPropertiesId = q.lastInsertedRowId().executeAsOne()
            q.createAccout(name, uiPropertiesId, startingBalance, includeInTotalBalance)
            q.lastInsertedRowId().executeAsOne()
        }
    }

    fun deleteAccount(id: Long) = q.deleteAccount(id)

    fun getAccountById(
        id: Long
    ) = q.getAccountById(
        id,
        mapper = { _, accountName, startingBalance, currentBalance, includeInTotalBalance,
                   uiPropertiesId, color, icon, orderNum ->
            Account(
                id,
                accountName,
                startingBalance,
                currentBalance,
                includeInTotalBalance,
                UiProperties(uiPropertiesId, color, icon, orderNum)
            )
        }
    )

    fun getAllAccounts() = q.getAllAccounts(
        mapper = { accountId, accountName, startingBalance, currentBalance, includeInTotalBalance,
                   uiPropertiesId, color, icon, orderNum ->
            Account(
                accountId,
                accountName,
                startingBalance,
                currentBalance,
                includeInTotalBalance,
                UiProperties(uiPropertiesId, color, icon, orderNum)
            )
        }
    )

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
        orderNum: Double
    ) = q.updateAccountUiDetails(color, icon, orderNum, accountId)

    fun updateAccountStartingBalance(
        accountId: Long,
        startingBalance: Long
    ) = q.updateAccountStartingBalance(accountId, startingBalance)
}
