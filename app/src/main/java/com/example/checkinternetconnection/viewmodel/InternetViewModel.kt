package com.example.checkinternetconnection.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class InternetViewModel():ViewModel() {


    private val viewModelScope = CoroutineScope(Dispatchers.Main + Job())


    private val _internetConnection = MutableLiveData<Boolean>()
    val internetConnection :LiveData<Boolean> = _internetConnection

    private val _connectionType = MutableLiveData<ConnectionType>()
    val connectionType : LiveData<ConnectionType> = _connectionType



    // remember this code working on android version M or higher..
    @RequiresApi(Build.VERSION_CODES.M)
    fun checkInternetConnection(context: Context)  {
        viewModelScope.launch {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if(capabilities != null){
                when{
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)->{
                        setInternetInfo(true,ConnectionType.WIFI)
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)->{
                        setInternetInfo(true,ConnectionType.CELLULAR)
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)->{
                        setInternetInfo(true,ConnectionType.ETHERNET)
                    }
                }
            }else{
                setInternetInfo(false , ConnectionType.NO_CONNECTION)
            }
        }
    }



    private fun setInternetInfo(connection : Boolean, type : ConnectionType){
        _internetConnection.value = connection
        _connectionType.value = type
    }


    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}