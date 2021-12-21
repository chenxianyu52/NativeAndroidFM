package com.xianyu.route.koin

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.xianyu.common.nav.AppConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.Module

object KoinUtil {
    fun start(app: Application) {
        startKoin {
            androidLogger()
            androidContext(app)
        }
        val moduleMap = AppConfig.getModuleConfig()

        for ((_, value) in moduleMap) {
            val module = ARouter.getInstance().build(value.path).navigation() as ModuleGenerate?
            module?.initModule()
        }
    }
}