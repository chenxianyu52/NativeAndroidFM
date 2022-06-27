package com.xianyu.androidfm

import android.app.Application
import com.xianyu.androidlibrary.log.LoggerUtil
import com.xianyu.androidlibrary.utils.CrashHandler
import com.xianyu.route.ARouterUtil
import com.xianyu.route.koin.KoinUtil

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        LoggerUtil.init()
        ARouterUtil.init(this)
        KoinUtil.start(this)
        CrashHandler.instance.init(this)
    }
}