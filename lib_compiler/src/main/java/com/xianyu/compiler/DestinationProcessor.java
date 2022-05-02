package com.xianyu.compiler;

import com.google.auto.service.AutoService;
import com.xianyu.annotation.ModuleKoinAnnotation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

@AutoService(Processor.class)
public class DestinationProcessor extends AbstractProcessor {

    private static final String TAG = "DestinationProcessor";

    /**
     * 编译器找到我们关心的注解后，会回调这个方法
     *
     * @param set
     * @param roundEnvironment
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> set,
                           RoundEnvironment roundEnvironment) {

        // 避免多次调用 process
        if (roundEnvironment.processingOver()) {
            return false;
        }

        System.out.println(TAG + " >>> process start ...");

//        String rootDir = processingEnv.getOptions().get("root_project_dir");

        // 获取所有标记了 @Destination 注解的 类的信息
        Set<Element> allDestinationElements = (Set<Element>)
                roundEnvironment.getElementsAnnotatedWith(ModuleKoinAnnotation.class);

        System.out.println(TAG + " >>> all Destination elements count = "
                + allDestinationElements.size());

        // 当未收集到 @Destination 注解的时候，跳过后续流程
        if (allDestinationElements.size() < 1) {
            return false;
        }

        // 将要自动生成的类的类名
        String className = "KoinModuleMapping_" + System.currentTimeMillis();

        StringBuilder builder = new StringBuilder();

        builder.append("package com.xianyu.androidfm;\n\n");
        builder.append("import java.util.HashMap;\n");
        builder.append("import java.util.Map;\n");
        builder.append("public class ").append(className).append(" {\n\n");
        builder.append("    public static Map<String,String> get() {\n");
        builder.append("        Map<String,String> map = new HashMap<>();\n");

        for (Element element : allDestinationElements) {
            final TypeElement typeElement = (TypeElement) element;

            // 尝试在当前类上，获取 @Destination 的信息
            final ModuleKoinAnnotation destination =
                    typeElement.getAnnotation(ModuleKoinAnnotation.class);
            if (destination == null) {
                continue;
            }

            final String url = destination.path();
            final String realPath = typeElement.getQualifiedName().toString();

            System.out.println(TAG + " >>> url = " + url);

            builder.append("        map.put(")
                    .append("\"").append(url).append("\"")
                    .append(",")
                    .append("\"").append(realPath).append("\"")
                    .append(");\n");
        }

        builder.append("        return map;\n");
        builder.append("    }\n");
        builder.append("}\n");


        String mappingFullClassName = "com.xianyu.androidfm." + className;

        System.out.println(TAG + " >>> mappingFullClassName = "
                + mappingFullClassName);

        System.out.println(TAG + " >>> class content = \n" + builder);

        // 写入自动生成的类到本地文件中
        try {
            JavaFileObject source = processingEnv.getFiler()
                    .createSourceFile(mappingFullClassName);
            Writer writer = source.openWriter();
            writer.write(builder.toString());
            writer.flush();
            writer.close();
        } catch (Exception ex) {
            throw new RuntimeException("Error while create file", ex);
        }

//        // 写入JSON到本地文件中
//
//        // 检测父目录是否存在
//        File rootDirFile = new File(rootDir);
//        if (!rootDirFile.exists()) {
//            throw new RuntimeException("root_project_dir not exist!");
//        }
//
//        // 创建 router_mapping 子目录
//        File routerFileDir = new File(rootDirFile, "router_mapping");
//        if (!routerFileDir.exists()) {
//            routerFileDir.mkdir();
//        }
//
//        File mappingFile = new File(routerFileDir,
//                "mapping_" + System.currentTimeMillis() + ".json");
//
//        // 写入json内容
//        try {
//            BufferedWriter out = new BufferedWriter(new FileWriter(mappingFile));
//            String jsonStr = destinationJsonArray.toString();
//            out.write(jsonStr);
//            out.flush();
//            out.close();
//        } catch (Throwable throwable) {
//            throw new RuntimeException("Error while writing json", throwable);
//        }
//
//        System.out.println(TAG + " >>> process finish.");

        return false;
    }

    /**
     * 告诉编译器，当前处理器支持的注解类型
     *
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(
                ModuleKoinAnnotation.class.getCanonicalName()
        );
    }
}
