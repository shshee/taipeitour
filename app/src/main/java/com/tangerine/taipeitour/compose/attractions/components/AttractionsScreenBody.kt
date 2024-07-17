package com.tangerine.taipeitour.compose.attractions.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tangerine.core.model.Attraction
import com.tangerine.core.source.R
import com.tangerine.taipeitour.compose.others.myPadding


fun AttractionsScreenBody(
    scope: LazyListScope,
    listItems: List<Attraction>,
    onViewDetails: (Int) -> Unit
) {
    TrendingAttractions(scope = scope, listItems = listItems, onViewDetails = onViewDetails)
    MoreAttractions(scope = scope, listItems = listItems, onViewDetails = onViewDetails)
}


fun TrendingAttractions(
    scope: LazyListScope,
    listItems: List<Attraction>,
    onViewDetails: (Int) -> Unit
) {
    if (listItems.isNotEmpty()) scope.item {
        HomeScreenLabel(
            text = stringResource(
                id = R.string.trending
            )
        )

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
}


fun MoreAttractions(
    scope: LazyListScope,
    listItems: List<Attraction>,
    onViewDetails: (Int) -> Unit
) {
    if (listItems.isNotEmpty()) {
        scope.item {
            Spacer(modifier = Modifier.height(myPadding()))
            HomeScreenLabel(text = stringResource(id = R.string.more_tour))
        }

        scope.items(listItems) {
            MoreAttractionElement(
                onViewDetails = onViewDetails,
                attraction = it,
                modifier = Modifier.padding(
                    start = myPadding(),
                    end = myPadding()
                )
            )
        }
    }
}