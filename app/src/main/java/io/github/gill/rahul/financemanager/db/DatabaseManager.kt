package io.github.gill.rahul.financemanager.db

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver


const val DatabaseFileName = "app.db"
fun getAndroidSqliteDriver(context: Context) =
    AndroidSqliteDriver(Database.Schema, context, DatabaseFileName)

fun getSqliteDB(driver: SqlDriver) = Database(driver = driver)

//fun getDBQueries(db: Database) = db.Schema.
