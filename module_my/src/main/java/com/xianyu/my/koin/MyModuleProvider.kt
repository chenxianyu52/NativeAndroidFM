package com.xianyu.my.koin

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.xianyu.androidlibrary.log.LoggerUtil
import com.xianyu.annotation.ModuleKoinAnnotation
import com.xianyu.route.RoutePathConstant
import com.xianyu.route.koin.ModuleGenerate
import org.koin.core.context.GlobalContext

@Route(path = RoutePathConstant.MY_KOIN_MODULE_PATH)
@ModuleKoinAnnotation(path = RoutePathConstant.MY_KOIN_MODULE_PATH)
class MyModuleProvider : ModuleGenerate {
    override fun initModule() {
        LoggerUtil.i("cxy", "my initModule")
        GlobalContext.loadKoinModules(myKoinModule)
    }

    override fun init(context: Context?) {

    }
}