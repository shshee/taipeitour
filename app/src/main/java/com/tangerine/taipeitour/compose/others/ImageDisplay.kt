package com.tangerine.taipeitour.compose.others

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer

@Composable
fun ImageDisplay(
    modifier: Modifier = Modifier,
    url: String?,
    contentScale: ContentScale = ContentScale.Crop
) {
    val showPlaceHolder = rememberSaveable { mutableStateOf(true) }

    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .build(),
        contentDescription = null,
        contentScale = contentScale,
        modifier = modifier
            .placeholder(
                visible = showPlaceHolder.value,
                color = Color.Gray,
                highlight = PlaceholderHighlight.shimmer(highlightColor = Color.White)
            )
    ) {
        val state = painter.state
        val completed =
            !(state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error)
        if (completed) {
            showPlaceHolder.value = false
            SubcomposeAsyncImageContent()
        }
    }
}