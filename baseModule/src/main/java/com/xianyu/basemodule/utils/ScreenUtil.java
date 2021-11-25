package com.xianyu.basemodule.utils;

import android.content.Context;
import android.util.DisplayMetrics;

public class ScreenUtil {
    public static int dp2Px(Context context , int dpValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) (metrics.density * dpValue + 0.5f);
    }
}
