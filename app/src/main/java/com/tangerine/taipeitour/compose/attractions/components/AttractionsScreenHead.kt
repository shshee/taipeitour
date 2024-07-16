package com.tangerine.taipeitour.compose.attractions.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.tangerine.core.model.Language
import com.tangerine.core.source.R
import com.tangerine.taipeitour.compose.others.myPadding

@Composable
fun AttractionsScreenHead(
    title: String,
    updateLanguage: (Language) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
        )
        LanguagesOptions(
            updateLanguage = updateLanguage,
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}

@Composable
fun LanguagesOptions(updateLanguage: (Language) -> Unit, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = Icons.Default.Translate,
                contentDescription = "More",
                tint = MaterialTheme.colorScheme.primary
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
}

@Composable
fun HomeScreenLabel(text: String) {
    Text(
        text = text, modifier = Modifier.padding(
            horizontal = myPadding()
        ), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold
    )
}