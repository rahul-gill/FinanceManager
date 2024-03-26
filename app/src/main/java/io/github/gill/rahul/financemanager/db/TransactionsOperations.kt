package io.github.gill.rahul.financemanager.db

import androidx.compose.ui.graphics.Color
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import io.github.gill.rahul.financemanager.models.TransactionType
import io.github.gill.rahul.financemanager.models.TransactionUiModel
import io.github.gill.rahul.financemanager.ui.screen.settings.IconsMap
import kotlinx.coroutines.Dispatchers
import java.time.LocalDateTime

class TransactionsOperations(
    private val db: Database,
    private val q: FinanceManagerQueries
) {

    companion object {
        val instance by lazy {
            TransactionsOperations(sqliteDB, dbQueries)
        }
    }

    fun createTransaction(
        accountId: Long,
        amount: Long,
        type: TransactionType,
        categoryId: Long,
        title: String?,
        description: String?,
        dateTime: LocalDateTime
    ) {
        db.transaction {
            q.createTransaction(
                accountId = accountId,
                amount = amount,
                type = type,
                categoryId = categoryId,
                title = title,
                description = description,
                dateTime = dateTime,
                dueDateTime = null
            )
            q.updateAccountBalance(
                accountId = accountId,
                amountToAdd = if (type == TransactionType.Expense) -amount else amount
            )
        }
    }

    fun deleteTransaction(id: Long) {
        db.transaction {
            q.deleteTransaction(id)

            //change account balance(current)
        }
    }

    fun getTransactionById(id: Long) = q.getTransactionById(id)

    fun getAllTransactions() = q.getAllTransactions(
        mapper = { id, accountId, type, categoryId, amount, title, description, dateTime, dueDateTime, categoryName, isExpense, color, icon ->
            val separatorIndex = icon.indexOf("//")
            val iconCat = icon.substring(0, separatorIndex)
            val iconKey = icon.substring(separatorIndex + 2)
            TransactionUiModel(
                id,
                accountId,
                type,
                categoryId,
                AmountWrapper.fromLong(amount),
                title,
                description,
                dateTime,
                dueDateTime,
                categoryName,
                isExpense,
                Color(color.toULong()),
                IconsMap.collection[iconCat]!![iconKey]!!
            )
        }
    ).asFlow().mapToList(Dispatchers.IO)

    fun updateTransaction() {
        //update
        //change account balance
    }
}

//TODO: when deleting something its uiCategory should also get deleted