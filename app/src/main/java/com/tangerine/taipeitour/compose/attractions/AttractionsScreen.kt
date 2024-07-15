package com.tangerine.taipeitour.compose.attractions

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.tangerine.core.model.Attraction
import com.tangerine.core.model.Language
import com.tangerine.core.model.UiState
import com.tangerine.taipeitour.compose.others.LocalSnackbarHostState
import com.tangerine.taipeitour.compose.others.myPadding
import com.tangerine.taipeitour.views.attractions.AttractionsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AttractionsScreen(
    onViewDetails: (Int) -> Unit,
    viewModel: AttractionsViewModel = koinViewModel()
) {
    val uiState by viewModel.attractionUiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        HomeToolbar(title = Language.getLanguageFromCode(uiState.data.currentLang).appName) {
            viewModel.updateNewLang(
                it
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Attractions(
                onViewDetails = onViewDetails,
                list = uiState.data.attractionsList
            )

            when (uiState.state) {
                UiState.LOADING -> {
                    CircularProgressIndicator()
                }

                UiState.ERROR -> {
                    val snackbarHostState = LocalSnackbarHostState.current
                    val message = uiState.data.latestError?.message
                        ?: stringResource(id = com.tangerine.core.source.R.string.something_went_wrong)
                    val retry = stringResource(id = com.tangerine.core.source.R.string.retry)

                    LaunchedEffect(Unit) {
                        val result = snackbarHostState
                            .showSnackbar(
                                message = message,
                                actionLabel = retry,
                                duration = SnackbarDuration.Indefinite
                            )

                        when (result) {
                            SnackbarResult.ActionPerformed -> {
                                viewModel.getAttractions()
                            }

                            SnackbarResult.Dismissed -> {
                            }
                        }
                    }
                }

                else -> {}
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun AttractionsScreenPreview() {
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

    Column(modifier = Modifier.fillMaxSize()) {
        HomeToolbar(title = "test", {})
        Attractions(
            onViewDetails = {},
            list = list, modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
    }
}
