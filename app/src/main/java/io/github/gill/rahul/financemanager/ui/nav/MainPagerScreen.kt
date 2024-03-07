package io.github.gill.rahul.financemanager.ui.nav

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun MainPagerScreen(
//    navController: NavController
//) {
//
//
//
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
////            DestinationsNavHost(
////                navController = navController,
////                navGraph = NavGraphs.root,
////                engine = navHostEngine,
////                modifier = Modifier.weight(1f)
////            )
//        }
//
//    }
//}