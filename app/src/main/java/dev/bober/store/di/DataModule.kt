package dev.bober.store.di

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dev.bober.store.data.utils.DataStoreManager
import dev.bober.store.data.api.AppsApi
import dev.bober.store.data.api.AuthApi
import dev.bober.store.data.repository.AppsRepository
import dev.bober.store.data.repository.OnboardingRepository
import dev.bober.store.data.source.RemoteDataSource
import dev.bober.store.utils.Constants
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .cache(
                Cache(androidApplication().cacheDir, 10L * 1024 * 1024)
            )
            .addInterceptor(
                interceptor = HttpLoggingInterceptor { message ->
                    Log.d("API", "OkHttp: $message")
                }.apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setFieldNamingPolicy(
                        FieldNamingPolicy.IDENTITY
                    ).create()
                )
            )
            .client(get<OkHttpClient>())
            .build()
    }
    single<AppsApi> {
        get<Retrofit>().create(AppsApi::class.java)
    }

    single<AuthApi> {
        get<Retrofit>().create(AuthApi::class.java)
    }

    factoryOf(::RemoteDataSource)
    factoryOf(::AppsRepository)
    factoryOf(::OnboardingRepository)
}