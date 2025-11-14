package dev.bober.store.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(
    private val dataStore: DataStore<Preferences>
) {
    suspend fun saveBoolean(key: String, value: Boolean) {
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(key)] = value
        }
    }

    suspend fun saveString(key: String, value: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(key)] = value
        }
    }

    suspend fun saveInt(key: String, value: Int) {
        dataStore.edit { preferences ->
            preferences[intPreferencesKey(key)] = value
        }
    }

    fun getBooleanFlow(key: String): Flow<Boolean?> = dataStore.data
        .map { preferences ->
            Log.i("dataStore", preferences.toString())
            preferences[booleanPreferencesKey(key)]
        }

    fun getStringFlow(key: String): Flow<String?> =
        dataStore.data
            .map { preferences ->
                preferences[stringPreferencesKey(key)]
            }

    fun getIntFLow(key: String): Flow<Int?> = dataStore.data
        .map { preferences ->
            preferences[intPreferencesKey(key)]
        }
}