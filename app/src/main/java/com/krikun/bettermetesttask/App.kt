package com.krikun.bettermetesttask

import android.app.Application
import com.krikun.data.network.MainApi
import com.krikun.bettermetesttask.presentation.DI.DI
import com.krikun.bettermetesttask.presentation.DI.DICommon

class App : Application() {

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        MainApi.init()
        DICommon.init(this, DI)

    }

    companion object {
        lateinit var instance: Application

        val context get() = instance
    }
}