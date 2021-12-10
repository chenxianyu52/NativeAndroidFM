package com.xianyu.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
public @interface BottomTabAnnotation {
    /**
     * "size": 24,
     * "enable": true,
     * "index": 0,
     * "pageUrl": "main/tabs/home",
     * "title": "首页",
     * "icon": "ic_home_black_24dp"
     */
    int size() default -1;

    boolean enable() default false;

    int index() default -1;

    String pageUrl();

    String title();

    String icon();
}
