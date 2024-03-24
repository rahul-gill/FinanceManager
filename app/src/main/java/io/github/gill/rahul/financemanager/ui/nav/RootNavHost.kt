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
import io.github.gill.rahul.financemanager.db.Categories
import io.github.gill.rahul.financemanager.db.CategoriesOperations
import io.github.gill.rahul.financemanager.db.UiProperties
import io.github.gill.rahul.financemanager.ui.screen.categories.CreateCategoryScreen
import io.github.gill.rahul.financemanager.ui.screen.create.CreateTxnScreen
import io.github.gill.rahul.financemanager.ui.screen.categories.CategoryScreen
import io.github.gill.rahul.financemanager.ui.screen.settings.MoreSettingsScreen
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
            Screen.Settings -> MoreSettingsScreen(
                onGoBack = { navController.pop() },
                onAddAccount = {/*TODO*/ }
            )

            Screen.Tabs -> TabsNavHost(
                toSetting = { navController.navigate(Screen.Settings) },
                goToCreateTxn = { navController.navigate(Screen.CreateTxn) },
                goToCategories = { navController.navigate(Screen.Categories) }
            )

            Screen.CreateTxn -> CreateTxnScreen(onGoBack = { navController.pop() })
            Screen.Categories -> CategoryScreen(
                navigateUp = { navController.pop() },
                onCategoryClick = {},
                expenseCategories = CategoriesOperations.instance.getAllExpenseCategories().collectAsState(
                    initial = emptyList()
                ).value,
                incomeCategories = CategoriesOperations.instance.getAllIncomeCategories().collectAsState(
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
        }
    }
}

