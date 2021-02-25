package com.shuyi.lzqmvp.baseMVP.net;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;

import com.shuyi.lzqmvp.BuildConfig;
import com.shuyi.lzqmvp.api.APIService;
import com.shuyi.lzqmvp.baseMVP.base.BaseBean;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * created by Lzq
 * on 2021/2/19 0019
 * Describe ：
 */
public class RetrofitClient {
    private static volatile RetrofitClient instance;

    private APIService apiService;

    //超时时间
    private static final int DEFAULT_TIMEOUT = 30;

    private RetrofitClient() {


    }

    public static RetrofitClient getInstance() {
        if (instance == null) {
            synchronized (RetrofitClient.class) {
                if (instance == null) {
                    instance = new RetrofitClient();
                }
            }
        }
        return instance;
    }

    public APIService getApi() {
        //初始化一个client,不然retrofit会自己默认添加一个
        OkHttpClient client = new OkHttpClient().newBuilder()
                //设置Header
                .addInterceptor(getHeaderInterceptor())
                //设置拦截器
                .addInterceptor(getInterceptor())
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                //设置网络请求的Url地址
                .baseUrl(BuildConfig.HttpUrl)
                //设置数据解析器
                .addConverterFactory(MyGsonConverterFactory.create())
                //设置网络请求适配器，使其支持RxJava与RxAndroid
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        //创建—— 网络请求接口—— 实例
        apiService = retrofit.create(APIService.class);
        return apiService;
    }

    /**
     * 设置Header
     *
     * @return
     */
    private Interceptor getHeaderInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder;
                requestBuilder = original.newBuilder();
//                添加Token
//                        .header("client", "Android")
//                        .header("uid", SharedManager.getStringFlag(SharedKey.UID))
//                        .header("token", SharedManager.getStringFlag(SharedKey.TOKEN))
//                        .header("deviceType", DeviceInfoUtil.getInstance().getPhoneModel())
//                        .header("deviceName", DeviceInfoUtil.getInstance().getPhoneModel())
//                        .header("version", EquipmentUtil.getAppVersionCode(MyApp.getContext()))
//                        .header("clientType", "Android")
//                        .header("uuid", EquipmentUtil.getAndroidId(MyApp.getContext()));

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };

    }

    /**
     * 设置拦截器
     *
     * @return
     */
    private Interceptor getInterceptor() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        //显示日志
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return interceptor;
    }


    public interface OnSuccessListener<T> {
        void onSuccess(String code, String msg, String data);
    }

    public interface onFailListener {
        void onFailed(String msg);
    }


    public static void postObservable(Activity activity, Observable<BaseBean> observable, @Nullable final OnSuccessListener successListener, @Nullable final onFailListener failListener) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) activity)))
                .subscribe(new BaseObserver<>(new BaseObserver.ResponseCallBack<BaseBean>() {
                    @Override
                    public void onSuccess(BaseBean baseBean) {
                        if (successListener != null) {
                            successListener.onSuccess(baseBean.getCode(), baseBean.getMsg(), baseBean.getData().toString());
                        }
                    }

                    @Override
                    public void onError(String msg) {
                        if (failListener != null) {
                            failListener.onFailed(msg);
                        }
                    }
                }));


    }

}
