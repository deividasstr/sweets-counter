package com.deividasstr.ui.base.di.modules

import com.deividasstr.data.networking.apis.FactsApi
import com.deividasstr.data.networking.apis.SweetsApi
import com.deividasstr.data.networking.services.FactsService
import com.deividasstr.data.networking.services.SweetsService
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule(private val baseUrl: String) {

    @Singleton
    @Provides
    fun providesInterceptors(): ArrayList<Interceptor> {

        val interceptors = arrayListOf<Interceptor>()

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC

        interceptors.add(loggingInterceptor)

        return interceptors
    }

    @Singleton
    @Provides
    fun providesRetrofit(interceptors: ArrayList<Interceptor>): Retrofit {

        val clientBuilder = OkHttpClient.Builder()
        if (!interceptors.isEmpty()) {
            interceptors.forEach { interceptor ->
                clientBuilder.addInterceptor(interceptor)
            }
        }

        return Retrofit.Builder()
            .client(clientBuilder.build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
    }

    @Singleton
    @Provides
    fun provideSweetsService(retrofit: Retrofit): SweetsService {
        return SweetsService(retrofit.create(SweetsApi::class.java))
    }

    @Singleton
    @Provides
    fun provideFactsService(retrofit: Retrofit): FactsService {
        return FactsService(retrofit.create(FactsApi::class.java))
    }
}