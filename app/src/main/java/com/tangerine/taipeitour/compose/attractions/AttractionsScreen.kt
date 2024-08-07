package com.tangerine.taipeitour.compose.attractions

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.tangerine.core.model.Attraction
import com.tangerine.core.model.Language
import com.tangerine.core.model.UiState
import com.tangerine.core.source.R
import com.tangerine.taipeitour.compose.attractions.components.AttractionsScreenBody
import com.tangerine.taipeitour.compose.attractions.components.AttractionsScreenHead
import com.tangerine.taipeitour.compose.others.LocalSnackbarHostState
import com.tangerine.taipeitour.viewmodel.AttractionsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttractionsScreen(
    scrollState: LazyListState,
    isBottomBarHidden: Boolean,
    onViewDetails: (Attraction) -> Unit,
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
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = isBottomBarHidden,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                val scope = rememberCoroutineScope()
                IconButton(
                    onClick = {
                        scope.launch { scrollState.animateScrollToItem(0) }
                    }, modifier = Modifier.clip(CircleShape),
                    colors = IconButtonColors(
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        containerColor = MaterialTheme.colorScheme.primary,
                        disabledContentColor = MaterialTheme.colorScheme.inverseOnSurface,
                        disabledContainerColor = MaterialTheme.colorScheme.inversePrimary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowUpward,
                        contentDescription = null
                    )
                }
            }
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
            //Only show loading when its content make it to the composition
            //so we have to wrap this screen to a place holder in order to show loading while waiting for it
            Surface(modifier = Modifier.fillMaxSize()) {
                AttractionsScreenBody(
                    scrollState = scrollState,
                    listItems = uiState.data.attractionsList,
                    onModifyBookmark = { id, isSaved ->
                        viewModel.modifyBookmark(id, isSaved)
                    },
                    onViewDetails = onViewDetails
                )
            }
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
