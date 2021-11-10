package com.xianyu.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import com.android.build.gradle.BaseExtension

class MyPlugin implements Plugin<Project> {
    @Override
    void apply(Project target) {
        MyPluginTransform transform = new MyPluginTransform(target)
        target.android.registerTransform(transform)
    }
}