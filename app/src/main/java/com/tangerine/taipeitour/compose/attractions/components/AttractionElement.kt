package com.tangerine.taipeitour.compose.attractions.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.tangerine.core.model.Attraction
import com.tangerine.taipeitour.compose.others.myPadding
import de.charlex.compose.material3.HtmlText

@Composable
fun MoreAttractionElement(
    onViewDetails: (Int) -> Unit,
    attraction: Attraction,
    modifier: Modifier = Modifier
) {
    val imageSize = 110.dp

    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(imageSize),
        elevation = CardDefaults.cardElevation(2.dp),
        onClick = { onViewDetails(attraction.id) }
    ) {
        Row {
            AttractionImage(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(imageSize), url = attraction.images.firstOrNull()?.src
            )

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = myPadding(), vertical = 5.dp)
            ) {
                HtmlText(
                    text = attraction.name,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                HtmlText(
                    text = attraction.introduction,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(com.tangerine.core.source.R.string.view_more),
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

        }
    }
}

@Composable
fun TrendAttractionElement(
    onViewDetails: (Int) -> Unit,
    attraction: Attraction,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .height(200.dp)
            .width(250.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        onClick = { onViewDetails(attraction.id) }
    ) {
        Box {
            AttractionImage(
                url = attraction.images.firstOrNull()?.src,
                modifier = Modifier.fillMaxSize()
            )

            HtmlText(
                text = attraction.name,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .background(
                        Color.Black.copy(alpha = 0.3f)
                    )
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .padding(myPadding())
            )
        }
    }
}

@Preview()
@Composable
fun MyPreview() {
    TrendAttractionElement(
        onViewDetails = {}, attraction = Attraction(
            id = 1,
            name = "Chùa Bà Đanh",
            introduction = "Nơi văng vẻ nhất việt nam",
            address = "Hồ Chí Minh City",
            url = "google.com",
            images = listOf()
        )
    )
}

@Composable
fun AttractionImage(modifier: Modifier = Modifier, url: String?) {
    Box(modifier = Modifier.background(MaterialTheme.colorScheme.inversePrimary)) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(url)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
        ) {
            val state = painter.state
            if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                Box(contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                SubcomposeAsyncImageContent()
            }
        }
    }
}