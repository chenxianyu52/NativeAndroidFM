package com.xianyu.my

import com.xianyu.route.homemodule.HelloRepository

class HelloRepositoryImpl() {
    fun giveHello(): String {
        return "Hello Koin my"
    }
}