package io.github.gill.rahul.financemanager.db

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver


const val DATABASE_FILE_NAME = "app.db"
fun getAndroidSqliteDriver(context: Context) =
    AndroidSqliteDriver(Database.Schema, context, DATABASE_FILE_NAME)

fun getSqliteDB(driver: SqlDriver) = Database(driver = driver)

//fun getDBQueries(db: Database) = db.Schema.
