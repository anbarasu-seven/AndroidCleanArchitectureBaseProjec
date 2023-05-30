package com.example.mvvmhilt.di

import android.content.Context
import androidx.room.Room
import com.example.mvvmhilt.BuildConfig
import com.example.mvvmhilt.data.api.ModuleSpecificApis
import com.example.mvvmhilt.data.models.Constants
import com.example.mvvmhilt.data.room.Database
import com.example.mvvmhilt.data.room.SampleDao
import com.example.mvvmhilt.repos.SampleRepo
import com.example.mvvmhilt.repos.SampleRepoImpl
import com.example.mvvmhilt.utils.InternetStatus
import com.example.mvvmhilt.utils.InternetStatusImpl
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
        "SampleDatabase"
    ).fallbackToDestructiveMigration().build()

    /**
     * Provides dao to be injected into repos
     */
    @Singleton
    @Provides
    fun provideDao(database: Database): SampleDao = database.getDao()

    @Singleton
    @Provides
    fun provideGithubService(okHttpClient: OkHttpClient): ModuleSpecificApis {
        return Retrofit.Builder()
            .baseUrl(Constants.WEB_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ModuleSpecificApis::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient
            .Builder()
            .build()
    }

    @Provides
    @Singleton
    fun provideNetworkConnectivity(@ApplicationContext context: Context): InternetStatus {
        return InternetStatusImpl(context)
    }

    @Provides
    @Singleton
    fun provideSampleRepoInterface(apis: ModuleSpecificApis, dao: SampleDao): SampleRepo {
        return SampleRepoImpl(apis, dao)
    }

}