package com.xianyu.route.koin

import com.alibaba.android.arouter.facade.template.IProvider
import org.koin.core.module.Module


/**
 * 所有的模块的koin module 都实现这个接口，加载的时候按需加载
 */
interface ModuleGenerate : IProvider {
    /**
     * 模块初始化都放在这里
     */
    fun initModule()
}
