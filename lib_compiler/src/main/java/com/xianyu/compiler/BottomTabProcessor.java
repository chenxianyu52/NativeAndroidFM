package com.xianyu.compiler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.auto.service.AutoService;
import com.xianyu.annotation.ActivityDestination;
import com.xianyu.annotation.BottomTabAnnotation;
import com.xianyu.annotation.FragmentDestination;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;


@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes({"com.xianyu.annotation.BottomTabAnnotation"})
@SupportedOptions("moduleName")
public class BottomTabProcessor extends AbstractProcessor {
    private Messager messager;
    private Filer filer;
    private String outFileName;
    private final String TAG = "BottomTabProcessor";

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        filer = processingEnv.getFiler();
        //获取gradle中配置的内容作为生成文件的名字
        outFileName = processingEnv.getOptions().get("moduleName") + "_bottom_tab.json";
        messager.printMessage(Diagnostic.Kind.NOTE, TAG + ": moduleName:" + outFileName);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        messager.printMessage(Diagnostic.Kind.NOTE, TAG + ":开始通过注解生成json");
        Set<? extends Element> bottomTabElements = roundEnv.getElementsAnnotatedWith(BottomTabAnnotation.class);

        if (!bottomTabElements.isEmpty()) {
            HashMap<String, JSONObject> destMap = new HashMap<>();
            handleElements(bottomTabElements, BottomTabAnnotation.class, destMap);

            //app/src/main/assets
            FileOutputStream fos = null;
            OutputStreamWriter writer = null;
            try {
                FileObject resource = filer.createResource(StandardLocation.CLASS_OUTPUT, "", outFileName);
                String resourcePath = resource.toUri().getPath();
                messager.printMessage(Diagnostic.Kind.NOTE, TAG + ": resourcePath:" + resourcePath);
                String appPath = resourcePath.substring(0, resourcePath.indexOf("build"));
                String assetsPath = appPath + "src/main/assets/";

                File file = new File(assetsPath);
                if (!file.exists()) {
                    file.mkdirs();
                }

                File outPutFile = new File(file, outFileName);
                if (outPutFile.exists()) {
                    outPutFile.delete();
                }
                outPutFile.createNewFile();
                String content = JSON.toJSONString(destMap);
                fos = new FileOutputStream(outPutFile);
                writer = new OutputStreamWriter(fos, "UTF-8");
                writer.write(content);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }


        return true;
    }

    private void handleElements(Set<? extends Element> elements, Class<? extends Annotation> annotationClaz, HashMap<String, JSONObject> destMap) {
        for (Element element : elements) {
            TypeElement typeElement = (TypeElement) element;
            String clazName = typeElement.getQualifiedName().toString();
            int id = Math.abs(clazName.hashCode());
            int size = -1;
            boolean enable = false;
            int index = -1;
            String pageUrl = null;
            String title = null;
            String icon = null;
            Annotation annotation = typeElement.getAnnotation(annotationClaz);
            if (annotation instanceof BottomTabAnnotation) {
                BottomTabAnnotation dest = (BottomTabAnnotation) annotation;
                size = dest.size();
                enable = dest.enable();
                index = dest.index();
                pageUrl = dest.pageUrl();
                title = dest.title();
                icon = dest.icon();
            }
            if(destMap.containsKey(String.valueOf(index))){
                messager.printMessage(Diagnostic.Kind.ERROR, TAG + " : 不同的tab不允许使用相同的index：" + clazName);
            } else {
                JSONObject object = new JSONObject();
                object.put("size", size);
                object.put("enable", enable);
                object.put("index", index);
                object.put("pageUrl", pageUrl);
                object.put("title", title);
                object.put("icon", icon);
                destMap.put(String.valueOf(index), object);
            }
        }
    }
}
