package com.shuyi.lzqmvp.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import androidx.annotation.StringRes;

import com.shuyi.lzqmvp.BuildConfig;
import com.shuyi.lzqmvp.app.MyApp;


/**
 * toast 信息显示
 * 覆盖上次提示
 */
public final class ToastUtils {
    private static boolean DEBUG = BuildConfig.DEBUG;

    public static void setDebug(boolean debug) {
        DEBUG = debug;
    }

    public static boolean isDebug() {
        return DEBUG;
    }

    private static Toast mToast = null;
    private static Handler mHandler = new Handler(Looper.getMainLooper());

    public static void show(@StringRes int resId) {
        show(MyApp.getContext(), MyApp.getContext().getText(resId), Toast.LENGTH_SHORT);
    }

    public static void show(String msg) {
        show(MyApp.getContext(), msg, Toast.LENGTH_SHORT);
    }

    public static void showLong(String msg) {
        show(MyApp.getContext(), msg, Toast.LENGTH_LONG);
    }

    public static void show(Context context, @StringRes int resId) {
        show(context, context.getText(resId), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, CharSequence text) {
        show(context, text, Toast.LENGTH_SHORT);
    }

    public static void show(Context context, String format, Object... args) {
        show(context, String.format(format, args));
    }

    public static void show(Context context, @StringRes int resId, int duration) {
        show(context, context.getText(resId), duration);
    }

    public static void show(Context context, @StringRes int resId, Object... args) {
        show(context, context.getString(resId, args));
    }

    public static void show(final Context context, final CharSequence text, final int duration) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        final Context appContext = context.getApplicationContext();
        mHandler.post(new Runnable() {
            @SuppressLint("ShowToast")
            @Override
            public void run() {
                if (null == mToast) {
                    mToast = Toast.makeText(appContext, text, duration);
                    mToast.setGravity(Gravity.CENTER, 0, 0);
                } else {
                    mToast.setText(text);
                    mToast.setDuration(duration);
                }
                mToast.show();
            }
        });
    }

    /**
     * 取消 cancel
     */
    public static void cancel() {
        if (null != mToast) {
            mToast.cancel();
        }
    }

    public static void showDebug(Context context, String text) {
        if (DEBUG) {
            show(context, "debug:" + text);
        }
    }

    public static void showDebug(Context context, @StringRes int resId) {
        if (DEBUG) {
            show(context, "debug:" + context.getText(resId));
        }
    }

    public static void showDebug(Context context, String format, Object... args) {
        if (DEBUG) {
            show(context, "debug:" + String.format(format, args));
        }
    }

    public static void showDebug(Context context, @StringRes int resId, Object... args) {
        if (DEBUG) {
            show(context, "debug:" + context.getString(resId, args));
        }
    }
}
