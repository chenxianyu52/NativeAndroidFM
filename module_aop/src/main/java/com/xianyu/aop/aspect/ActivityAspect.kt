/*
package com.xianyu.aop.aspect

import android.util.Log
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature

@Aspect
open class ActivityAspect {
    @Pointcut("execution(* com.xianyu.androidfm.*Activity+.on**(..))")
    private fun aspectActivity() {
    }


    @Around("aspectActivity()")
    @Throws(Throwable::class)
    open fun printActivityTime(point: ProceedingJoinPoint) {
        //获取调用方法定义
        val method = (point.signature as? MethodSignature)?.method ?: return
        val className = point.target.javaClass.simpleName
        val startTime = System.currentTimeMillis()
        val kind = point.kind;
        val declaringTypeName = point.signature.declaringTypeName
        val signatureName = point.signature.name
        val sourceFileName = point.sourceLocation.fileName
        val sourceWithinType = point.sourceLocation.withinType.name
        val target: Any = point.target
        point.proceed()
//        Log.i(TAG, "----- printActivityTime -----");
//        Log.i(TAG, "kind:$kind");
//        Log.i(TAG, "declaringTypeName:$declaringTypeName");
//        Log.i(TAG, "signatureName:$signatureName");
//        Log.i(TAG, "sourceFileName:$sourceFileName");
//        Log.i(TAG, "sourceWithinType:$sourceWithinType");
//        Log.i(TAG, "target:$target");
        Log.i(
            TAG,
            "class: $className,method: ${method.name},time = ${System.currentTimeMillis() - startTime}ms"
        )
    }

    */
/**
     * 为了解决这个问题，必须要加入静态方法
     * java.lang.NoSuchMethodError: No static method aspectOf()
     *//*

    companion object {
        @JvmStatic
        fun aspectOf(): ActivityAspect {
            return ActivityAspect()
        }

        private const val TAG = "ActivityAspect"
    }
}*/
