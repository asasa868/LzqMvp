package com.shuyi.lzqmvp.baseMVP.mvp;

/**
 * created by Lzq
 * on 2021/2/19 0019
 * Describe ：
 */
public interface BasePresenter<V> {

    /**
     * 绑定view，一般在初始化中调用该方法
     *
     * @param view view
     */
    void attachView(V view);

    /**
     * 解除绑定view，一般在onDestroy中调用
     */
    void detachView();

    /**
     * View是否绑定
     *
     * @return
     */
    boolean isViewAttached();

    /**
     * 获取view
     *
     * @return
     */
    V getView();
}
