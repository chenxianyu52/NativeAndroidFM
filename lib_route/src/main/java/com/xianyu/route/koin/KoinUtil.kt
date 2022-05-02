package com.xianyu.route.koin

import android.app.Application
import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter
import com.xianyu.androidlibrary.log.LoggerUtil
import com.xianyu.common.nav.AppConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.Module
import java.lang.reflect.InvocationTargetException

object KoinUtil {
    private const val TAG = "RouterTAG"

    // 编译期间生成的总映射表
    private const val GENERATED_MAPPING =
        "com.xianyu.androidfm.module.generated.ModuleAllPathMapping"

    // 存储所有映射表信息
//    private val mapping: HashSet<String> = HashSet()

    fun start(app: Application) {
        startKoin {
            androidLogger()
            androidContext(app)
        }


//        val moduleMap = AppConfig.getModuleConfig()
//        LoggerUtil.dObject("cxy moduleMap", moduleMap)

//        for ((_, value) in moduleMap) {
//            LoggerUtil.dObject("cxy moduleMap2 ", value.path)
//            val module = ARouter.getInstance().build(value.path).navigation() as ModuleGenerate?
//            LoggerUtil.dObject("cxy moduleMap end ", module)
//            module?.initModule()
//        }
        LoggerUtil.i(TAG, "koin start")
        try {
            val clazz = Class.forName(GENERATED_MAPPING)
            LoggerUtil.i(TAG, "clazz")
            val method = clazz.getMethod("get")
            LoggerUtil.i(TAG, "getMethod")
            val allMapping = method.invoke(null) as Map<String, String>
            LoggerUtil.i(TAG, "invoke")

            if (allMapping.isNotEmpty()) {
                LoggerUtil.i(TAG, "init: get all set:")
                allMapping.onEach {
                    LoggerUtil.i(TAG, it.key + " -> " + it.value)
                    val module = ARouter.getInstance().build(it.key).navigation() as ModuleGenerate?
                    module?.initModule()
//                    (it as? String)?.let { it1 -> mapping.add(it1) }
                }
//                mapping.addAll(allMapping)
            }
            LoggerUtil.i(TAG, "koin end")

        } catch (e: InvocationTargetException) {
            Log.e(TAG, "init: error while init router : $e")
        }
    }
}