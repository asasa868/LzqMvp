package com.shuyi.lzqmvp.baseMVP.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.shuyi.lzqmvp.Utils.ToastUtils;
import com.shuyi.lzqmvp.baseMVP.mvp.BaseView;
import com.shuyi.lzqmvp.ui.ShapeLoadingDialog;
import com.uber.autodispose.AutoDisposeConverter;

/**
 * created by Lzq
 * on 2020/11/10 0010
 * Describe ：
 */
public abstract class BaseFragment extends Fragment implements BaseView {

    private ShapeLoadingDialog shapeLoadingDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(attachLayoutId(), container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();

    }

    /**
     * 初始化布局
     *
     * @param root
     */
    protected abstract void initView(View root);

    /**
     * 初始化数据
     *
     * @throws NullPointerException
     */
    protected abstract void initData() throws NullPointerException;

    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    protected abstract int attachLayoutId();

    @Override
    public void showLoading(String string) {
        startLoading(string, getContext());
    }

    @Override
    public void hideLoading() {
        stopLoading();
    }

    @Override
    public void onError(String string) {
        ToastUtils.show(string);
        hideLoading();
    }

    protected void startLoading(String msg, Context context) {
        shapeLoadingDialog = new ShapeLoadingDialog.Builder(context).loadText(msg).build();
        shapeLoadingDialog.setCanceledOnTouchOutside(false);
        shapeLoadingDialog.setCancelable(false);
        shapeLoadingDialog.show();
    }

    protected void stopLoading() {
        if (shapeLoadingDialog != null) {
            shapeLoadingDialog.dismiss();
        }
    }



}
