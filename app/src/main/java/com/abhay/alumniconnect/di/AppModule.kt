package com.abhay.alumniconnect.di

import android.content.Context
import com.abhay.alumniconnect.BuildConfig
import com.abhay.alumniconnect.data.remote.AlumniApi
import com.abhay.alumniconnect.data.remote.RequestInterceptor
import com.abhay.alumniconnect.data.repository.SessionManager
import com.abhay.alumniconnect.data.repository.SessionManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAlumniApi(sessionManager: SessionManager): AlumniApi {

        val BASE_URL = "http://${BuildConfig.LOCALHOST_IP_ADDRESS}:5000/api/"

        val interceptor = RequestInterceptor(sessionManager)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create<AlumniApi>(AlumniApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSessionManager(@ApplicationContext context: Context): SessionManager {
        return SessionManagerImpl(context)
    }

}