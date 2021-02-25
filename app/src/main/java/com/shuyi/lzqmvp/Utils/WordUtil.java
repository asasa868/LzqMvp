package com.shuyi.lzqmvp.Utils;

import android.content.Context;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

import androidx.core.content.ContextCompat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * created by Lzq
 * on 2020/12/5 0005
 * Describe ：文字变色工具类
 */
public class WordUtil {

    /**
     * 关键字高亮变色
     *
     * @param context 上下文
     * @param color   变化的色值
     * @param text    文字
     * @param keyword 文字中的关键字
     * @return 结果SpannableString
     */
    public static SpannableString matcherSearchTitle(Context context, int color, String text, String keyword) {
        SpannableString s = new SpannableString(text);
        keyword = escapeExprSpecialWord(keyword);
        text = escapeExprSpecialWord(text);
        if (text.contains(keyword) && !TextUtils.isEmpty(keyword)) {
            try {
                Pattern p = Pattern.compile(keyword);
                Matcher m = p.matcher(s);
                while (m.find()) {
                    int start = m.start();
                    int end = m.end();
                    //注意：请使用ContextCompat.getColor获取颜色值。
                    s.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, color)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            } catch (Exception e) {
                Log.e("错误：", e.toString());
            }
        }
        return s;
    }

    /**
     * 转义正则特殊字符 （$()*+.[]?\^{},|）
     *
     * @param keyword
     * @return keyword
     */
    public static String escapeExprSpecialWord(String keyword) {
        if (!TextUtils.isEmpty(keyword)) {
            String[] fbsArr = {"\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|"};
            for (String key : fbsArr) {
                if (keyword.contains(key)) {
                    keyword = keyword.replace(key, "\\" + key);
                }
            }
        }
        return keyword;
    }

    /**
     * 通过Html.fromHtml方式修改颜色(解决SDK版本问题)
     *
     * @param html
     * @return
     */
    public static Spanned fromHtml(String html) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

    /**
     * 字体颜色修改
     *
     * @param content
     * @param color
     * @return
     */
    public static String color(String content, int color) {
        return "<font color=\"#" + Integer.toHexString(color) + "\" >" + content + "</font>";
    }

    /**
     * 加粗字体
     *
     * @param content
     * @return
     */
    public static String bold(String content) {
        return "<b>" + content + "</b>";
    }


    public static String intToString(String data) {

        double uidDouble = Double.parseDouble(data);
        int uidInt = (int) uidDouble;
        String uidString = String.valueOf(uidInt);

        return uidString;
    }
}
