package com.xianyu.androidfm

import android.app.Application
import com.xianyu.common.log.LoggerUtil
import com.xianyu.route.ARouterUtil
import com.xianyu.route.koin.KoinUtil

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        LoggerUtil.init()
        ARouterUtil.init(this)
        KoinUtil.start(this)
    }
}