package com.tangerine.taipeitour.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tangerine.taipeitour.compose.attractions.AttractionScreen
import com.tangerine.taipeitour.compose.attractions.details.AttractionDetailsScreen
import com.tangerine.taipeitour.compose.others.Route
import com.tangerine.taipeitour.views.attractions.AttractionsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val attractionsViewModel = koinViewModel<AttractionsViewModel>()

    NavHost(
        navController = navController,
        startDestination = Route.attractions,
        modifier = modifier
    ) {
        composable(route = Route.attractions) {
            AttractionScreen(onViewDetails = {
                navController.navigateSingleTopTo("${Route.attractions}/$it")
            }, viewModel = attractionsViewModel)
        }

        composable(
            route = Route.attractionDetails,
            arguments = listOf(
                navArgument(Route.attractionIdArg) { type = NavType.IntType }
            ),
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getInt(Route.attractionIdArg)
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