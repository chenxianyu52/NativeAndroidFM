package com.xianyu.plugin

import javassist.CtClass
import javassist.CtField
import javassist.CtMethod;
import org.gradle.api.Project;

import javassist.ClassPool
import org.gradle.api.logging.LogLevel;
import com.android.SdkConstants

class JavassistInject {
    public static final String JAVA_ASSIST_APP = "com.xianyu.androidfm"
    private static final String CLICK_LISTENER = "android.view.View\$OnClickListener"

//    public static final String JAVA_ASSIST_MOBCLICK = "com.umeng.analytics.MobclickAgent"

    private final static ClassPool pool = ClassPool.getDefault()

    static void injectDir(String path, String packageName, Project project) {
        pool.appendClassPath(path)
        String androidJarPath = project.android.bootClasspath[0].toString()
        log("androidJarPath: " + androidJarPath, project)
        pool.appendClassPath(androidJarPath)
        importClass(pool)
        File dir = new File(path)
        if (!dir.isDirectory()) {
            return
        }
        dir.eachFileRecurse { File file ->
            String filePath = file.absolutePath
            log("filePath : " + filePath, project)
            if (filePath.endsWith(".class") && !filePath.contains('R$')
                    && !filePath.contains('R.class') && !filePath.contains("BuildConfig.class")) {
                log("filePath my : " + filePath, project)
                int index = filePath.indexOf(packageName);
                boolean isMyPackage = index != -1;
                if (!isMyPackage) {
                    return
                }
                String className = JavassistUtils.getClassName(index, filePath)
                log("className my : " + className, project)
                CtClass ctClass = pool.getCtClass(className)
                CtClass[] interfaces = ctClass.getInterfaces()
                CtClass[] method = ctClass.get
                for(CtClass cc : interfaces){
                    log("interfaces" + cc.name,project)
                }
                if (interfaces.contains(pool.get(CLICK_LISTENER))) {
                    log("111" + className,project)
                    if (className.contains("\$")) {
                        log("class is inner class：" + ctClass.name,project)
                        log("CtClass: " + ctClass,project)
                        CtClass outer = pool.get(className.substring(0, className.indexOf("\$")))

                        CtField field = ctClass.getFields().find {
                            return it.type == outer
                        }
                        if (field != null) {
                            log("fieldStr: " + field.name,project)
                            String body = "android.widget.Toast.makeText(" + field.name + "," +
                                    "\"javassist\", android.widget.Toast.LENGTH_SHORT).show();"
                            addCode(ctClass, body, path,project)
                        }
                    } else {
                        log("class is outer class: " + ctClass.name,project)
                        //更改onClick函数
                        String body = "android.widget.Toast.makeText(\$1.getContext(), \"javassist\", android.widget.Toast.LENGTH_SHORT).show();"
                        addCode(ctClass, body, path,project)
                    }
                }
            }
//                log("CtClass my : " + c.getSimpleName() , project)
//                for(CtMethod method : c.getDeclaredMethods()){
//                    log("CtMethod my : " + method.getName() , project)
//                    if(checkOnClickMethod(method)){
//                        log("checkOnClickMethod my : " + method.getName() , project)
//                        injectMethod(method)
//                        c.writeFile(path)
//                    }
//                }
        }
    }


    private static void addCode(CtClass ctClass, String body, String fileName,Project project) {

        ctClass.defrost()
        CtMethod method = ctClass.getDeclaredMethod("onClick", pool.get("android.view.View"))
        method.insertAfter(body)

        ctClass.writeFile(fileName)
        ctClass.detach()
        log("write file: " + fileName + "\\" + ctClass.name,project)
        log("modify method: " + method.name + " succeed",project)
    }


    private static boolean checkOnClickMethod(CtMethod method) {
        return method.getName().endsWith("onClick") && method.getParameterTypes().length == 1 && method.getParameterTypes()[0].getName().equals("android.view.View");
    }

    private static void injectMethod(CtMethod method) {
        method.insertAfter("System.out.println((\$1).getTag());")
//        method.insertAfter("MobclickAgent.onEvent(MyApp.getInstance(), (\$1).getTag().toString());")
    }

    private static void log(String msg, Project project) {
        project.logger.log(LogLevel.ERROR, msg)
    }

    private static void importClass(ClassPool pool) {
        pool.importPackage(JAVA_ASSIST_APP)
//        pool.importPackage(JAVA_ASSIST_MOBCLICK)
    }

}
