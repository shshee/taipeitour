package com.tangerine.taipeitour.compose

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.viewpager2.widget.ViewPager2.ScrollState
import com.tangerine.taipeitour.compose.attractions.AttractionsScreen
import com.tangerine.taipeitour.compose.attractions.details.AttractionDetailsScreen
import com.tangerine.taipeitour.compose.bookmarks.BookmarksScreen
import com.tangerine.taipeitour.views.attractions.AttractionsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavHost(
    navController: NavHostController,
    scrollState: LazyListState,
    modifier: Modifier = Modifier
) {
    val attractionsViewModel = koinViewModel<AttractionsViewModel>()

    NavHost(
        navController = navController,
        startDestination = bottomTabScreens.first().route,
        modifier = modifier
    ) {
        for (screen in bottomTabScreens) {
            composable(route = screen.route) {
                when (screen) {
                    is AttractionsPage -> {
                        AttractionsScreen(scrollState = scrollState, onViewDetails = {
                            navController.navigateSingleTopTo(
                                AttractionDetailsPage.generateRouteFromId(
                                    it
                                )
                            )
                        }, viewModel = attractionsViewModel)
                    }

                    is BookmarksPage -> BookmarksScreen()
                }
            }
        }

        composable(
            route = AttractionDetailsPage.route,
            arguments = AttractionDetailsPage.arguments,
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getInt(AttractionDetailsPage.attractionIdArg)
            AttractionDetailsScreen(item = attractionsViewModel.attractionUiState.value.data.attractionsList.first { it.id == id })
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }