package com.tangerine.taipeitour.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tangerine.core.model.Attraction
import com.tangerine.taipeitour.R

@Composable
fun myPadding() = dimensionResource(id = com.tangerine.core.source.R.dimen.standard_padding)

@Composable
fun AttractionElement(attraction: Attraction, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        Row(modifier = Modifier.padding(myPadding())) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(attraction.images.first().src)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.taipei),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxHeight()
                    .width(100.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = myPadding())
            ) {
                Text(text = attraction.name, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = attraction.introduction,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 3,
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
fun Attractions(list: MutableList<Attraction>, paddings: Dp, modifier: Modifier=Modifier) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(paddings),
        modifier = modifier.padding(paddings)
    ) {
        items(list) {
            AttractionElement(
                attraction = it
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

    Attractions(list = list, paddings = myPadding())
}