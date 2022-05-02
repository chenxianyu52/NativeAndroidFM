package com.xianyu.net2

import com.xianyu.net2.net.RetrofitClient
import org.koin.dsl.module

val NetKoinModule = module {
    single {
        RetrofitClient()
    }
}