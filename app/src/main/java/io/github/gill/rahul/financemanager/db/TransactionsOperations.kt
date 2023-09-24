package io.github.gill.rahul.financemanager.db

class TransactionsOperations(
    private val db: Database,
    private val q: FinanceManagerQueries
) {
    fun createTransaction(){
        db.transaction {
            //create transaction

            //change account balance(current)
        }
    }

    fun deleteTransaction(id: Long){
        db.transaction {
            q.deleteTransaction(id)

            //change account balance(current)
        }
    }

    fun getTransactionById(id: Long) = q.getTransactionById(id)

    fun  getAllTransactions() = q.getAllTransactions()

    fun updateTransaction(){
        //update
        //change account balance
    }
}

//TODO: when deleting something its uiCategory should also get deleted