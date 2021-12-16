package com.xianyu.net

import org.koin.dsl.module

val NetKoinModule = module {
    single { OkhttpClient }

}