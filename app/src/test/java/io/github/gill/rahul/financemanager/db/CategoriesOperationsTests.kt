package io.github.gill.rahul.financemanager.db

import androidx.compose.ui.graphics.Color
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Test


class CategoriesOperationsTests {

    private val sqlDriver = run {
        JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY).apply {
            Database.Schema.create(this)
        }
    }
    private val db = getSqliteDB(sqlDriver)
    private val categoriesOperations = CategoriesOperations(
        db,
        db.financeManagerQueries
    )

    @Test
    fun `is category reordering working correctly`() {
        (0..20).map {
            categoriesOperations.createCategory(
                "$it", Color.Red.value.toLong(), "entertainment//swimming", true
            )
        }
        runBlocking {
            val list = categoriesOperations.getAllExpenseCategories().first()
            assert(list.size == 21)
            println(categoriesOperations.getAllExpenseCategories().first().map { "{${it.name}_${it.orderNum}}" })
            categoriesOperations.onSaveCategoriesOrder(5, 20)
            println(categoriesOperations.getAllExpenseCategories().first().map { "{${it.name}_${it.orderNum}}" })
            println("chaned rows: ${db.financeManagerQueries.changdRows().executeAsOne()}")
            categoriesOperations.onSaveCategoriesOrder(20, 5)
            println(categoriesOperations.getAllExpenseCategories().first().map { "{${it.name}_${it.orderNum}}" })
            println("chaned rows: ${db.financeManagerQueries.changdRows().executeAsOne()}")
        }


    }
}
