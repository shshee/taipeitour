package com.tangerine.taipeitour.compose.bookmarks

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.tangerine.core.database.repo.AttractionsLocalRepoImpl
import org.koin.compose.koinInject

@Composable
fun BookmarksScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        val data = koinInject<AttractionsLocalRepoImpl>()
        val test = data.getAllSavedAttractions().collectAsState(initial = listOf())

        LazyColumn {
            items(test.value) {
                Text(text = it?.name.toString(),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold)
            }
        }
    }
}