package io.github.gill.rahul.financemanager.ui.nav

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.navigation.navigate
import io.github.gill.rahul.financemanager.NavGraphs
import io.github.gill.rahul.financemanager.appCurrentDestinationAsState
import io.github.gill.rahul.financemanager.destinations.BudgetScreenDestination
import io.github.gill.rahul.financemanager.destinations.Destination
import io.github.gill.rahul.financemanager.destinations.HomeScreenDestination
import io.github.gill.rahul.financemanager.destinations.MoreSettingsScreenDestination
import io.github.gill.rahul.financemanager.destinations.StatsScreenDestination
import io.github.gill.rahul.financemanager.startAppDestination
import io.github.gill.rahul.financemanager.ui.materialSharedAxisZIn
import io.github.gill.rahul.financemanager.ui.materialSharedAxisZOut

@OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalAnimationApi::class)
@Composable
fun RootNavHost() {
    val navController = rememberNavController()
    val currentDestination: Destination =
        navController.appCurrentDestinationAsState().value
            ?: NavGraphs.root.startAppDestination
    val shouldShowBottomNav = remember(currentDestination) {
        currentDestination == HomeScreenDestination ||
            currentDestination == BudgetScreenDestination ||
            currentDestination == StatsScreenDestination ||
            currentDestination == MoreSettingsScreenDestination
    }
    val navHostEngine = rememberAnimatedNavHostEngine(
        navHostContentAlignment = Alignment.TopCenter,
        rootDefaultAnimations = RootNavGraphDefaultAnimations(
            enterTransition = { materialSharedAxisZIn(forward = true) },
            exitTransition = { materialSharedAxisZOut(forward = false) },
            popEnterTransition = { materialSharedAxisZIn(forward = false) },
            popExitTransition = { materialSharedAxisZOut(forward = false) },
        )
    )

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = shouldShowBottomNav,
                enter = slideInVertically { it },
                exit = slideOutVertically { it }
            ) {
                NavigationBar {
                    BottomBarDestination.entries.forEach { destination ->
                        NavigationBarItem(
                            selected = currentDestination == destination.direction,
                            onClick = {
                                navController.navigate(destination.direction) {
                                    launchSingleTop = true
                                }
                            },
                            icon = {
                                Icon(
                                    destination.icon,
                                    contentDescription = stringResource(destination.label)
                                )
                            },
                            label = { Text(stringResource(destination.label)) },
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        DestinationsNavHost(
            navController = navController,
            navGraph = NavGraphs.root,
            engine = navHostEngine,
            modifier = Modifier.padding(paddingValues)
        )
    }
}
