package com.tangerine.taipeitour.compose.more

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.tangerine.taipeitour.compose.MainScreen
import com.tangerine.taipeitour.compose.morePageScreens
import com.tangerine.taipeitour.compose.others.myPadding


@Composable
fun MoreScreen(onOptionSelected: (String) -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        LazyColumn {
            items(morePageScreens) { screen ->
                Card(
                    modifier = Modifier
                        .padding(myPadding())
                        .fillMaxSize(),
                    onClick = { onOptionSelected(screen.route) }
                ) {
                    MoreElement(item = screen)
                }
            }
        }
    }
}

@Composable
fun MoreElement(item: MainScreen) {
    Row(
        modifier = Modifier
            .padding(myPadding())
            .fillMaxSize(), horizontalArrangement = Arrangement.spacedBy(myPadding())
    ) {
        Icon(
            imageVector = item.iconEnabled,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = stringResource(id = item.title),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleMedium
        )
    }
}