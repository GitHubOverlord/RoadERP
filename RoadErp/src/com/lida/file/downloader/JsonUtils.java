
package com.lida.file.downloader;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtils {
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    
    
    
    public static <T> T fromJson(String json, Class<T> clazz) {
        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat(DEFAULT_DATE_PATTERN);
        Gson gson = builder.create();
        return gson.fromJson(json, clazz);
    }
    
    public static <T> List<T> fromList(String json, Type type) {
        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat(DEFAULT_DATE_PATTERN);
        Gson gson = builder.create();
        return gson.fromJson(json, type);
    }
}
