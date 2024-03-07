package io.github.gill.rahul.financemanager.ui.nav

import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import dev.olshevski.navigation.reimagined.AnimatedNavHost
import dev.olshevski.navigation.reimagined.NavAction
import dev.olshevski.navigation.reimagined.NavBackHandler
import dev.olshevski.navigation.reimagined.material3.BottomSheetNavHost
import dev.olshevski.navigation.reimagined.moveToTop
import dev.olshevski.navigation.reimagined.navigate
import dev.olshevski.navigation.reimagined.pop
import dev.olshevski.navigation.reimagined.rememberNavController
import io.github.gill.rahul.financemanager.R
import io.github.gill.rahul.financemanager.ui.BudgetScreen
import io.github.gill.rahul.financemanager.ui.create.CreateTxnScreen
import io.github.gill.rahul.financemanager.ui.HomeScreen
import io.github.gill.rahul.financemanager.ui.SomeOtherScreen
import io.github.gill.rahul.financemanager.ui.StatsScreen
import io.github.gill.rahul.financemanager.ui.DateRangeType
import io.github.gill.rahul.financemanager.ui.settings.MoreSettingsScreen
import wow.app.core.ui.components.TabItem
import wow.app.core.ui.components.Tabs
import wow.app.core.ui.materialSharedAxisZIn
import wow.app.core.ui.materialSharedAxisZOut

private val MinToolbarHeight = 96.dp
private val MaxToolbarHeight = 176.dp

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
            Screen.Settings -> MoreSettingsScreen()
            Screen.Tabs -> TabsNavHost(
                toSetting = { navController.navigate(Screen.Settings) },
                goToCreateTxn = { navController.navigate(Screen.CreateTxn) }
            )

            Screen.CreateTxn -> CreateTxnScreen(onGoBack = { navController.pop() })
        }
    }
//    val toolbarHeightRange = with(LocalDensity.current) {
//        MinToolbarHeight.roundToPx()..MaxToolbarHeight.roundToPx()
//    }
//
//
//    val nestedScrollConnection = remember {
//        object : NestedScrollConnection {
//            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
//                return Offset.Zero
//            }
//        }
//    }
//    Box(modifier = Modifier.nestedScroll(nestedScrollConnection)) {
//        DestinationsNavHost(
//            navController = navController,
//            navGraph = NavGraphs.root,
//            engine = navHostEngine,
//            modifier = Modifier
//                .fillMaxSize()
//                .graphicsLayer { translationY = toolbarHeightRange.last.toFloat() }
//                .padding(bottom = MinToolbarHeight)
//        )
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(MaxToolbarHeight)
//        ) {
//            TopAppBar(
//                title = { Text("Finance Manager") },
//                actions = {
//                    IconButton(onClick = { navController.navigate(MoreSettingsScreenDestination) }) {
//                        Icon(imageVector = Icons.Default.Settings, contentDescription = "TODO")
//                    }
//                },
//
//                )
//
//            val selectedIndex = remember {
//                mutableIntStateOf(0)
//            }
//            ScrollableTabRow(
//                selectedTabIndex = selectedIndex.intValue,
//                indicator = {},
//                divider = {}
//            ) {
//                BottomBarDestination.entries.forEachIndexed { index, entry ->
//                    TabItem(selected = selectedIndex.intValue == index, onClick = {
//                        selectedIndex.intValue = index
//                        navController.navigate(entry.direction) {
//                            launchSingleTop = true
//                        }
//                    }, text = stringResource(entry.label))
//                }
//            }
//        }
//    }

