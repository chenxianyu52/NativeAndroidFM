package com.xianyu.homemodule.koin

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.xianyu.annotation.ModuleKoinAnnotation
import com.xianyu.route.RoutePathConstant
import com.xianyu.route.koin.KoinModuleGenerate
import org.koin.core.module.Module

@Route(path = RoutePathConstant.HOME_KOIN_MODULE_PATH)
@ModuleKoinAnnotation(path = RoutePathConstant.HOME_KOIN_MODULE_PATH)
class KoinModuleProvider : KoinModuleGenerate {
    override fun getModuleList(): Module {
        return homeKoinModule
    }

    override fun init(context: Context?) {
    }
}