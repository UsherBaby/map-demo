package com.pacific.arch.example

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.baidu.mapapi.SDKInitializer

class APP : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        SDKInitializer.initialize(this)
    }

    companion object {
        @Volatile
        @get:Synchronized
        var instance: APP? = null
            private set
    }
}