//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Finance Manager") },
//                actions = {
//                    IconButton(onClick = { navController.navigate(MoreSettingsScreenDestination) }) {
//                        Icon(imageVector = Icons.Default.Settings, contentDescription = "TODO")
//                    }
//                }
//            )
//        },
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier.padding(paddingValues)
//        ) {
//            val selectedIndex = remember {
//                mutableIntStateOf(0)
//            }
//
//            var offset by remember { mutableFloatStateOf(0f) }
//            Row(
//                modifier = Modifier.scrollable(
//                    orientation = Orientation.Horizontal,
//                    state =  rememberScrollableState { delta ->
//                        offset += delta
//                        delta
//                    }
//                )
//            ) {
//
//            }
//            ScrollableTabRow(
//                selectedTabIndex = selectedIndex.intValue,
//                indicator = {},
//                divider = {}
//            ) {
//                BottomBarDestination.entries.forEachIndexed { index, entry ->
//                    TabItem(selected = selectedIndex.intValue == index, onClick = {
//                        selectedIndex.intValue = index
//                        navController.navigate(entry.direction) {
//                            launchSingleTop = true
//                        }
//                    }, text = stringResource(entry.label))
//                }
//            }
//            DestinationsNavHost(
//                navController = navController,
//                navGraph = NavGraphs.root,
//                engine = navHostEngine,
//                modifier = Modifier.weight(1f)
//            )
//        }
//
//    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TabsNavHost(
    toSetting: () -> Unit,
    goToCreateTxn: () -> Unit
) {
    val bottomSheetController = rememberNavController<Unit>(
        initialBackstack = emptyList()
    )
    var transactionGroupType: DateRangeType by remember {
        mutableStateOf(DateRangeType.Daily)
    }
    var customRangeLengthDays by remember {
        mutableLongStateOf(10L)
    }

    val navController = rememberNavController(TabScreen.Dashboard)
    NavBackHandler(controller = navController)
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.app_name)) },
                actions = {
                    IconButton(
                        onClick = toSetting
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "TODO"
                        )
                    }
                },
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
            }
        }
    ) { paddingValues ->
        val paddingModifier = Modifier.padding(paddingValues)

        AnimatedNavHost(
            modifier = Modifier.padding(paddingValues),
            controller = navController,
            transitionSpec = { action, _, _ ->
                materialSharedAxisZIn(forward = action != NavAction.Pop) togetherWith
                        materialSharedAxisZOut(forward = action == NavAction.Pop)
            }
        ) { tab ->
            when (tab) {
                TabScreen.Dashboard -> HomeScreen(
                    modifier = paddingModifier,
                    goToCreateTxn = goToCreateTxn,
                    customRangeLengthDays = customRangeLengthDays,
                    transactionGroupType = transactionGroupType,
                    showDateRangeChooser = { bottomSheetController.navigate(Unit) }
                )

                TabScreen.Budget -> BudgetScreen(paddingModifier)
                TabScreen.Stats -> StatsScreen(paddingModifier)
                TabScreen.More -> SomeOtherScreen(paddingModifier)
            }
        }
    }
    NavBackHandler(
        controller = bottomSheetController,
        allowEmptyBackstack = true
    )
    BottomSheetNavHost(
        controller = bottomSheetController,
        onDismissRequest = { bottomSheetController.pop() }
    ) {
        Surface(
            tonalElevation = BottomSheetDefaults.Elevation,
            shadowElevation = BottomSheetDefaults.Elevation,
        ) {
            val choiceList = remember {
                listOf(
                    DateRangeType.Daily,
                    DateRangeType.Weekly,
                    DateRangeType.Monthly,
                    DateRangeType.Yearly,
                    DateRangeType.Custom(customRangeLengthDays)
                )
            }
            Card {
                Text(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .align(Alignment.CenterHorizontally),
                    text = stringResource(R.string.group_transactions_by),
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
                Column(Modifier.selectableGroup()) {
                    choiceList.forEach { choice ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .selectable(
                                    selected = (choice == transactionGroupType),
                                    onClick = {
                                        transactionGroupType = choice
                                        bottomSheetController.pop()
                                    },
                                    role = Role.RadioButton
                                )
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (transactionGroupType == choice),
                                onClick = null
                            )
                            Text(
                                text = stringResource(choice.nameStringRes),
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}