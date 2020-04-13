package com.esiran.greenpay.common.util;

import java.util.List;

public class ListUtil {
    public static String list2str(List<String> data){
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<data.size(); i++){
            sb.append(data.get(i));
            sb.append(i<data.size()-1?",":"");
        }
        return sb.toString();
    }
}
