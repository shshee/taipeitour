package com.tangerine.taipeitour.compose.attractions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.tangerine.core.model.Attraction
import com.tangerine.core.model.AttractionsUiState
import com.tangerine.core.model.Language
import com.tangerine.core.model.UiState
import com.tangerine.core.source.R
import com.tangerine.taipeitour.compose.attractions.components.AttractionsScreenBody
import com.tangerine.taipeitour.compose.attractions.components.MoreAttractionElement
import com.tangerine.taipeitour.compose.attractions.components.AttractionsScreenHead
import com.tangerine.taipeitour.compose.attractions.components.HomeScreenLabel
import com.tangerine.taipeitour.compose.attractions.components.TrendAttractionElement
import com.tangerine.taipeitour.compose.attractions.components.TrendingAttractions
import com.tangerine.taipeitour.compose.others.LocalSnackbarHostState
import com.tangerine.taipeitour.compose.others.myPadding
import com.tangerine.taipeitour.views.attractions.AttractionsViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttractionsScreen(
    onViewDetails: (Int) -> Unit,
    viewModel: AttractionsViewModel = koinViewModel()
) {
    val uiState by viewModel.attractionUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val swipeRefreshState = rememberSwipeRefreshState(uiState.state == UiState.LOADING)

    if (uiState.state == UiState.ERROR) {
        val snackbarHostState = LocalSnackbarHostState.current
        val message = uiState.handleError()
            ?: stringResource(id = R.string.something_went_wrong)
        val retry = stringResource(id = R.string.retry)

        LaunchedEffect(Unit) {
            val result = snackbarHostState
                .showSnackbar(
                    message = message,
                    //actionLabel = retry,
                    duration = SnackbarDuration.Short
                )

//                        when (result) {
//                            SnackbarResult.ActionPerformed -> {
//                                viewModel.getAttractions()
//                            }
//
//                            SnackbarResult.Dismissed -> {
//                            }
//                        }
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AttractionsScreenHead(
                scrollBehavior = scrollBehavior,
                title = Language.getLanguageFromCode(uiState.data.currentLang).appName,
                updateLanguage = {
                    viewModel.updateNewLang(
                        it
                    )
                })
        }
    ) {
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = {
                viewModel.getAttractions()
            },
            indicator = { state, trigger ->
                SwipeRefreshIndicator(
                    state = state,
                    refreshTriggerDistance = trigger,
                    scale = true,
                    contentColor = MaterialTheme.colorScheme.primary
                )
            }, modifier = Modifier.padding(it)
        ) {
            AttractionsScreenBody(
                listItems = uiState.data.attractionsList,
                onViewDetails = onViewDetails
            )
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AttractionElementPreview() {
    val item = Attraction(
        id = 1,
        name = "Chùa Bà Đanh",
        introduction = "Nơi văng vẻ nhất việt nam",
        address = "Hồ Chí Minh City",
        url = "google.com",
        images = listOf(Attraction.Image(src = "https://statics.vinwonders.com/chua-ba-danh-ha-nam-1_1629214003.jpg"))
    )

    val list = mutableListOf(item)
    repeat(4) {
        list.add(item)
    }

    //Attractions(list = list, onViewDetails = {})
}
