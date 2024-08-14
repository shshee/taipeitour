package com.tangerine.taipeitour.compose

import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tangerine.core.database.datastore.DataStoreHolder
import com.tangerine.taipeitour.compose.attractions.AttractionsScreen
import com.tangerine.taipeitour.compose.attractions.details.AttractionDetailsScreen
import com.tangerine.taipeitour.compose.bookmarks.BookmarksScreen
import com.tangerine.taipeitour.compose.more.MoreScreen
import com.tangerine.taipeitour.compose.more.SettingsScreen
import com.tangerine.taipeitour.viewmodel.AttractionsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NavHost(
    navController: NavHostController,
    scrollState: LazyListState,
    isBottomBarHidden: Boolean,
    modifier: Modifier = Modifier
) {
    SharedTransitionLayout {
        val attractionsViewModel = koinViewModel<AttractionsViewModel>()

        NavHost(
            navController = navController,
            startDestination = bottomTabScreens.first().route,
            modifier = modifier
        ) {
            for (screen in bottomTabScreens) {
                composable(route = screen.route,
                    enterTransition = { slideInHorizontally() },
                    popEnterTransition = { slideInHorizontally() }
                ) {
                    BackHandler(true) {
                        // Or do nothing
                    }

                    when (screen) {
                        is AttractionsPage -> {
                            AttractionsScreen(
                                scrollState = scrollState,
                                onViewDetails = {
                                    navController.navigateSingleTopTo(
                                        AttractionDetailsPage.generateRouteFromId(
                                            it.id
                                        )
                                    )
                                },
                                isBottomBarHidden = isBottomBarHidden,
                                viewModel = attractionsViewModel,
                                sharedTransitionScope = this@SharedTransitionLayout,
                                animatedVisibilityScope = this
                            )
                        }

                        is BookmarksPage -> BookmarksScreen()

                        is MorePage -> MoreScreen {
                            navController.navigateSingleTopTo(it)
                        }
                    }
                }
            }

            composable(
                route = AttractionDetailsPage.route,
                arguments = AttractionDetailsPage.arguments
            ) { navBackStackEntry ->
                val id = navBackStackEntry.arguments?.getInt(AttractionDetailsPage.attractionIdArg)
                val uiState by attractionsViewModel.attractionUiState.collectAsState()

                AttractionDetailsScreen(
                    item = uiState.data.attractionsList.first { it.id == id },
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this
                )
            }

            for (screen in morePageScreens) {
                composable(route = screen.route) {
                    when (screen) {
                        is SettingsPage -> SettingsScreen {
                            attractionsViewModel.forceReload = true
                        }
                    }
                }
            }
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
//        popUpTo(
//            this@navigateSingleTopTo.graph.findStartDestination().id
//        ) {
//            saveState = true
//        }

        launchSingleTop = true
        restoreState = true
    }