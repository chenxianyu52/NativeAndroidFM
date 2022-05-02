package com.xianyu.homemodule.koin

import com.xianyu.homemodule.HelloRepositoryImpl
import com.xianyu.route.homemodule.HelloRepository
import org.koin.dsl.module

val homeKoinModule = module {
    single{ HelloRepositoryImpl() }
}