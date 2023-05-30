package com.example.mvvmhilt.common.di

import android.content.Context
import androidx.room.Room
import com.example.mvvmhilt.BuildConfig
import com.example.mvvmhilt.data.api.UsersApi
import com.example.mvvmhilt.data.models.Constants
import com.example.mvvmhilt.data.room.Database
import com.example.mvvmhilt.data.room.UserDao
import com.example.mvvmhilt.domain.repos.UsersRepo
import com.example.mvvmhilt.data.repos.UsersRepoImpl
import com.example.mvvmhilt.common.utils.InternetStatus
import com.example.mvvmhilt.common.utils.InternetStatusImpl
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

}