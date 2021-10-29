package com.example.hotcapp.common

import android.app.Application

private lateinit var application: BaseApp
class BaseApp: Application() {
    companion object{

        fun getAppContext() = application
    }

    override fun onCreate() {
        super.onCreate()
        application = this
    }
}