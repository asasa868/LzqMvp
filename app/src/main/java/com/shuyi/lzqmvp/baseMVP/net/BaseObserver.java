package com.shuyi.lzqmvp.baseMVP.net;

import android.util.Log;

import com.shuyi.lzqmvp.Utils.ToastUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLHandshakeException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * created by Lzq
 * on 2021/2/24 0024
 * Describe ：
 */
public class BaseObserver<T> implements Observer<T> {
    private Disposable disposable;
    private ResponseCallBack callBack;


    public BaseObserver(ResponseCallBack responseCallBack) {
        this.callBack = responseCallBack;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        this.disposable = d;
    }

    @Override
    public void onNext(@NonNull T t) {
        callBack.onSuccess(t);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        try {

            if (e instanceof SocketTimeoutException) {//请求超时
                ToastUtils.show("请求超时,请稍后再试");
            } else if (e instanceof ConnectException) {//网络连接超时
                ToastUtils.show("网络连接超时,请检查网络状态");
            } else if (e instanceof SSLHandshakeException) {//安全证书异常
                ToastUtils.show("安全证书异常");
            } else if (e instanceof HttpException) {//请求的地址不存在
                int code = ((HttpException) e).code();
                if (code == 504) {
                    ToastUtils.show("网络异常，请检查您的网络状态");
                } else if (code == 404) {
                    ToastUtils.show("请求的地址不存在");
                } else {
                    ToastUtils.show("请求失败");
                }
            } else if (e instanceof UnknownHostException) {//域名解析失败
                ToastUtils.show("域名解析失败");
            } else {
                callBack.onError(e.getMessage());
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        } finally {
            Log.e("OnSuccessAndFaultSub", "error:" + e.getMessage());
            if (disposable != null && !disposable.isDisposed()) { //事件完成取消订阅
                disposable.dispose();
            }

        }

    }

    @Override
    public void onComplete() {
        if (disposable != null && !disposable.isDisposed()) { //事件完成取消订阅
            disposable.dispose();
        }
    }


    public interface ResponseCallBack<T> {
        void onSuccess(T t);

        void onError(String msg);
    }
}
