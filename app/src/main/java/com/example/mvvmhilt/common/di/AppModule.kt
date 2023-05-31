package com.example.mvvmhilt.common.di

import android.content.Context
import androidx.room.Room
import com.example.mvvmhilt.BuildConfig
import com.example.mvvmhilt.common.utils.InternetStatus
import com.example.mvvmhilt.common.utils.InternetStatusImpl
import com.example.mvvmhilt.data.api.TMDBService
import com.example.mvvmhilt.data.api.UsersApi
import com.example.mvvmhilt.data.models.Config
import com.example.mvvmhilt.data.models.Constants
import com.example.mvvmhilt.data.repos.tvshow.ShowsRepositoryImpl
import com.example.mvvmhilt.data.repos.tvshow.datasource.TvShowCacheDataSource
import com.example.mvvmhilt.data.repos.tvshow.datasource.TvShowLocalDataSource
import com.example.mvvmhilt.data.repos.tvshow.datasource.TvShowRemoteDatasource
import com.example.mvvmhilt.data.repos.tvshow.datasourceImpl.TvShowCacheDataSourceImpl
import com.example.mvvmhilt.data.repos.tvshow.datasourceImpl.TvShowLocalDataSourceImpl
import com.example.mvvmhilt.data.repos.tvshow.datasourceImpl.TvShowRemoteDataSourceImpl
import com.example.mvvmhilt.data.repos.users.UsersRepoImpl
import com.example.mvvmhilt.data.room.Database
import com.example.mvvmhilt.data.room.TvShowDao
import com.example.mvvmhilt.data.room.UserDao
import com.example.mvvmhilt.domain.repos.ShowsRepo
import com.example.mvvmhilt.domain.repos.UsersRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * Provides local room database as a singleton
     */
    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        Database::class.java,
        "users-db"
    ).fallbackToDestructiveMigration().build()

    /**
     * Provides dao to be injected into repos
     */
    @Singleton
    @Provides
    fun provideDao(database: Database): UserDao = database.getDao()

    /**
     * Provides dao to be injected into repos
     */
    @Singleton
    @Provides
    fun provideShowsDao(database: Database): TvShowDao = database.getTvShowDao()

    @Singleton
    @Provides
    fun provideGithubService(okHttpClient: OkHttpClient): UsersApi {
        return Retrofit.Builder()
            .baseUrl(Constants.WEB_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(UsersApi::class.java)
    }

    @Singleton
    @Provides
    fun provideTmdbService(okHttpClient: OkHttpClient): TMDBService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(TMDBService::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
    } else {
        OkHttpClient.Builder().build()
    }

    /**
     * Provide class that is responsible for knowing internet status
     */
    @Provides
    @Singleton
    fun provideNetworkConnectivity(@ApplicationContext context: Context): InternetStatus {
        return InternetStatusImpl(context)
    }

    /**
     * Provide class that is responsible for knowing internet status
     */
    @Provides
    @Singleton
    fun provideSampleRepoInterface(apis: UsersApi, dao: UserDao): UsersRepo {
        return UsersRepoImpl(apis, dao)
    }

    @Provides
    @Singleton
    fun provideShowsRepo(showsRepo: ShowsRepositoryImpl): ShowsRepo = showsRepo

    @Provides
    @Singleton
    fun provideShowsLocalDataSource(localDataSource: TvShowLocalDataSourceImpl):
            TvShowLocalDataSource = localDataSource

    @Provides
    @Singleton
    fun provideShowsRemoteDataSource(
        remoteDatasource: TvShowRemoteDataSourceImpl
    ): TvShowRemoteDatasource = remoteDatasource

    @Provides
    @Singleton
    fun provideShowsCacheDataSource(
        cacheDatasource: TvShowCacheDataSourceImpl
    ): TvShowCacheDataSource = cacheDatasource

    @Provides
    @Singleton
    fun provideConfig(): Config = Config(
        BASE_URL = BuildConfig.BASE_URL,
        API_KEY = BuildConfig.API_KEY
    )
}