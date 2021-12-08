package com.xianyu.homemodule

import com.xianyu.route.homemodule.HelloRepository

class HelloRepositoryImpl(): HelloRepository {
    override fun giveHello(): String {
        return "Hello Koin"
    }
}