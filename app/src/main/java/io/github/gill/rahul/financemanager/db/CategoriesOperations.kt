package io.github.gill.rahul.financemanager.db

import androidx.compose.ui.graphics.Color
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import io.github.gill.rahul.financemanager.models.CategoryUiModel
import io.github.gill.rahul.financemanager.ui.screen.settings.IconsMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow


class CategoriesOperations(
    private val db: Database,
    private val q: FinanceManagerQueries
) {
    companion object {
        val instance by lazy {
            CategoriesOperations(sqliteDB, dbQueries)
        }
    }

    fun createCategory(
        name: String,
        color: Long,
        icon: String,
        isExpenseCategory: Boolean
    ): Long {
        return db.transactionWithResult {
            q.createUiPropertyForCategory(color, icon)
            val uiPropertiesId = q.lastInsertedRowId().executeAsOne()
            q.creatCategory(name, uiPropertiesId, isExpenseCategory)
            q.lastInsertedRowId().executeAsOne()
        }
    }

    //TODO: on category  delete move all transaction in it other or delete them
    fun deleteCategory(id: Long) = q.deletCategory(id)

    fun getCategoryById(id: Long) = q.getCategoryById(id)

    fun getAllExpenseCategories(): Flow<List<CategoryUiModel>> {
        return q.getAllExpenseCategories(
            mapper = { id: Long, name: String, _: Long, isExpenseCategory: Boolean, _: Long, color: Long, icon: String, orderNum: Long ->
                val separatorIndex= icon.indexOf("//")
                val iconCat = icon.substring(0, separatorIndex)
                val iconKey = icon.substring(separatorIndex + 2)
                CategoryUiModel(
                    id,
                    name,
                    isExpenseCategory,
                    Color(color.toULong()),
                    IconsMap.collection[iconCat]!![iconKey]!!,
                    orderNum
                )
            }
        ).asFlow().mapToList(Dispatchers.IO)
    }


    fun getAllIncomeCategories() = q.getAllIncomeCategories(
       mapper = { id: Long, name: String, _: Long, isExpenseCategory: Boolean, _: Long, color: Long, icon: String, orderNum: Long ->
           val separatorIndex= icon.indexOf("//")
           val iconCat = icon.substring(0, separatorIndex)
           val iconKey = icon.substring(separatorIndex + 2)
           CategoryUiModel(
               id,
               name,
               isExpenseCategory,
               Color(color.toULong()),
               IconsMap.collection[iconCat]!![iconKey]!!,
               orderNum
           )
       }
    ).asFlow().mapToList(Dispatchers.IO)

    fun updateCategoryName(categoryId: Long, updatedName: String) {
        q.updateCategoryName(categoryId = categoryId, updatedName = updatedName)
    }

    fun updateCategoryUiProperties(
        categoryId: Long,
        color: Long,
        icon: String,
        orderNum: Long
    ) = q.updateCategoryUiDetails(color, icon, orderNum, categoryId)

    fun onSaveCategoriesOrder(fromOrderValue: Int, toOrderValue: Int) {
        q.updateCategoriesOrder(fromOrderValue.toLong(), toOrderValue.toLong())
    }

    fun updateCategoryOrdering(categoryIdToOrderNumMap: List<Pair<Long, Int>>) {
        db.transaction {
            categoryIdToOrderNumMap.forEach { (id, orderNum) ->
                q.updateCategoriesOrderSimple( categoryId = id , newOrderNum = orderNum.toLong())
            }
        }
    }
}
