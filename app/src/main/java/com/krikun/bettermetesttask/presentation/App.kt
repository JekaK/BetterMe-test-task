package com.krikun.bettermetesttask.presentation

import android.app.Application
import com.krikun.bettermetesttask.presentation.DI.DI
import com.krikun.bettermetesttask.presentation.DI.DICommon
import com.krikun.data.network.MainApi

class App : Application() {

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        MainApi.init(context)
        DICommon.init(this, DI)

    }

    companion object {
        lateinit var instance: Application

        val context get() = instance
    }
}