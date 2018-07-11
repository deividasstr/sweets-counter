package com.deividasstr.ui.utils.di

import android.content.Context
import com.deividasstr.data.di.modules.NetworkModule
import com.deividasstr.data.networking.manager.NetworkManager
import com.deividasstr.ui.utils.TestVals
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.willReturn
import okhttp3.Interceptor
import retrofit2.Retrofit

class TestNetworkModule(baseUrl: String) : NetworkModule(baseUrl) {

    override fun provideNetworkManager(context: Context): NetworkManager {
        val mock = mock<NetworkManager>()
        given { mock.networkAvailable } willReturn { false }
        return mock
    }

    override fun provideRetrofit(interceptors: ArrayList<Interceptor>): Retrofit {
        return super.provideRetrofit(interceptors).newBuilder().baseUrl(TestVals.mockUrl).build()
    }
}