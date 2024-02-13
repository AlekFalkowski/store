package dev.falkow.blanco.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.falkow.blanco.layout.resources.INavDrawerContentData
import dev.falkow.blanco.layout.resources.NavDrawerContentData
import dev.falkow.blanco.layout.storages.AppDatabase
import dev.falkow.blanco.layout.storages.IAppDevNetworkStorage
import dev.falkow.blanco.layout.storages.IAppNetworkStorage
import dev.falkow.blanco.nodes.catalog.storages.ICatalogDevRemoteStorage
import dev.falkow.blanco.nodes.catalog.storages.ICatalogRemoteStorage
import dev.falkow.blanco.nodes.catalog_product.storages.CatalogProductRemoteStorage
import dev.falkow.blanco.nodes.order.storages.IOrderRemoteStorage
import dev.falkow.blanco.shared.config.PROJECT_URI_BASE
import dev.falkow.blanco.shared.storages.IKeyValueLocalStorage
import dev.falkow.blanco.shared.storages.ISharedDevRemoteStorage
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NetworkSource

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DevNetworkSource

@Module
@InstallIn(SingletonComponent::class)
object AppProvidingModule {

    /** App Resources. */

    @Provides
    fun provideNavDrawerContentData(): INavDrawerContentData = NavDrawerContentData


    /** App Database. */

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(
            context = appContext,
            klass = AppDatabase::class.java,
            name = "local-database"
        ).build()

    @Provides
    @Singleton
    fun provideSettingsLocalDataService(database: AppDatabase): IKeyValueLocalStorage =
        database.keyValueLocalStorage()


    /** App Network Source. */

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            // this.level = HttpLoggingInterceptor.Level.BASIC
            this.level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            // .addInterceptor(
            //     Interceptor { chain ->
            //         val url = chain.request()
            //             .url
            //             .newBuilder()
            //             // .addQueryParameter("api_key", ALONE_API)
            //             // .addQueryParameter("with_genres", "$codeForActionOrComedy")
            //             .build()
            //
            //         val request = chain.request()
            //             .newBuilder()
            //             .url(
            //                 url.toString()
            //                     .replace("%26", "&")
            //                     .replace("%3D", "=")
            //             )
            //             .build()
            //         return@Interceptor chain.proceed(request)
            //     }
            // )
            .addInterceptor(
                Interceptor { chain ->
                    val newRequest = chain.request()
                        .newBuilder()
                        .url(
                            chain.request().url.toString()
                                .replace("%26", "&")
                                .replace("%3D", "=")
                                .replace("%2F", "/")
                        )
                        .build()
                    return@Interceptor chain.proceed(newRequest)
                }
            )
            .addNetworkInterceptor(httpLoggingInterceptor)
            .build()

    private val json = Json { ignoreUnknownKeys = true }

    @Provides
    @Singleton
    @NetworkSource
    fun provideAppNetworkSource(okHttpClient: OkHttpClient): IAppNetworkStorage =
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(PROJECT_URI_BASE)
            .build()
            .create(IAppNetworkStorage::class.java)

    @Provides
    @Singleton
    @DevNetworkSource
    fun provideDevNetworkSource(okHttpClient: OkHttpClient): IAppDevNetworkStorage =
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .baseUrl("https://falkow.dev/blanco/")
            .build()
            .create(IAppDevNetworkStorage::class.java)

    @Provides
    @Singleton
    fun provideCatalogRemoteSource(@NetworkSource networkSource: IAppNetworkStorage): ICatalogRemoteStorage =
        networkSource

    @Provides
    @Singleton
    fun provideCatalogDevRemoteSource(@DevNetworkSource devNetworkSource: IAppDevNetworkStorage): ICatalogDevRemoteStorage =
        devNetworkSource

    @Provides
    @Singleton
    fun provideCatalogProductRemoteSource(@NetworkSource networkSource: IAppNetworkStorage): CatalogProductRemoteStorage =
        networkSource

    @Provides
    @Singleton
    fun provideOrderRemoteSource(@NetworkSource networkSource: IAppNetworkStorage): IOrderRemoteStorage =
        networkSource

    @Provides
    @Singleton
    fun provideProjectLibraryDevRemoteStorage(@DevNetworkSource devNetworkSource: IAppDevNetworkStorage): ISharedDevRemoteStorage =
        devNetworkSource


    /** Firebase Services. */

    @Provides
    @Singleton
    fun provideFirebaseAnalytics(): FirebaseAnalytics =
        Firebase.analytics

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth =
        Firebase.auth

    @Provides
    @Singleton
    fun provideFirebaseDynamicLinks(): FirebaseDynamicLinks =
        Firebase.dynamicLinks
}