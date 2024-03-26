package io.github.gill.rahul.financemanager.ui.nav

import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShapeLine
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.olshevski.navigation.reimagined.AnimatedNavHost
import dev.olshevski.navigation.reimagined.NavAction
import dev.olshevski.navigation.reimagined.NavBackHandler
import dev.olshevski.navigation.reimagined.moveToTop
import dev.olshevski.navigation.reimagined.navigate
import dev.olshevski.navigation.reimagined.rememberNavController
import io.github.gill.rahul.financemanager.ui.BudgetScreen
import io.github.gill.rahul.financemanager.ui.screen.dashboard.DashboardScreen
import io.github.gill.rahul.financemanager.ui.SomeOtherScreen
import io.github.gill.rahul.financemanager.ui.StatsScreen
import wow.app.core.R
import wow.app.core.ui.components.BaseDialog
import wow.app.core.ui.components.TabItem
import wow.app.core.ui.components.Tabs
import wow.app.core.ui.materialSharedAxisZIn
import wow.app.core.ui.materialSharedAxisZOut

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun TabsNavHost(
    goToSettings: () -> Unit,
    goToCreateTxn: () -> Unit,
    goToCategories: () -> Unit,
    goToAccounts: () -> Unit
) {
    val navController = rememberNavController(TabScreen.Dashboard)
    var showMoreTabsDialog by remember {
        mutableStateOf(false)
    }
    NavBackHandler(controller = navController)
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(id = R.string.app_name),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            )
        },
        bottomBar = {
            Tabs(
                modifier = Modifier
                    .selectableGroup()
                    .navigationBarsPadding()
            ) {
                val tabs = remember {
                    TabScreen.entries.toList()
                }

                tabs.forEach { tabScreen ->
                    TabItem(
                        onClick = {
                            if (!navController.moveToTop { it == tabScreen }) {
                                navController.navigate(tabScreen)
                            }
                        },
                        selected = navController.backstack.entries.last().destination == tabScreen,
                        text = stringResource(
                            when (tabScreen) {
                                TabScreen.Dashboard -> R.string.dashboard
                                TabScreen.Budget -> R.string.budget
                                TabScreen.More -> R.string.more
                                TabScreen.Stats -> R.string.stats
                            }
                        )
                    )
                }

                IconButton(onClick = { showMoreTabsDialog = true }) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "TODO",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    ) { paddingValues ->

        AnimatedNavHost(
            modifier = Modifier.padding(paddingValues),
            controller = navController,
            transitionSpec = { action, _, _ ->
                materialSharedAxisZIn(forward = action != NavAction.Pop) togetherWith
                        materialSharedAxisZOut(forward = action == NavAction.Pop)
            }
        ) { tab ->
            when (tab) {
                TabScreen.Dashboard -> DashboardScreen(
                    goToCreateTxn = goToCreateTxn
                )

                TabScreen.Budget -> BudgetScreen()
                TabScreen.Stats -> StatsScreen()
                TabScreen.More -> SomeOtherScreen()
            }
        }
    }
    if (showMoreTabsDialog) {
        BaseDialog(
            onDismissRequest = { showMoreTabsDialog = false }
        ) {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically)
            ) {
                MoreNavItem(
                    text = stringResource(id = R.string.accounts),
                    onClick = goToAccounts,
                    icon = rememberVectorPainter(image = Icons.Default.AccountBalanceWallet)
                )
                MoreNavItem(
                    text = stringResource(id = R.string.categories),
                    onClick = goToCategories,
                    icon = rememberVectorPainter(image = Icons.Default.ShapeLine)
                )
                MoreNavItem(
                    text = stringResource(id = R.string.settings),
                    onClick = goToSettings,
                    icon = rememberVectorPainter(image = Icons.Default.Settings)
                )
            }
        }
    }
}

@Composable
fun MoreNavItem(
    modifier: Modifier = Modifier,
    text: String,
    icon: Painter,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(25))
            .background(shape = RoundedCornerShape(25), color = Color.Transparent)
            .clickable { onClick() }
            .padding(12.dp)
            .then(modifier)
    ) {
        Icon(painter = icon, contentDescription = null)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}
