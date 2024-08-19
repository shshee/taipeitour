package com.tangerine.taipeitour.compose.attractions.details

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.tangerine.core.model.Attraction
import com.tangerine.core.model.SharedTransitionTag
import com.tangerine.taipeitour.compose.others.ImageDisplay
import com.tangerine.taipeitour.compose.others.myPadding
import de.charlex.compose.material3.HtmlText
import kotlin.math.absoluteValue

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AttractionDetailsScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    item: Attraction
) {
//    LaunchedEffect(pagerState) {
//        // Collect from the a snapshotFlow reading the currentPage
//        snapshotFlow { pagerState.currentPage }.collect { page ->
//            //pagerState.animateScrollToPage((page + 1) % item.images.size)
//        }
//    }
    val minImageSize = 0F
    val maxImageSize = 300.dp.value
    var currentImageSize by remember { mutableFloatStateOf(maxImageSize) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y / 4

                val newImageSize = currentImageSize + delta
                val previousImageSize = currentImageSize

                currentImageSize = newImageSize.coerceIn(minImageSize, maxImageSize)
                val consumed = currentImageSize - previousImageSize

                return Offset(0f, consumed)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
    ) {
        AttractionImages(
            item = item,
            animatedVisibilityScope = animatedVisibilityScope,
            sharedTransitionScope = sharedTransitionScope,
            modifier = Modifier.height(currentImageSize.dp)
        )

        AttractionInfo(
            item = item,
            animatedVisibilityScope = animatedVisibilityScope,
            sharedTransitionScope = sharedTransitionScope,
            modifier = Modifier.padding(myPadding())
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AttractionImages(
    item: Attraction,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope,
    modifier: Modifier = Modifier
) {
    if (item.images.isNotEmpty()) with(sharedTransitionScope) {
        val pagerState = rememberPagerState {
            item.images.size
        }

        HorizontalPager(
            state = pagerState,
            modifier = modifier
                .sharedBounds(
                    rememberSharedContentState(key = "${SharedTransitionTag.images}/${item.id}"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    enter = fadeIn(),
                    exit = fadeOut(),
                    resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds()
                )
        ) { page ->
            ImageDisplay(
                contentScale = ContentScale.Fit,
                url = item.images[pagerState.currentPage].src,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
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
    } else Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Black)
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AttractionInfo(
    item: Attraction,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope,
    modifier: Modifier = Modifier
) {
    val test = StringBuilder()
    repeat(1) {
        test.append(item.introduction)
    }

    with(sharedTransitionScope) {
        Column(
            modifier = modifier.verticalScroll(rememberScrollState())
        ) {
            HtmlText(
                text = item.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.sharedBounds(
                    rememberSharedContentState(key = "${SharedTransitionTag.title}/${item.id}"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    enter = fadeIn(),
                    exit = fadeOut(),
                    resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds()
                )
            )

            HtmlText(
                text = test.toString(),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Justify,
                modifier = Modifier
                    .padding(vertical = myPadding())
                    .sharedBounds(
                        rememberSharedContentState(key = "${SharedTransitionTag.description}/${item.id}"),
                        animatedVisibilityScope = animatedVisibilityScope,
                        enter = fadeIn(),
                        exit = fadeOut(),
                        resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds()
                    )
            )
        }
    }
}