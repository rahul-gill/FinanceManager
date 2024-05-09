package io.github.gill.rahul.financemanager.db

import androidx.compose.ui.graphics.Color
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import io.github.gill.rahul.financemanager.db.DateRangeType.Weekly.isInDateRange
import io.github.gill.rahul.financemanager.models.TransactionType
import io.github.gill.rahul.financemanager.models.TransactionUiModel
import io.github.gill.rahul.financemanager.ui.screen.settings.IconsMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.TemporalAdjusters.previousOrSame

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

    fun transactionsDateFiltered(
        dateRangeType: DateRangeType,
        currentRangeStart: LocalDate
    ): Flow<List<HeaderListItem>> = getAllTransactions().map { txnList ->
        val finalList = mutableListOf<HeaderListItem>()
        var prevDateTime: LocalDateTime? = null
        val newTxnList = txnList
            .filter { txn ->
                txn.dateTime.toLocalDate().isInDateRange(dateRangeType, currentRangeStart)
            }.sortedWith { first, second ->
                when {
                    first.dateTime.year > second.dateTime.year -> 1
                    first.dateTime.year < second.dateTime.year -> -1
                    first.dateTime.month > second.dateTime.month -> 1
                    first.dateTime.month < second.dateTime.month -> -1
                    else -> 0
                }
            }
        newTxnList.forEach { item ->
            if (prevDateTime == null || prevDateTime!!.year != item.dateTime.year || prevDateTime!!.month != item.dateTime.month) {
                finalList.add(HeaderListItem.Header(item.dateTime.toLocalDate()))
            }
            finalList.add(HeaderListItem.Item(item))
            prevDateTime = item.dateTime
        }
        finalList
    }


    fun overallIncomeExpenseFiltered(
        dateRangeType: DateRangeType,
        currentRangeStart: LocalDate
    ): Pair<AmountWrapper, AmountWrapper> {
        var income = AmountWrapper.fromLong(0)
        var expense = AmountWrapper.fromLong(0)
        val newTxnList = getAllTransactions().map { txnList ->
            txnList.filter { txn ->
                txn.dateTime.toLocalDate().isInDateRange(dateRangeType, currentRangeStart)
            }.forEach { txn ->
                if (txn.isExpense) expense += txn.amount
                else income += txn.amount
            }
        }
        return Pair(income, expense)
    }

    fun updateTransaction() {
        //update
        //change account balance
    }
}

//TODO: when deleting something its uiCategory should also get deleted

sealed class HeaderListItem {
    class Header(val value: LocalDate) : HeaderListItem()
    class Item(val value: TransactionUiModel) : HeaderListItem()
}