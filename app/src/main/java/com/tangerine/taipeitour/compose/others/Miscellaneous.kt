package com.tangerine.taipeitour.compose.others

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.tangerine.core.source.R

@Composable
fun myPadding() = dimensionResource(id = R.dimen.standard_compose_padding)

object Route {
    const val attractions = "attractions"

    const val attractionIdArg = "attraction_id"
    const val attractionDetails = "${attractions}/{${attractionIdArg}}"
}