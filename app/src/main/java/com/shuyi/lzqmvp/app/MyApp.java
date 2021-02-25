package com.shuyi.lzqmvp.app;

import android.app.Application;
import android.util.Log;

import com.tencent.mmkv.MMKV;

/**
 * created by Lzq
 * on 2021/2/19 0019
 * Describe ：
 */
public class MyApp extends Application {

    private static MyApp myApplication;

    public static Application getContext() {

        return myApplication;

    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;

        // 设置初始化的根目录
        String dir = getFilesDir().getAbsolutePath() + "/mmkv_2";
        String rootDir = MMKV.initialize(dir);
        Log.i("MMKV", "mmkv root: " + rootDir);
    }
}
