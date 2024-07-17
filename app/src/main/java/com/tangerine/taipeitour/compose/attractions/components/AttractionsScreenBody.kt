package com.tangerine.taipeitour.compose.attractions.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tangerine.core.model.Attraction
import com.tangerine.core.source.R
import com.tangerine.taipeitour.compose.others.myPadding


@Composable
fun AttractionsScreenBody(
    listItems: List<Attraction>,
    onViewDetails: (Int) -> Unit
) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(myPadding())) {
        if (listItems.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(myPadding()))
                HomeScreenLabel(
                    text = stringResource(
                        id = R.string.trending
                    )
                )
            }

            item {
                TrendingAttractions(listItems = listItems, onViewDetails = onViewDetails)
            }
        }

        if (listItems.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(myPadding()*3))
                HomeScreenLabel(text = stringResource(id = R.string.more_tour))
                Spacer(modifier = Modifier.height(myPadding()))
            }

            MoreAttractions(scope = this, listItems = listItems, onViewDetails = onViewDetails)
        }

        item {
            Spacer(modifier = Modifier.height(myPadding()))
        }
    }


}


@Composable
fun TrendingAttractions(
    listItems: List<Attraction>,
    onViewDetails: (Int) -> Unit
) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(1), modifier = Modifier
            .height(210.dp)
            .padding(horizontal = myPadding()),
        horizontalArrangement = Arrangement.spacedBy(myPadding())
    ) {
        items(listItems) {
            TrendAttractionElement(onViewDetails = onViewDetails, attraction = it)
        }
    }
}


fun MoreAttractions(
    scope: LazyListScope,
    listItems: List<Attraction>,
    onViewDetails: (Int) -> Unit
) {
    scope.items(listItems) {
        MoreAttractionElement(
            onViewDetails = onViewDetails,
            attraction = it,
            modifier = Modifier.padding(
                horizontal = myPadding()
            )
        )
    }
}

@Composable
fun HomeScreenLabel(text: String) {
    Text(
        text = text, modifier = Modifier.padding(
            horizontal = myPadding()
        ), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold
    )
}