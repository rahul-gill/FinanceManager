package io.github.gill.rahul.financemanager.db

class CategoriesOperations(
    private val db: Database,
    private val q: FinanceManagerQueries
) {
    fun createCategory(
        name: String,
        color: Long,
        icon: String,
        orderNum: Double
    ): Long {
        return db.transactionWithResult {
            q.createUiProperty(color, icon, orderNum)
            val uiPropertiesId = q.lastInsertedRowId().executeAsOne()
            q.creatCategory(name, uiPropertiesId)
            q.lastInsertedRowId().executeAsOne()
        }
    }

    //TODO: on category  delete move all transaction in it other or delete them
    fun deleteCategory(id: Long) = q.deletCategory(id)

    fun getCategoryById(id: Long) = q.getCategoryById(id)

    fun getAllCategories() = q.getAllCategories()

    fun updateCategoryName(categoryId: Long, updatedName: String) {
        q.updateCategoryName(categoryId = categoryId, updatedName = updatedName)
    }

    fun updateCategoryUiProperties(
        categoryId: Long,
        color: Long,
        icon: String,
        orderNum: Double
    ) = q.updateCategoryUiDetails(color, icon, orderNum, categoryId)
}
