package com.tangerine.taipeitour.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tangerine.core.model.Attraction
import com.tangerine.core.model.Language
import com.tangerine.core.model.UiState
import com.tangerine.taipeitour.views.attractions.AttractionsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AttractionScreen(viewModel: AttractionsViewModel = koinViewModel()) {
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
            when (uiState.state) {
                UiState.LOADING -> {
                    CircularProgressIndicator()
                }

                UiState.SUCCESS -> {
                    Attractions(
                        list = uiState.data.attractionsList, paddings = myPadding()
                    )
                }

                UiState.ERROR -> {
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun AttractionScreenPreview() {
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
            list = list, paddings = myPadding(), modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
    }
}
