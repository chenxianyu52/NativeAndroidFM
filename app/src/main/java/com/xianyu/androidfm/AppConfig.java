package com.xianyu.androidfm;

import android.content.res.AssetManager;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xianyu.androidfm.model.BottomBar;
import com.xianyu.androidfm.model.Destination;
import com.xianyu.basemodule.utils.AppGlobals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class AppConfig {
    private static HashMap<String, Destination> sDestConfig;
    private static BottomBar sBottomBar;


    public static HashMap<String, Destination> getDestConfig() {
        if (sDestConfig == null) {
            sDestConfig = new HashMap<>();
            List<String> content = parseNavFile();
            for (String json : content) {
                HashMap<String, Destination> destinationHashMap = JSON.parseObject(json, new TypeReference<HashMap<String, Destination>>() {
                });
                sDestConfig.putAll(destinationHashMap);
            }
        }
        return sDestConfig;
    }

    public static BottomBar getBottomBarConfig() {
        if (sBottomBar == null) {
            String content = parseFile("main_tabs_config.json");
            sBottomBar = JSON.parseObject(content, BottomBar.class);
        }
        return sBottomBar;
    }


    private static String parseFile(String fileName) {
        AssetManager assets = AppGlobals.getApplication().getAssets();
        InputStream is = null;
        BufferedReader br = null;
        StringBuilder builder = new StringBuilder();
        try {
            is = assets.open(fileName);
            br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (Exception e) {

            }
        }

        return builder.toString();
    }

    /**
     * 解析assets下的所有的导航相关的文件
     */
    private static List<String> parseNavFile(){
        ArrayList jsons = new ArrayList();
        AssetManager assets = AppGlobals.getApplication().getResources().getAssets();
        String[] list = null;
        try {
            list = assets.list("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (list != null) {
            for (String item: list){
                Log.i("cxy3",item);
                if(item.contains("_nav")){
                    jsons.add(parseFile(item));
                }
            }
        }
        return jsons;
    }
}
