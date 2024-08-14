package com.tangerine.taipeitour.compose.more

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.tangerine.core.database.datastore.DataStoreHolder
import com.tangerine.core.model.Language
import com.tangerine.taipeitour.compose.others.myPadding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun SettingsScreen(updatedLanguage: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = myPadding() * 2, horizontal = myPadding())
    ) {
        ExposedDropdownMenuSample(updatedLanguage)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownMenuSample(updatedLanguage: () -> Unit) {
    val dataStore = koinInject<DataStoreHolder>()
    var expanded by remember { mutableStateOf(false) }
    var lang by remember { mutableStateOf(Language.TAIWAN) }

    LaunchedEffect(dataStore) {
        lang = Language.getLanguageFromOrdinal(dataStore.getValue(DataStoreHolder.langKey).first())
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        val scope = rememberCoroutineScope()
        OutlinedTextField(
            modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable).fillMaxWidth(),
            value = stringResource(id = lang.title),
            onValueChange = {},
            readOnly = true,
            singleLine = true,
            label = { Text(text = stringResource(id = com.tangerine.core.source.R.string.language), style = MaterialTheme.typography.bodyMedium) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            Language.entries.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = stringResource(id = option.title)) },
                    onClick = {
                        lang = option
                        scope.launch {
                            dataStore.setValue(DataStoreHolder.langKey, option.ordinal)
                        }
                        updatedLanguage.invoke()

                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}