package com.xianyu.homemodule.koin

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.xianyu.androidlibrary.log.LoggerUtil
import com.xianyu.annotation.ModuleKoinAnnotation
import com.xianyu.route.RoutePathConstant
import com.xianyu.route.koin.ModuleGenerate
import org.koin.core.context.GlobalContext

@Route(path = RoutePathConstant.HOME_KOIN_MODULE_PATH)
@ModuleKoinAnnotation(path = RoutePathConstant.HOME_KOIN_MODULE_PATH)
class ModuleProvider : ModuleGenerate {
    override fun initModule() {
        LoggerUtil.i("cxy", "home initModule")
        GlobalContext.loadKoinModules(homeKoinModule)
    }

    override fun init(context: Context?) {

    }
}