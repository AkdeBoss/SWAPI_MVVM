package com.happinessinc.getshwifty.di

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.happinessinc.getshwifty.App
import com.happinessinc.getshwifty.repo.LaunchRepo
import com.happinessinc.getshwifty.repo.api.LaunchEventsApiDataSource
import com.happinessinc.getshwifty.repo.api.SpaceXApiService
import com.happinessinc.getshwifty.repo.db.AppDatabase
import com.happinessinc.getshwifty.repo.db.LaunchEventDao
import dagger.Module
import dagger.Provides
import dagger.hilt.DefineComponent
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.modules.ApplicationContextModule
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val okClient = OkHttpClient().newBuilder()
        .addNetworkInterceptor(StethoInterceptor())
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .client(okClient)
        .baseUrl("https://api.spacexdata.com/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideApiService(retrofit: Retrofit): SpaceXApiService = retrofit.create(SpaceXApiService::class.java)

    @Singleton
    @Provides
    fun provideRemoteDataSource(spaceXApiService: SpaceXApiService) = LaunchEventsApiDataSource(spaceXApiService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideLaunchEventDao(db: AppDatabase) = db.launchEventDao()

    @Singleton
    @Provides
    fun provideRepo(remoteDataSource: LaunchEventsApiDataSource,
                          localDataSource: LaunchEventDao) =
        LaunchRepo(remoteDataSource, localDataSource)
}