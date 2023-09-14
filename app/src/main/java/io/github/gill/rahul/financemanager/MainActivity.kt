package io.github.gill.rahul.financemanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.navigate
import io.github.gill.rahul.financemanager.destinations.Destination
import io.github.gill.rahul.financemanager.ui.nav.BottomBarDestination
import io.github.gill.rahul.financemanager.ui.theme.FinanceManagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinanceManagerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val currentDestination: Destination =
                        navController.appCurrentDestinationAsState().value
                            ?: NavGraphs.root.startAppDestination
                    val shouldShowBottomNav by remember {
                        derivedStateOf {
                            BottomBarDestination.entries.find {
                                it.direction == currentDestination
                            } != null
                        }
                    }

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
                            modifier = Modifier.padding(paddingValues)
                        )
                    }
                }
            }
        }
    }
}
