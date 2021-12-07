package com.xianyu.common.utils

import android.Manifest
import androidx.fragment.app.FragmentActivity
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions


/**
 * Description: <PermissionCheckUtil><br>
 * Author:      mxdl<br>
 * Date:        2019/3/29<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
</PermissionCheckUtil> */
object PermissionCheckUtil {
    fun check(activity: FragmentActivity) {
        XXPermissions.with(activity) // 申请单个权限
                .permission(Permission.RECORD_AUDIO) // 申请多个权限
                //.permission(Permission.Group.CALENDAR)
                // 申请安装包权限
                //.permission(Permission.REQUEST_INSTALL_PACKAGES)
                // 申请悬浮窗权限
                //.permission(Permission.SYSTEM_ALERT_WINDOW)
                // 申请通知栏权限
                //.permission(Permission.NOTIFICATION_SERVICE)
                // 申请系统设置权限
                //.permission(Permission.WRITE_SETTINGS)
                // 设置权限请求拦截器
                //.interceptor(new PermissionInterceptor())
                // 设置不触发错误检测机制
                //.unchecked()
                .request(object : OnPermissionCallback {
                    override fun onGranted(permissions: List<String?>?, all: Boolean) {
                        if (all) {
                            ToastUtil.showToast("获取录音和日历权限成功")
                        } else {
                            ToastUtil.showToast("获取部分权限成功，但部分权限未正常授予")
                        }
                    }

                    override fun onDenied(permissions: List<String?>?, never: Boolean) {
                        if (never) {
                            ToastUtil.showToast("被永久拒绝授权，请手动授予录音和日历权限")
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(activity, permissions)
                        } else {
                            ToastUtil.showToast("获取录音和日历权限失败")
                        }
                    }
                })
    }
}
