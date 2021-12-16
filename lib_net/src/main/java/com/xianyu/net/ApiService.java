package com.xianyu.net;

import com.xianyu.common.AppGlobals;

import java.io.File;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

import com.xianyu.common.log.LoggerUtil;
import com.xianyu.net.cache.FixCacheInterceptor;

import okhttp3.logging.HttpLoggingInterceptor;

public class ApiService {
    protected static final OkHttpClient okHttpClient;
    protected static String sBaseUrl;
    protected static Convert sConvert;

    static {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        //缓存文件夹
        File cacheFile = new File(AppGlobals.getApplication().getFilesDir().toString(),"cache");
        //缓存大小
        int cacheSize = 10 * 1024 * 1024;
        //创建缓存对象
        Cache cache = new Cache(cacheFile,cacheSize);
        FixCacheInterceptor cacheInterceptor = new FixCacheInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS).addNetworkInterceptor(cacheInterceptor).cache(cache)
                .addInterceptor(interceptor)//.addInterceptor(cacheInterceptor)
                .build();


        //http 证书问题
        TrustManager[] trustManagers = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }};
        try {
            SSLContext ssl = SSLContext.getInstance("SSL");
            ssl.init(null, trustManagers, new SecureRandom());

            HttpsURLConnection.setDefaultSSLSocketFactory(ssl.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    public static void init(String baseUrl, Convert convert) {
        sBaseUrl = baseUrl;
        if (convert == null) {
            convert = new JsonConvert();
        }
        sConvert = convert;
    }

    public static <T> GetRequest<T> get(String url) {
        return new GetRequest<>(sBaseUrl + url);
    }

    public static <T> PostRequest<T> post(String url) {
        return new PostRequest<>(sBaseUrl + url);
    }
}
