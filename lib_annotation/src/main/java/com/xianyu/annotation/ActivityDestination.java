package com.xianyu.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
public @interface ActivityDestination {
    String pageUrl();

    boolean needLogin() default false;//有些页面需要登录才能进入，判断是否需要登录

    boolean asStarter() default false;//是否是startDestination
}
