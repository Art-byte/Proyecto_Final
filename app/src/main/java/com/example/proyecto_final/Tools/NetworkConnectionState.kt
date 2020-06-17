package com.example.proyecto_final.Tools

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log


class NetworkConnectionState(val context: Context) {

    fun NetworkIsConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork

            if (network != null) {

                connectivityManager.getNetworkCapabilities(network).run {
                    return when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                            Log.d("RED", "TRANSPORT WIFI")
                            true
                        }

                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)->{
                            Log.d("RED","TRANSPORT CELULAR")
                            true
                        }
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                            Log.d("RED", "TRANSPORT ETHERNET")
                            true
                        }

                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI_AWARE)->{
                            Log.d("RED", "TRANSPORT WIFI AWARE")
                            true
                        }

                        else -> {
                            Log.d("RED", "***NADA***")
                            false
                        }


                    }
                }
            } else {
                Log.d("RED", "NETWORK NULL")
                return false
            }

        } else {
            Log.d("RED", "OLD VERSIONS")
            connectivityManager.activeNetworkInfo.run {
                return isConnected
            }
        }


    }
}