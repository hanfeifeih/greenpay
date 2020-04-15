package com.esiran.greenpay.common.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.*;

public class MapUtil {
    private static final Gson g = new Gson();
    public static <T> Map<String,String> entity2map(T t){
        String json = g.toJson(t);
        return g.fromJson(json,new TypeToken<Map<String,String>>(){}.getType());
    }
    public static String sortAndSerialize(Map<String,String> params, String[] excludeKeys){
        Set<String> keys = params.keySet();
        List<String> keyList = new ArrayList<>(keys);
        Collections.sort(keyList);
        StringBuilder sb = new StringBuilder();
        List<String> excludeKeyList = excludeKeys==null?new ArrayList<>()
                :Arrays.asList(excludeKeys);
        for (int i=0; i<keyList.size(); i++){
            String key = keyList.get(i);
            if (excludeKeyList.contains(key))
                continue;
            sb.append(key).append("=")
                    .append(params.get(key))
                    .append(i<keyList.size()-1?"&":"");
        }
        return sb.toString();
    }
}
