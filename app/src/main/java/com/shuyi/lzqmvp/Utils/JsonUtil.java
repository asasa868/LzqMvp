package com.shuyi.lzqmvp.Utils;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * created by Lzq
 * on 2021/2/22 0022
 * Describe ：gson解析工具类
 */
public class JsonUtil {

    private Gson mGSon;
    private static JsonUtil sInstance;

    public static synchronized JsonUtil getInstance() {
        if (sInstance == null) {
            sInstance = new JsonUtil();
        }
        return sInstance;
    }

    private JsonUtil() {
        mGSon = new GsonBuilder().disableHtmlEscaping().create();
    }

    /**
     *
     * @param srcObj
     * @return obj转字符串
     */
    public String objToJsonStr(Object srcObj) {
        String result = "";
        if (mGSon == null) {
            mGSon = new GsonBuilder().disableHtmlEscaping().create();
        }
        try {
            result = mGSon.toJson(srcObj);
        } catch (Exception e) {
            Log.getStackTraceString(e);
        }
        return result;
    }

    /**
     *
     * @param src
     * @param classOfT
     * @param <T>
     * @return json字符串转json对象
     */
    public <T> T jsonStrToObj(String src, Class<T> classOfT) {
        if(TextUtils.isEmpty(src)){
            return null;
        }
        T result = null;
        if (mGSon == null) {
            mGSon = new GsonBuilder().disableHtmlEscaping().create();
        }
        try {
            result = mGSon.fromJson(src, classOfT);
        } catch (Exception e) {
            Log.getStackTraceString(e);
        }
        return result;
    }

    /**
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return json字符串转数组
     * @throws Exception
     */
    public <T> List<T> fromJsonArray(String json, Class<T> clazz) throws Exception {
        List<T> lst = new ArrayList<>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (final JsonElement elem : array) {
            lst.add(mGSon.fromJson(elem, clazz));
        }
        return lst;
    }
}
