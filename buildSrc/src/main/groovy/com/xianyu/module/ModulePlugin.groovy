package com.xianyu.module

import com.android.build.api.transform.Transform
import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project

class ModulePlugin implements Plugin<Project> {

    // 实现apply方法，注入插件的逻辑
    void apply(Project project) {

        // 注册 Transform
        if (project.plugins.hasPlugin(AppPlugin)) {
            AppExtension appExtension = project.extensions.getByType(AppExtension)
            Transform transform = new ModuleMappingTransform()
            appExtension.registerTransform(transform)
        }

        // 1. 自动帮助用户传递路径参数到注解处理器中
        if (project.extensions.findByName("kapt") != null) {
            project.extensions.findByName("kapt").arguments {
                arg("root_project_dir", project.rootProject.projectDir.absolutePath)
            }
        }

        // 2. 实现旧的构建产物的自动清理
        project.clean.doFirst {

            // 删除 上一次构建生成的 router_mapping目录
            File routerMappingDir =
                    new File(project.rootProject.projectDir, "router_mapping")

            if (routerMappingDir.exists()) {
                routerMappingDir.deleteDir()
            }

        }

        if (!project.plugins.hasPlugin(AppPlugin)) {
            return
        }

        println("I am from RouterPlugin, apply from ${project.name}")
    }

}
