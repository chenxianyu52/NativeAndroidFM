package com.xianyu.net2.model

data class BaseModel<out T>(val errorCode: Int, val errorMsg: String, val data: T)