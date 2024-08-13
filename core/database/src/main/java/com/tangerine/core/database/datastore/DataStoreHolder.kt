package com.tangerine.core.database.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreHolder(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settingPrefs")
        val langKey = intPreferencesKey("language")
    }

    fun <T> getValue(keyName: Preferences.Key<T>): Flow<T?> = context.dataStore.data.map {
        it[keyName]
    }

    suspend fun <T> setValue(keyName: Preferences.Key<T>, value: T) {
        context.dataStore.edit { it[keyName] = value }
    }
}