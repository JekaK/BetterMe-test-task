package com.krikun.bettermetesttask.presentation.utils

import android.content.Context
import android.net.ConnectivityManager
import com.krikun.bettermetesttask.presentation.App.Companion.context


object Network {

    fun isNetworkAvailable(): Boolean {
        val cm =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting

    }
}