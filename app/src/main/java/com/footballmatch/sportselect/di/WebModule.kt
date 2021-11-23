package com.footballmatch.sportselect.di

import android.content.Context
import android.net.ConnectivityManager
import com.footballmatch.sportselect.BuildConfig
import com.footballmatch.sportselect.web.service.InternetConnectionService
import com.footballmatch.sportselect.web.service.InternetConnectionServiceImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.footballmatch.sportselect.data.web.ball_interception.BallInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WebModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    @Provides
    @Singleton
    fun connectivityManager(@ApplicationContext context: Context) =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @Provides
    @Singleton
    fun internetConnectionService(connectivityManager: ConnectivityManager): InternetConnectionService =
        InternetConnectionServiceImpl(connectivityManager)

    @Provides
    @Singleton
    fun internetConnectionInterceptor(internetConnectionService: InternetConnectionService) =
        BallInterceptor(internetConnectionService)

    @Provides
    @Singleton
    fun footballClient(internetInterceptor: BallInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.readTimeout(100,TimeUnit.SECONDS )
        builder.writeTimeout(100,TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }
        builder.addInterceptor(internetInterceptor)
        return builder.build()
    }
}