package com.xianyu.androidlibrary.log

import android.content.Intent
import android.os.Bundle
import com.orhanobut.logger.*
import java.util.*


object LoggerUtil {
    @JvmStatic
    fun init() {
        val formatStrategy: FormatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false) // (Optional) Whether to show thread info or not. Default true
            .methodCount(0) // (Optional) How many method line to show. Default 2
            .methodOffset(7) // (Optional) Hides internal method calls up to offset. Default 5
            .tag("cxy") // (Optional) Global tag for every log. Default PRETTY_LOGGER
            .build()

        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
    }

    @JvmStatic
    fun i(tag: String?, message: String?) {
        if (message == null) {
            Logger.t(tag).e("message is null")
        } else {
            Logger.t(tag).i(message)
        }
    }

    @JvmStatic
    fun d(tag: String?, message: String?) {
        Logger.t(tag).d(message)
    }

    @JvmStatic
    fun dJson(tag: String?, json: String?) {
        Logger.t(tag).json(json)
    }

    @JvmStatic
    fun dXml(tag: String?, xml: String?) {
        Logger.t(tag).xml(xml)
    }

    @JvmStatic
    fun dObject(tag: String?, obj: Any?) {
        Logger.t(tag).d(obj)
    }

    @JvmStatic
    fun dIntent(tag: String?, intent: Intent?) {
        if (intent == null) {
            Logger.t(tag).e("intent is null")
        } else {
            Logger.t(tag).i(intentToString(intent))
        }
    }

    @JvmStatic
    fun dBundle(tag: String?, bundle: Bundle?) {
        if (bundle == null) {
            Logger.t(tag).e("bundle is null")
        } else {
            Logger.t(tag).i(bundleToString(bundle))
        }
    }

    @JvmStatic
    fun e(tag: String?, message: String?) {
        if (message == null) {
            Logger.t(tag).e("message is null")
        } else {
            Logger.t(tag).e(message)
        }
    }

    @JvmStatic
    fun e(tag: String?, exception: Exception?) {
        if (exception == null) {
            Logger.t(tag).e("exception is null")
        } else {
            Logger.t(tag).e(exception, "")
        }
    }

    private fun intentToString(intent: Intent?): String {
        return intent.toString() + " " + bundleToString(intent?.extras)
    }

    private fun bundleToString(bundle: Bundle?): String {
        val out = StringBuilder("Bundle[")
        if (bundle == null) {
            out.append("null")
        } else {
            var first = true
            for (key in bundle.keySet()) {
                if (!first) {
                    out.append(", ")
                }
                out.append(key).append('=')
                when (val value = bundle[key]) {
                    is IntArray -> {
                        out.append(Arrays.toString(value as IntArray?))
                    }
                    is ByteArray -> {
                        out.append(Arrays.toString(value as ByteArray?))
                    }
                    is BooleanArray -> {
                        out.append(Arrays.toString(value as BooleanArray?))
                    }
                    is ShortArray -> {
                        out.append(Arrays.toString(value as ShortArray?))
                    }
                    is LongArray -> {
                        out.append(Arrays.toString(value as LongArray?))
                    }
                    is FloatArray -> {
                        out.append(Arrays.toString(value as FloatArray?))
                    }
                    is DoubleArray -> {
                        out.append(Arrays.toString(value as DoubleArray?))
                    }
                    is Array<*> -> {
                        out.append(Arrays.toString(value as Array<*>?))
                    }
                    is Bundle -> {
                        out.append(bundleToString(value as Bundle?))
                    }
                    else -> {
                        out.append(value)
                    }
                }
                first = false
            }
        }
        out.append("]")
        return out.toString()
    }

}