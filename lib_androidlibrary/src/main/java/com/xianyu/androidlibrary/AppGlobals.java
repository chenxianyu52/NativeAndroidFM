package com.xianyu.androidlibrary;

import android.app.Application;

import java.lang.reflect.InvocationTargetException;

public class AppGlobals {
    private static Application sApplication;

    public static Application getApplication() {
        if (sApplication == null) {
            try {
                sApplication = (Application) Class.forName("android.app.ActivityThread")
                        .getMethod("currentApplication")
                        .invoke(null, (Object[]) null);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return sApplication;
    }
}
