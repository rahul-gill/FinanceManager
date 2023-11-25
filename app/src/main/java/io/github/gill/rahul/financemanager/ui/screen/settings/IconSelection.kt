package io.github.gill.rahul.financemanager.ui.screen.settings

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatShapes
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.github.gill.rahul.financemanager.R
import io.github.gill.rahul.financemanager.ui.MoneyManagerPreviews
import io.github.gill.rahul.financemanager.ui.PreviewWrapper
import io.github.gill.rahul.financemanager.ui.getContentColorForBackground
import io.github.gill.rahul.financemanager.ui.onCondition

object IconsMap {
    val collection = mapOf(
        "entertainment" to mapOf(
            "swimming" to R.drawable.swimming,
            "skull" to R.drawable.skull,
            "brand_netflix" to R.drawable.brand_netflix,
            "device_speaker" to R.drawable.device_speaker,
            "ball_football" to R.drawable.ball_football,
            "disc" to R.drawable.disc,
            "music" to R.drawable.music,
            "brand_youtube_filled" to R.drawable.brand_youtube_filled,
            "pumpkin_scary" to R.drawable.pumpkin_scary,
            "device_gamepad_2" to R.drawable.device_gamepad_2,
            "brand_amazon" to R.drawable.brand_amazon,
            "air_balloon" to R.drawable.air_balloon,
            "ball_american_football" to R.drawable.ball_american_football,
            "beach" to R.drawable.beach,
            "ping_pong" to R.drawable.ping_pong,
            "ball_basketball" to R.drawable.ball_basketball,
        ),
        "general" to mapOf(
            "category_2" to R.drawable.category_2,
            "confetti" to R.drawable.confetti,
            "shield_lock_filled" to R.drawable.shield_lock_filled,
            "balloon" to R.drawable.balloon,
            "books" to R.drawable.books,
        ),
        "shopping" to mapOf(
            "shoe" to R.drawable.shoe,
            "shirt_sport" to R.drawable.shirt_sport,
            "shopping_cart" to R.drawable.shopping_cart,
            "shopping_bag" to R.drawable.shopping_bag,
            "device_mobile" to R.drawable.device_mobile,
            "armchair_2" to R.drawable.armchair_2,
            "device_desktop" to R.drawable.device_desktop,
        ),
        "travel" to mapOf(
            "motorbike" to R.drawable.motorbike,
            "trekking" to R.drawable.trekking,
            "bus" to R.drawable.bus,
            "plane" to R.drawable.plane,
            "speedboat" to R.drawable.speedboat,
            "gas_station" to R.drawable.gas_station,
            "train" to R.drawable.train,
            "backpack" to R.drawable.backpack,
        ),
        "peoplePets" to mapOf(
            "friends" to R.drawable.friends,
            "fish" to R.drawable.fish,
            "paw_filled" to R.drawable.paw_filled,
            "dog" to R.drawable.dog,
            "cat" to R.drawable.cat,
        ),
        "finance" to mapOf(
            "brand_mastercard" to R.drawable.brand_mastercard,
            "credit_card_filled" to R.drawable.credit_card_filled,
            "brand_cashapp" to R.drawable.brand_cashapp,
            "wallet" to R.drawable.wallet,
            "building_bank" to R.drawable.building_bank,
            "currency_pound" to R.drawable.currency_pound,
            "brand_stripe" to R.drawable.brand_stripe,
            "gift_card_filled" to R.drawable.gift_card_filled,
            "home_dollar" to R.drawable.home_dollar,
            "currency_dollar" to R.drawable.currency_dollar,
            "pig_money" to R.drawable.pig_money,
            "graph" to R.drawable.graph,
            "chart_bar" to R.drawable.chart,
            "currency_euro" to R.drawable.currency_euro,
            "timeline" to R.drawable.timeline,
        ),
        "food" to mapOf(
            "pizza" to R.drawable.pizza,
            "cup" to R.drawable.cup,
            "ice_cream" to R.drawable.ice_cream,
            "meat" to R.drawable.meat,
            "beer" to R.drawable.beer,
            "coffee" to R.drawable.coffee,
            "soup" to R.drawable.soup,
            "glass" to R.drawable.glass,
            "cheese" to R.drawable.cheese,
            "sausage" to R.drawable.sausage,
            "candy" to R.drawable.candy,
            "ice_cream_2" to R.drawable.ice_cream_2,
        ),
        "medical" to mapOf(
            "vaccine" to R.drawable.vaccine,
            "wheelchair" to R.drawable.wheelchair,
            "first_aid_kit" to R.drawable.first_aid_kit,
            "heartbeat" to R.drawable.heartbeat,
        )
    )
}
