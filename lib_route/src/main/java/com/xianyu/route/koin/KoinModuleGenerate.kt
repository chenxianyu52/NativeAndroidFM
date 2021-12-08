package com.xianyu.route.koin

import com.alibaba.android.arouter.facade.template.IProvider
import org.koin.core.module.Module


/**
 * 所有的模块的koin module 都实现这个接口，加载的时候按需加载
 */
interface KoinModuleGenerate : IProvider {
    fun getModuleList(): Module
}
