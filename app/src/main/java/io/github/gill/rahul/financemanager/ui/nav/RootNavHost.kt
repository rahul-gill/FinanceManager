package io.github.gill.rahul.financemanager.ui.nav

import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import dev.olshevski.navigation.reimagined.AnimatedNavHost
import dev.olshevski.navigation.reimagined.NavAction
import dev.olshevski.navigation.reimagined.NavBackHandler
import dev.olshevski.navigation.reimagined.navigate
import dev.olshevski.navigation.reimagined.pop
import dev.olshevski.navigation.reimagined.rememberNavController
import io.github.gill.rahul.financemanager.db.AccountsOperations
import io.github.gill.rahul.financemanager.db.CategoriesOperations
import io.github.gill.rahul.financemanager.ui.screen.accounts.AccountsListScreen
import io.github.gill.rahul.financemanager.ui.screen.accounts.CreateAccountScreen
import io.github.gill.rahul.financemanager.ui.screen.categories.CreateCategoryScreen
import io.github.gill.rahul.financemanager.ui.screen.txn.CreateTxnScreen
import io.github.gill.rahul.financemanager.ui.screen.categories.CategoriesListScreen
import io.github.gill.rahul.financemanager.ui.screen.settings.SettingsScreen
import wow.app.core.ui.materialSharedAxisZIn
import wow.app.core.ui.materialSharedAxisZOut

@Composable
fun RootNavHost() {
    val navController = rememberNavController<Screen>(Screen.Tabs)
    NavBackHandler(controller = navController)
    AnimatedNavHost(
        controller = navController,
        transitionSpec = { action, _, _ ->
            materialSharedAxisZIn(forward = action != NavAction.Pop) togetherWith
                    materialSharedAxisZOut(forward = action == NavAction.Pop)
        }
    ) { screen ->
        when (screen) {
            Screen.Settings -> SettingsScreen(
                onGoBack = { navController.pop() }
            )

            Screen.Tabs -> TabsNavHost(
                goToSettings = { navController.navigate(Screen.Settings) },
                goToCreateTxn = { navController.navigate(Screen.CreateTxn) },
                goToCategories = { navController.navigate(Screen.Categories) },
                goToAccounts = { navController.navigate(Screen.Accounts) }
            )

            Screen.CreateTxn -> CreateTxnScreen(onGoBack = { navController.pop() })
            Screen.Categories -> CategoriesListScreen(
                navigateUp = { navController.pop() },
                onCategoryClick = {},
                expenseCategories = CategoriesOperations.instance.getAllExpenseCategories()
                    .collectAsState(
                        initial = emptyList()
                    ).value,
                incomeCategories = CategoriesOperations.instance.getAllIncomeCategories()
                    .collectAsState(
                        initial = emptyList()
                    ).value,
                onSaveCategoriesOrder = CategoriesOperations.instance::onSaveCategoriesOrder
            ) {
                navController.navigate(Screen.CreateCategory)
            }

            Screen.CreateCategory -> {
                CreateCategoryScreen(
                    onSave = { name, color, iconIdentifier, isExpenseCategory ->
                        CategoriesOperations.instance.createCategory(
                            name = name,
                            color = color.value.toLong(),
                            icon = iconIdentifier,
                            isExpenseCategory
                        )
                    },
                    navigateUp = { navController.pop() }
                )
            }

            Screen.Accounts -> AccountsListScreen(
                navigateUp = { navController.pop() },
                onAccountClick = { /*TODO*/ },
                accounts = AccountsOperations.instance.getAllAccounts()
                    .collectAsState(initial = emptyList()).value,
                navigateToCreateAccount = {
                    navController.navigate(Screen.CreateAccount)
                }
            )

            Screen.CreateAccount -> CreateAccountScreen(
                navigateUp = { navController.pop() },
                onSave = { name, color, iconIdentifier, startingBalance, includeInTotalBalance ->
                    AccountsOperations.instance.createAccount(
                        name = name,
                        color = color.value.toLong(),
                        icon = iconIdentifier,
                        startingBalance = startingBalance,
                        includeInTotalBalance = includeInTotalBalance
                    )
                }
            )
        }
    }
}

