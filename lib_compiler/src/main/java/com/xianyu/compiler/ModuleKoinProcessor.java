package com.xianyu.compiler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.auto.service.AutoService;
import com.xianyu.annotation.ActivityDestination;
import com.xianyu.annotation.FragmentDestination;
import com.xianyu.annotation.ModuleKoinAnnotation;

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
@SupportedAnnotationTypes({"com.xianyu.annotation.ModuleKoinAnnotation"})
@SupportedOptions("moduleName")
public class ModuleKoinProcessor extends AbstractProcessor {
    private Messager messager;
    private Filer filer;
    private String outFileName;
    private final String TAG = "ModuleKoinProcessor";

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        filer = processingEnv.getFiler();
        //获取gradle中配置的内容作为生成文件的名字
        outFileName = processingEnv.getOptions().get("moduleName") + "_koin.json";
        messager.printMessage(Diagnostic.Kind.NOTE, TAG + ": moduleName:" + outFileName);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        messager.printMessage(Diagnostic.Kind.NOTE, TAG + ": 开始通过注解生成json");
        Set<? extends Element> koinElements = roundEnv.getElementsAnnotatedWith(ModuleKoinAnnotation.class);

        if (!koinElements.isEmpty()) {
            HashMap<String, JSONObject> destMap = new HashMap<>();
            handleKoinModuleAnnotation(koinElements, ModuleKoinAnnotation.class, destMap);

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

    private void handleKoinModuleAnnotation(Set<? extends Element> elements, Class<? extends Annotation> annotationClaz, HashMap<String, JSONObject> destMap) {
        for (Element element : elements) {
            TypeElement typeElement = (TypeElement) element;
            String clazName = typeElement.getQualifiedName().toString();
            int id = Math.abs(clazName.hashCode());
            String path = "";
            Annotation annotation = typeElement.getAnnotation(annotationClaz);
            if (annotation instanceof ModuleKoinAnnotation) {
                ModuleKoinAnnotation dest = (ModuleKoinAnnotation) annotation;
                path = dest.path();
            }

            if (destMap.containsValue(path)) {
                messager.printMessage(Diagnostic.Kind.ERROR, TAG + " : 一个模块只能有一个：" + clazName);
            } else {
                JSONObject object = new JSONObject();
                object.put("path", path);
                destMap.put(String.valueOf(id), object);
            }
        }
    }
}
