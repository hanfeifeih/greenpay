package com.esiran.greenpay.common.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

public class MapUtil {
    private static final Gson g = new Gson();
    public static <T> Map<String,String> entity2map(T t){
        String json = g.toJson(t);
        return g.fromJson(json,new TypeToken<Map<String,String>>(){}.getType());
    }
}
