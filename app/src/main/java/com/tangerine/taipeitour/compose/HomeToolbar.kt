package com.tangerine.taipeitour.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.tangerine.core.model.Language
import com.tangerine.taipeitour.R

@Composable
fun HomeToolbar(title: String, updateLanguage: (Language) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .height(dimensionResource(id = com.tangerine.core.source.R.dimen.toolbar_height))
    ) {
        Text(
            text = title, style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(
                horizontal = myPadding()
            ), color = MaterialTheme.colorScheme.onSecondary
        )
        Spacer(modifier = Modifier.weight(1f))
        Languages(updateLanguage = updateLanguage)
    }
}

@Composable
fun Languages(updateLanguage: (Language) -> Unit, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_translate_12sdp),
                contentDescription = "More",
                tint = MaterialTheme.colorScheme.onSecondary
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

@Preview
@Composable
fun MyPreview() {
    HomeToolbar(title = "Test",{})
}