package com.tangerine.taipeitour.compose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.tangerine.taipeitour.R

/**
 * Contract for information needed on every Rally navigation destination
 */
abstract class Destination {
    abstract val route: String
    open val arguments: List<NamedNavArgument> = listOf()
}

abstract class MainScreen : Destination() {
    abstract val title: Int
    abstract val iconEnabled: ImageVector
    abstract val iconDisabled: ImageVector

    open val children: List<Destination> = listOf()
}

object AttractionsPage : MainScreen() {
    override val title = com.tangerine.core.source.R.string.home
    override val iconEnabled = Icons.Filled.Home
    override val iconDisabled = Icons.Outlined.Home

    override val route = "attractions"
    override val children = listOf(AttractionDetailsPage)
}

object AttractionDetailsPage : Destination() {
    const val attractionIdArg = "attraction_id"
    override val route = "${AttractionsPage.route}/{${attractionIdArg}}"

    override val arguments = listOf(
        navArgument(attractionIdArg) { type = NavType.IntType }
    )

    fun generateRouteFromId(id: Int) = "${AttractionsPage.route}/$id"
}

object BookmarksPage : MainScreen() {
    override val title = com.tangerine.core.source.R.string.bookmarks
    override val iconEnabled = Icons.Filled.Bookmarks
    override val iconDisabled = Icons.Outlined.Bookmarks

    override val route = "bookmarks"
}


// Screens to be displayed in the top RallyTabRow
val bottomTabScreens = listOf(AttractionsPage, BookmarksPage)
