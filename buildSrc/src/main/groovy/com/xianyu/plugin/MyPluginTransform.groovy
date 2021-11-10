package com.xianyu.plugin

import com.android.build.api.transform.Context
import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.Format
import com.android.build.api.transform.JarInput;
import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformInvocation
import com.android.build.api.transform.TransformOutputProvider
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import javassist.ClassPool
import javassist.CtClass
import javassist.CtMethod
import javassist.NotFoundException
import org.gradle.api.Project;

import java.util.Set;

class MyPluginTransform extends Transform {

    Project mProject

    public MyPluginTransform(Project project) {
        mProject = project
    }

    //返回的任务  名字 会放到任务列表里
    @Override
    String getName() {
        return "myplugin"
    }

    //告诉Transform你要处理那些类型的文件（这些文件都是编译好的）
    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        //表示处理编译好的class文件
        return TransformManager.CONTENT_CLASS
    }
    //过滤器，标明你要处理的范围。是整个项目 或者某一个子项目 或者某几个子项目 ，也可以自己去定义Scope
    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(Context context, Collection<TransformInput> inputs, Collection<TransformInput> referencedInputs, TransformOutputProvider outputProvider, boolean isIncremental) throws IOException, TransformException, InterruptedException {
        super.transform(context, inputs, referencedInputs, outputProvider, isIncremental)

        log("transform >>>>>")
        //Transform 的 input 有两种类型，目录 和 jar，分开遍历
        inputs.each { TransformInput input->
            input.directoryInputs.each { DirectoryInput directoryInput->
                log("directoryInput name = " + directoryInput.name +", path = " + directoryInput.file.absolutePath)

                JavassistInject.injectDir(directoryInput.file.getAbsolutePath(), "com", mProject)

                def dest = outputProvider.getContentLocation(directoryInput.name,
                        directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)

                //将 input 的目录复制到 output 指定目录
                FileUtils.copyDirectory(directoryInput.file, dest)
            }

            input.jarInputs.each { JarInput jarInput ->

                log("jarInput name = " + jarInput.name +", path = " + jarInput.file.absolutePath)

                JavassistInject.injectDir(jarInput.file.getAbsolutePath(), "com", mProject)

                //重命名输出文件（同目录 copyFile 会冲突）
                def jarName = jarInput.name
                def md5Name = jarInput.file.hashCode()
                if(jarName.endsWith(".jar")){
                    jarName = jarName.substring(0, jarName.length() - 4)
                }
                def dest = outputProvider.getContentLocation(jarName + md5Name,
                        jarInput.contentTypes, jarInput.scopes, Format.JAR)
                FileUtils.copyFile(jarInput.file, dest)
            }
        }
    }

    void log(String log){
        mProject.logger.error(log)
    }
}