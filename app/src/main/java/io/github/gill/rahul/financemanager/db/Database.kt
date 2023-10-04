package io.github.gill.rahul.financemanager.db

import android.content.Context
import app.cash.sqldelight.ColumnAdapter
import app.cash.sqldelight.EnumColumnAdapter
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import io.github.gill.rahul.financemanager.FinanceManagerApp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

const val DatabaseFileName = "app.db"
fun getAndroidSqliteDriver(context: Context) =
    AndroidSqliteDriver(Database.Schema, context, DatabaseFileName)

fun getSqliteDB(driver: SqlDriver) = Database(
    driver = driver,
    TransactionsAdapter = Transactions.Adapter(
        typeAdapter = EnumColumnAdapter<TransactionType>(),
        dateTimeAdapter = LocalDateTimeAdapter,
        dueDateTimeAdapter = LocalDateTimeAdapter
    )
)

fun getDBQueries(db: Database) = db.financeManagerQueries

private object LocalDateTimeAdapter : ColumnAdapter<LocalDateTime, String> {
    override fun decode(databaseValue: String): LocalDateTime =
        LocalDateTime.parse(databaseValue)

    override fun encode(value: LocalDateTime): String =
        value.format(DateTimeFormatter.ISO_DATE_TIME)
}

private val sqliteDriver by lazy {
    getAndroidSqliteDriver(FinanceManagerApp.instance)
}

val sqliteDB by lazy {
    getSqliteDB(sqliteDriver)
}

val dbQueries by lazy {
    getDBQueries(sqliteDB)
}
