package com.tangerine.taipeitour.compose.bookmarks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tangerine.core.database.repo.AttractionsLocalRepoImpl
import com.tangerine.taipeitour.compose.others.ImageDisplay
import com.tangerine.taipeitour.compose.others.myPadding
import org.koin.compose.koinInject

@Composable
fun BookmarksScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        val data = koinInject<AttractionsLocalRepoImpl>()
        val test = data.listenToAllSavedAttractions().collectAsStateWithLifecycle(emptyList())

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(2.dp),
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            items(test.value) {
                ImageDisplay(
                    modifier = Modifier
                        .height(150.dp),
                    url = it?.images?.firstOrNull()?.src,
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}