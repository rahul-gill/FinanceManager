package io.github.gill.rahul.financemanager.ui.nav

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.InsertChartOutlined
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.StackedLineChart
import androidx.compose.ui.graphics.vector.ImageVector
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import io.github.gill.rahul.financemanager.R
import io.github.gill.rahul.financemanager.ui.destinations.BudgetScreenDestination
import io.github.gill.rahul.financemanager.ui.destinations.HomeScreenDestination
import io.github.gill.rahul.financemanager.ui.destinations.MoreSettingsScreenDestination
import io.github.gill.rahul.financemanager.ui.destinations.StatsScreenDestination

enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    val icon: ImageVector,
    @StringRes val label: Int
) {
    Home(HomeScreenDestination, Icons.Outlined.Home, R.string.home),
    Budget(BudgetScreenDestination, Icons.Outlined.InsertChartOutlined, R.string.budget),
    Stats(StatsScreenDestination, Icons.Outlined.StackedLineChart, R.string.stats),
    More(MoreSettingsScreenDestination, Icons.Outlined.Menu, R.string.more)
}
