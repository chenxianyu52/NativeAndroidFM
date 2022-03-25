package com.xianyu.net

import com.xianyu.androidlibrary.AppGlobals
import com.xianyu.net.cache.FixCacheInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object OkhttpClient {
    var okHttpClient: OkHttpClient? = null
    var sBaseUrl: String? = null
    var sConvert: Convert<Any>? = null

    init {
        val interceptor = HttpLoggingInterceptor()
        //缓存文件夹
        val cacheFile = File(AppGlobals.getApplication().filesDir.toString(), "cache")
        //缓存大小
        val cacheSize = 10 * 1024 * 1024
        //创建缓存对象
        val cache = Cache(cacheFile, cacheSize.toLong())
        val cacheInterceptor = FixCacheInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        okHttpClient = OkHttpClient.Builder()
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .connectTimeout(5, TimeUnit.SECONDS)
            .addNetworkInterceptor(cacheInterceptor)
            .cache(cache)
            .addInterceptor(interceptor)
            .build()

        //http 证书问题
        val trustManagers = arrayOf<TrustManager>(object : X509TrustManager {
            @Throws(CertificateException::class)
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate?> {
                return arrayOfNulls(0)
            }
        })
        try {
            val ssl = SSLContext.getInstance("SSL")
            ssl.init(null, trustManagers, SecureRandom())
            HttpsURLConnection.setDefaultSSLSocketFactory(ssl.socketFactory)
            HttpsURLConnection.setDefaultHostnameVerifier { _, _ -> true }
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: KeyManagementException) {
            e.printStackTrace()
        }
    }

    fun init(baseUrl: String?, convert: Convert<Any>?) {
        var convert = convert
        ApiService.sBaseUrl = baseUrl
        if (convert == null) {
            convert = JsonConvert()
        }
        sConvert = convert
    }
}