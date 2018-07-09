package com.deividasstr.ui.utils.di

import android.content.Context
import com.deividasstr.data.di.modules.NetworkModule
import com.deividasstr.data.networking.manager.NetworkManager
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.willReturn

class TestNetworkModule : NetworkModule("") {

    override fun provideNetworkManager(context: Context): NetworkManager {
        val mock = mock<NetworkManager>()
        given { mock.networkAvailable } willReturn  { false }
        return mock
    }
}