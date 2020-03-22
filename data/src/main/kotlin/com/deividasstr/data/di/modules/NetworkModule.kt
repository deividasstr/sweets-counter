package com.deividasstr.data.di.modules

import android.content.Context
import com.deividasstr.data.networking.apis.FactsApi
import com.deividasstr.data.networking.apis.SweetsApi
import com.deividasstr.data.networking.manager.NetworkManager
import com.deividasstr.data.networking.services.FactsService
import com.deividasstr.data.networking.services.SweetsService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class NetworkModule(private val baseUrl: String) {

    @Singleton
    @Provides
    fun provideInterceptors(): ArrayList<Interceptor> {

        val interceptors = arrayListOf<Interceptor>()

        val loggingInterceptorBasic = HttpLoggingInterceptor()
        loggingInterceptorBasic.level = HttpLoggingInterceptor.Level.BASIC

        val loggingInterceptorBody = HttpLoggingInterceptor()
        loggingInterceptorBody.level = HttpLoggingInterceptor.Level.BODY

        interceptors.add(loggingInterceptorBasic)
        interceptors.add(loggingInterceptorBody)

        return interceptors
    }

    @Singleton
    @Provides
    fun provideRetrofit(interceptors: ArrayList<Interceptor>): Retrofit {
        val clientBuilder = OkHttpClient.Builder()
        if (!interceptors.isEmpty()) {
            interceptors.forEach { interceptor ->
                clientBuilder.addInterceptor(interceptor)
            }
        }

        return Retrofit.Builder()
            .client(clientBuilder.build())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
    }

    @Singleton
    @Provides
    fun provideSweetsService(retrofit: Retrofit, networkManager: NetworkManager): SweetsService {
        return SweetsService(retrofit.create(SweetsApi::class.java), networkManager)
    }

    @Singleton
    @Provides
    fun provideFactsService(retrofit: Retrofit, networkManager: NetworkManager): FactsService {
        return FactsService(retrofit.create(FactsApi::class.java), networkManager)
    }

    @Singleton
    @Provides
    fun provideNetworkManager(context: Context): NetworkManager {
        return NetworkManager(context)
    }
}
