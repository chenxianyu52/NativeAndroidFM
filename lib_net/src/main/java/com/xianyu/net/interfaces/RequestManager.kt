package com.xianyu.net.interfaces

import com.xianyu.net.GetRequest
import com.xianyu.net.PostRequest

class RequestManager {
    fun <T> get(baseUrl: String, url: String): GetRequest<T> {
        return GetRequest(baseUrl + url)
    }

    fun <T> post(baseUrl: String, url: String): PostRequest<T> {
        return PostRequest(baseUrl + url)
    }
}