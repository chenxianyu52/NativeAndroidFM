package com.xianyu.route.koin

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.xianyu.route.RoutePathConstant
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

object KoinUtil {
    fun start(app: Application) {
        val homeModuleList = ARouter.getInstance().build(RoutePathConstant.HOME_KOIN_MODULE_PATH).navigation() as KoinModuleGenerate
        startKoin {
            androidLogger()
            androidContext(app)
            modules(homeModuleList.getModuleList())
        }
    }
}