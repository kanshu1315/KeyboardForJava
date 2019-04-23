package com.kanshu.keyboard.util;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

public final class GsonHelper {


    private static final Gson mGson = new Gson();


    /**
     * 把一个json的字符串转换成为一个包含POJO对象的List
     */
    public static <T> List<T> fromJson(String string, Class<T[]> cls) {
        Gson gson = new Gson();
        T[] array = gson.fromJson(string, cls);
        return Arrays.asList(array);
    }


}
