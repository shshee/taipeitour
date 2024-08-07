package com.tangerine.taipeitour.compose.attractions.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tangerine.core.model.Language

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttractionsScreenHead(
    scrollBehavior: TopAppBarScrollBehavior,
    title: String,
    updateLanguage: (Language) -> Unit
) {
    val primaryColor = MaterialTheme.colorScheme.primary

    Surface(shadowElevation = 2.dp) {
        CenterAlignedTopAppBar(
            colors = TopAppBarColors(
                containerColor = MaterialTheme.colorScheme.onPrimary,
                scrolledContainerColor = MaterialTheme.colorScheme.onPrimary,
                navigationIconContentColor = primaryColor,
                titleContentColor = primaryColor,
                actionIconContentColor = primaryColor
            ),
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )
            }, actions = {
                LanguagesOptions(updateLanguage = updateLanguage)
            },
            scrollBehavior = scrollBehavior
        )
    }
}

@Composable
fun LanguagesOptions(updateLanguage: (Language) -> Unit, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = !expanded }) {
        Icon(
            imageVector = Icons.Default.Translate,
            contentDescription = "More"
        )
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        Language.values().forEach {
            DropdownMenuItem(
                text = { Text(text = stringResource(id = it.title)) },
                onClick = {
                    updateLanguage(it)
                    expanded = false
                }
            )
        }
    }
}