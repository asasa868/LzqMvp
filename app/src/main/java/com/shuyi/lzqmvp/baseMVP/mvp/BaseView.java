package com.shuyi.lzqmvp.baseMVP.mvp;

import com.uber.autodispose.AutoDisposeConverter;

/**
 * created by Lzq
 * on 2021/2/19 0019
 * Describe ：
 */
public interface BaseView {

    /**
     * 显示加载中
     */
    void showLoading(String string);

    /**
     * 隐藏加载
     */
    void hideLoading();

    /**
     * 数据获取失败
     *
     * @param string
     */
    void onError(String string);

    /**
     * 初始化View控件
     */
    void initBaseViews();

    /**
     * 初始化数据
     */
    void initBaseData();
    /**
     * 绑定presenter
     */
    void initPresenter();
    /**
     * 绑定Android生命周期 防止RxJava内存泄漏
     *
     * @param <T>
     * @return
     */
    <T> AutoDisposeConverter<T> bindAutoDispose();
}
