package com.example.mvvmhilt.common.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmhilt.common.utils.extn.connectivityManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InternetStatusImpl @Inject constructor(val context: Context) : InternetStatus {

    //internet status holder
    private var status: LiveData<Boolean> = MutableLiveData(false)

    private val cM = context.connectivityManager
    private val nR = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .build()

    //callback to receive internet connection status from device
    private val listener = object : ConnectivityManager.NetworkCallback() {
        // network is available for use
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            (status as MutableLiveData).postValue(true)
        }

        // lost network connection
        override fun onLost(network: Network) {
            super.onLost(network)
            (status as MutableLiveData).postValue(false)
        }
    }

    init {
        cM.requestNetwork(nR, listener)
    }

    override fun isConnected(): Boolean = status.value ?: false

    override fun observe(): LiveData<Boolean> = status
}

interface InternetStatus {
    fun isConnected(): Boolean
    fun observe(): LiveData<Boolean>
}