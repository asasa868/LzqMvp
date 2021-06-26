package com.shuyi.lzqmvp.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.shuyi.lzqmvp.R;

import jp.wasabeef.glide.transformations.BitmapTransformation;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropSquareTransformation;
import jp.wasabeef.glide.transformations.CropTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import jp.wasabeef.glide.transformations.gpu.BrightnessFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.PixelationFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SepiaFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SketchFilterTransformation;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.util.concurrent.ExecutionException;

import jp.wasabeef.glide.transformations.BitmapTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by ：Lzq
 * User: android
 * Date: 2021/6/23
 * Time: 10:59
 * Description: glide工具类
 * 使用图片加载
 * 首先这里需要自己手动配置 缓存的错误图 和 占位图 请查看  {@link #onErrorImg}方法, 和{@link #onLoadingImg}方法
 * 其次 如果需要配置缓存 直接查看 420 - 450行代码、
 */
public class GlideUtil {


    /**
     * 普通加载图片
     *
     * @param context 上下文
     * @param o       图片地址  包括Bitmap，Drawable文件夹下,url,uri,File文件，字节流，object
     * @param view    加载文件的图标
     */
    public static void loadImg(Context context, Object o, ImageView view) {
        if (o instanceof Bitmap) {
            Glide.with(context).load(o).into(view);
        } else if (o instanceof Drawable) {
            Glide.with(context).load(o).into(view);
        } else if (o instanceof String) {
            Glide.with(context).load(o).into(view);
        } else if (o instanceof Uri) {
            Glide.with(context).load(o).into(view);
        } else if (o instanceof File) {
            Glide.with(context).load(o).into(view);
        } else if (o instanceof byte[]) {
            Glide.with(context).load(o).into(view);
        } else {
            Glide.with(context).load(o).into(view);
        }
    }


    /**
     * 有占位图和加载失败图加载图片
     * 不限制加载类型
     *
     * @param context 上下文
     * @param o       图片地址  包括Bitmap，Drawable文件夹下,url,uri,File文件，字节流，object
     * @param view    加载文件的图标
     */
    public static void loadImg2(Context context, Object o, ImageView view) {
        Glide.with(context)
                .load(o)
                .apply(initOptions())
                .into(view);
    }


    /**
     * 有占位图和加载失败图加载图片
     * 不限制加载类型
     *
     * @param context        上下文
     * @param o              图片地址  包括Bitmap，Drawable文件夹下,url,uri,File文件，字节流，object
     * @param view           加载文件的图标
     * @param transformation 自定义的图片变换规则
     */
    public static void loadImg3(Context context, Object o, ImageView view, BitmapTransformation transformation) {
        Glide.with(context)
                .load(o)
                .apply(initOptions(transformation))
                .into(view);
    }

    /**
     * 有占位图和加载失败图加载图片
     * 不限制加载类型
     *
     * @param context 上下文
     * @param o       图片地址  包括Bitmap，Drawable文件夹下,url,uri,File文件，字节流，object
     * @param view    加载文件的图标
     * @param options 自定义的图片属性设置
     */
    public static void loadImg4(Context context, Object o, ImageView view, RequestOptions options) {
        Glide.with(context)
                .load(o)
                .apply(options)
                .into(view);
    }

    /**
     * 有占位图和加载失败图加载图片
     * 不限制加载类型
     *
     * @param context 上下文
     * @param o       图片地址  包括Bitmap，Drawable文件夹下,url,uri,File文件，字节流，object
     * @param view    加载文件的图标
     * @param radius  圆角
     */
    public static void loadRadiusImg(Context context, Object o, ImageView view, int radius) {
        Glide.with(context)
                .load(o)
                .apply(initOptions(new RoundedCornersTransformation(radius, 0)))
                .into(view);
    }


    /**
     * 有占位图和加载失败图加载图片
     * 只能加载静态图
     *
     * @param context 上下文
     * @param o       图片地址  包括Bitmap，Drawable文件夹下,url,uri,File文件，字节流，object
     * @param view    加载文件的图标
     */
    public static void loadBitmapImg(Context context, Object o, ImageView view) {
        Glide.with(context)
                .asBitmap()
                .load(o)
                .apply(initOptions())
                .into(view);
    }

    /**
     * 有占位图和加载失败图加载图片
     * 只能加载gif
     *
     * @param context 上下文
     * @param o       图片地址  包括Bitmap，Drawable文件夹下,url,uri,File文件，字节流，object
     * @param view    加载文件的图标
     */
    public static void loadGifImg(Context context, Object o, ImageView view) {
        Glide.with(context)
                .asGif()
                .load(o)
                .apply(initOptions())
                .into(view);
    }

    /**
     * 有占位图和加载失败图加载图片
     * 只能加载文件
     *
     * @param context 上下文
     * @param o       图片地址  包括Bitmap，Drawable文件夹下,url,uri,File文件，字节流，object
     * @param view    加载文件的图标
     */
    public static void loadFileImg(Context context, Object o, ImageView view) {
        Glide.with(context)
                .asFile()
                .load(o)
                .apply(initOptions())
                .into(view);
    }

    /**
     * 有占位图和加载失败图加载图片
     * 只能加载Drawable
     *
     * @param context 上下文
     * @param o       图片地址  包括Bitmap，Drawable文件夹下,url,uri,File文件，字节流，object
     * @param view    加载文件的图标
     */
    public static void loadDrawableImg(Context context, Object o, ImageView view) {
        Glide.with(context)
                .asDrawable()
                .load(o)
                .apply(initOptions())
                .into(view);
    }


    /**
     * 有占位图和加载失败图加载图片
     * 加载指定大小的图片
     *
     * @param context 上下文
     * @param o       图片地址  包括Bitmap，Drawable文件夹下,url,uri,File文件，字节流，object
     * @param view    加载文件的图标
     * @param size    指定宽高的正方形
     */
    public static void loadSpecifySizeImg(Context context, Object o, ImageView view, int size) {
        Glide.with(context)
                .load(o)
                .apply(initOptions())
                .override(size)
                .into(view);
    }

    /**
     * 有占位图和加载失败图加载图片
     * 加载指定大小的图片
     *
     * @param context 上下文
     * @param o       图片地址  包括Bitmap，Drawable文件夹下,url,uri,File文件，字节流，object
     * @param view    加载文件的图标
     * @param width   指定长度的宽
     * @param height  指定长度的高
     */
    public static void loadSpecifySizeImg(Context context, Object o, ImageView view, int width, int height) {
        Glide.with(context)
                .load(o)
                .apply(initOptions())
                .diskCacheStrategy(isCache())
                .override(width, height)
                .into(view);
    }

    /**
     * 如果图片的url 上需要校验 token 等，这时候glide的缓存就会失效
     * 这时候就可以继承glideUrl类 重写getCacheKey方法，推荐只保留 url
     * 这样子就可以合理地使用缓存
     */
    static class MyGlideUrl extends GlideUrl {

        private String mUrl;

        public MyGlideUrl(String url) {
            super(url);
            this.mUrl = url;
        }

        @Override
        public String getCacheKey() {
            return mUrl.replace(findParam(), "");
        }

        /**
         * 具体要如何存取 缓存的key 的逻辑可在这里实现
         * 如：url== http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x1080.jpg?token=aaaaaaaaaaaaaaaa
         *
         * @return 需要替换的字符
         */
        private String findParam() {
            String param = "";
            //缓存的key 具体实现逻辑
            int tokenKeyIndex = mUrl.indexOf("?token=") >= 0 ? mUrl.indexOf("?token=") : -1;
            if (tokenKeyIndex != -1) {
                int nextAndIndex = mUrl.indexOf("&", tokenKeyIndex + 1);
                if (nextAndIndex != -1) {
                    param = mUrl.substring(tokenKeyIndex, nextAndIndex + 1);
                } else {
                    param = mUrl.substring(tokenKeyIndex);
                }
            }
            return param;
        }
    }

    /**
     * 如果我们要进行自定义的话，通常只需要在两种Target的基础上去自定义就可以了，一种是CustomTarget，一种是CustomViewTarget。
     * 第一种CustomTarget
     * 自定义必需要重写两个方法，
     * onResourceReady方法可以获取到加载目标图片的对象，在进行操作。
     * onLoadCleared 生命周期回调，在取消加载并释放其资源时调用。
     * 使用的话，直接在into方法中传入CustomTarget对象就可以了
     * 它有两个构造函数，第一种直接返回的图片对象是原始大小，第二种需要指定宽高，返回的对象就是指定后的大小
     * 第一种使用过多可能会造成oom ，所以会推荐使用第二种居多。
     * 此Target用于是imageView的情况
     */

    CustomTarget<Drawable> customTarget = new CustomTarget<Drawable>() {
        @Override
        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

        }

        @Override
        public void onLoadCleared(@Nullable Drawable placeholder) {

        }
    };


    /**
     * 第二种CustomViewTarget
     * 自定义必需要重写三个方法，
     * onResourceReady方法可以获取到加载目标图片的对象，在进行操作。
     * onResourceCleared 生命周期回调，在取消加载并释放其资源时调用。
     * onLoadFailed 加载失败时候的回调，可进行失败操作
     * 在构造函数中，必须传入一个控件，此控件必须是继承view的
     * 在构造函数中泛型，第一个值的是要操作的view的类，地址个是加载图片的类型.
     * 此Target用于不是imageView的情况
     * CustomViewTarget<View,Object> customViewTarget=new CustomViewTarget<View, Object>() {
     *
     * @Override protected void onResourceCleared(@Nullable Drawable placeholder) {
     * <p>
     * }
     * @Override public void onLoadFailed(@Nullable Drawable errorDrawable) {
     * <p>
     * }
     * @Override public void onResourceReady(@NonNull Object resource, @Nullable Transition<? super Object> transition) {
     * <p>
     * }
     * };
     */


    /**
     * 预加载图片，缓存策略需要一样。不然可能会造成 重复下载的情况
     *
     * @param context  上下文
     * @param url      图片地址
     * @param listener 缓存监听  缓存成功，还是失败
     */
    public static void preloadImg(Context context, String url, RequestListener<Drawable> listener) {
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(isCache())
                .listener(listener)
                .preload();

    }

    /**
     * 下载图片 ，需要注意的是 它需要在子线程中才能使用。
     *
     * @param context 上下文
     * @param url     图片地址
     * @return 图片文件
     */
    public static File submitImg(Context context, String url) {
        try {
            File imgFile = null;
            FutureTarget<File> target = Glide.with(context)
                    .asFile()
                    .load(url)
                    .submit();


            imgFile = target.get();

            return imgFile;
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 初始化设置，包括加载中图片，加载错误图片
     *
     * @param transformation 自定义的图片变换规则，可配合glide-transformations库使用
     * @return RequestOptions
     */
    private static RequestOptions initOptions(BitmapTransformation transformation) {
        return new RequestOptions()
                .transform(transformation)
                .error(onErrorImg())
                .placeholder(onLoadingImg())
                .skipMemoryCache(isSkipMemoryCache())//是否允许内存缓存
                .onlyRetrieveFromCache(isSkipRetrieveCache())//是否只从缓存加载图片
                .diskCacheStrategy(isCache());//磁盘缓存策略
    }

    /**
     * 初始化设置，包括加载中图片，加载错误图片
     *
     * @return RequestOptions
     */
    private static RequestOptions initOptions() {
        return new RequestOptions()
                .error(onErrorImg())
                .placeholder(onLoadingImg())
                .skipMemoryCache(isSkipMemoryCache())//是否允许内存缓存
                .onlyRetrieveFromCache(isSkipRetrieveCache())//是否只从缓存加载图片
                .centerCrop()
                .diskCacheStrategy(isCache());//磁盘缓存策略
    }

    /**
     * @return 加载失败图片
     */
    private static int onErrorImg() {
        return  R.mipmap.ic_launcher;
    }


    /**
     * @return 加载中图片
     */
    private static int onLoadingImg() {
        return R.mipmap.ic_launcher;
    }




//-----------------------------------------------------------------缓存配置-----------------------------------------------------
    /**
     * DiskCacheStrategy.NONE	表示不缓存任何内容。
     * DiskCacheStrategy.DATA	表示只缓存原始图片。
     * DiskCacheStrategy.RESOURCE	表示只缓存转换过后的图片。
     * DiskCacheStrategy.ALL	表示既缓存原始图片，也缓存转换过后的图片。
     * DiskCacheStrategy.AUTOMATIC	表示让Glide根据图片资源智能地选择使用哪一种缓存策略（默认选项）。
     * @return 硬盘的缓存策略。
     */
    private static DiskCacheStrategy isCache() {
        return DiskCacheStrategy.AUTOMATIC;
    }

    /**
     * @return 是否跳过内存缓存
     * true 不缓存 false 缓存
     */
    private static boolean isSkipMemoryCache() {
        return false;
    }

    /**
     * @return 是否只从缓存加载图片
     * true 是 false 否
     */
    private static boolean isSkipRetrieveCache() {
        return false;
    }


//-----------------------------------------------------------------缓存配置-----------------------------------------------------

    /**
     * @describe 清除内容缓存
     */
    public static void clearMemory(Context context) {
        Glide.get(context).clearMemory();
    }

    /**
     * @describe 清除磁盘缓存
     */
    public static void clearDiskCache(Context context) {
        Glide.get(context).clearDiskCache();
    }

}
