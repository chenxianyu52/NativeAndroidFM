package com.xianyu.androidfm;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;


public class InterceptEventHanlder {
    private static String TAG = "tracepoint";

    //------------------- activity 事件接收
    public static void activityOnCreate(Activity activity){
        Log.e(TAG,activity.getClass().getName());
    }

    public static void activityOnResume(Activity activity) {
        Log.e(TAG,activity.getClass().getName());
    }
    //------------------- fragment 事件接收
    public static void setUserVisibleHint(Fragment fragment, boolean visiable){
        if (visiable){
            Log.e(TAG,"pv:"+fragment.getClass().getName());
        }
    }

    public static void onHiddenChanged(Fragment fragment,boolean hidden){

        if (!hidden){
            Log.e(TAG,"pv:"+fragment.getClass().getName());
        }
    }

//    public static void setUserVisibleHint(android.support.v4.app.Fragment fragment,boolean visiable){
//
//        if (visiable){
//            Log.e(TAG,"pv:"+fragment.getClass().getName());
//        }
//    }
//
//    public static void onHiddenChanged(android.support.v4.app.Fragment fragment,boolean hidden){
//
//        if (!hidden){
//            Log.e(TAG,"pv:"+fragment.getClass().getName());
//        }
//    }

    //------------------- click 事件接收

    public static void onClick(View view){

        try {
            //以下ViewPath工具类可从源码app模块中获取
            Activity activity = ViewPath.getActivity(view);
            String path = ViewPath.getViewPath(activity,view);

            Log.e(TAG,"viewPath:"+path);
        }catch (Exception e){e.printStackTrace();}
    }
}
