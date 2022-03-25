package com.xianyu.androidlibrary.utils

import android.widget.Toast
import com.xianyu.androidlibrary.AppGlobals


/**
 * Description: <吐司工具类><br>
 * Author: mxdl<br>
 * Date: 2018/6/11<br>
 * Version: V1.0.0<br>
 * Update: <br>
</吐司工具类> */
object ToastUtil {

    fun showToast(message: String) {
        Toast.makeText(AppGlobals.getApplication(), message, Toast.LENGTH_SHORT).show()
    }

    fun showToast(resid: Int) {
        Toast.makeText(AppGlobals.getApplication(), AppGlobals.getApplication().getString(resid), Toast.LENGTH_SHORT)
                .show()
    }
}