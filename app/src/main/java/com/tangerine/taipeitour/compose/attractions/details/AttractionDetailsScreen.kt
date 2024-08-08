package com.tangerine.taipeitour.compose.attractions.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.tangerine.core.model.Attraction
import com.tangerine.taipeitour.compose.attractions.components.AttractionImage
import kotlin.math.absoluteValue

@Composable
fun AttractionDetailsScreen(item: Attraction) {
    val pagerState = rememberPagerState {
        item.images.size
    }

    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState
        ) { page ->
            AttractionImage(
                contentScale = ContentScale.Fit,
                url = item.images[pagerState.currentPage].src,
                modifier = Modifier
                    .fillMaxWidth().height(300.dp).background(MaterialTheme.colorScheme.onSecondary)
                    .graphicsLayer {
                        val pageOffset = (
                                (pagerState.currentPage - page) + pagerState
                                    .currentPageOffsetFraction
                                ).absoluteValue

                        lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).let { stat ->
                            scaleX = stat
                            scaleY = stat
                        }
                    }
            )
        }
    }
}