package com.xianyu.net.cache

import com.xianyu.common.AppGlobals
import com.xianyu.net.util.NetworkUtil
import okhttp3.*
import java.io.IOException


/** Serves requests from the cache and writes responses to the cache. */
class FixCacheInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        val netAvailable: Boolean = NetworkUtil.isNetworkConnected(AppGlobals.getApplication())
        request = if (netAvailable) {
            request.newBuilder()
                .cacheControl(CacheControl.FORCE_NETWORK)
                .build()
        } else {
            request.newBuilder()
                .cacheControl(CacheControl.FORCE_CACHE)
                .build()
        }
        var response: Response = chain.proceed(request)
        response = if (netAvailable) {
            response.newBuilder()
                .removeHeader("Pragma")
                .header("Cache-Control", "public, max-age=" + 60 * 60)
                .build()
        } else {
            response.newBuilder()
                .removeHeader("Pragma")
                .header("Cache-Control", "public, only-if-cached, max-stale=" + 7 * 24 * 60 * 60)
                .build()
        }
        return response
    }
}
