package com.shuyi.lzqmvp.baseMVP.base;

import com.shuyi.lzqmvp.baseMVP.mvp.BasePresenter;
import com.shuyi.lzqmvp.baseMVP.mvp.BaseView;

/**
 * created by Lzq
 * on 2021/2/19 0019
 * Describe ：
 */
public abstract class BasePresenters<V extends BaseView> implements BasePresenter<V> {

    protected V mView;

    /**
     * 绑定view，一般在初始化中调用该方法
     *
     * @param view view
     */
    @Override
    public void attachView(V view) {
        this.mView = view;
    }

    /**
     * 解除绑定view，一般在onDestroy中调用
     */
    @Override
    public void detachView() {

        this.mView = null;
    }

    /**
     * View是否绑定
     *
     * @return
     */
    @Override
    public boolean isViewAttached() {
        return mView != null;
    }

    /**
     * 获取view
     *
     * @return
     */
    @Override
    public V getView() {
        return mView;
    }
}
