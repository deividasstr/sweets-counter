package com.deividasstr.data.networking.manager

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Singleton

@Singleton
class NetworkManager(private val context: Context) {

    val networkAvailable: Boolean
        get() =
            (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo?.isConnected
            ?: false
}
