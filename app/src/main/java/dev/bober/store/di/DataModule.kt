package dev.bober.store.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dev.bober.store.data.DataStoreManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "app_prefs"
)

val dataModule = module {
    single<DataStore<Preferences>> {
        androidContext().dataStore
    }

    single<DataStoreManager> {
        DataStoreManager(dataStore = get())
    }
}