package com.shuyi.lzqmvp.baseMVP.base;

import androidx.lifecycle.Lifecycle;

import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

/**
 * created by Lzq
 * on 2020/11/10 0010
 * Describe ：
 */
public abstract class BaseMvpFragment<T extends BasePresenters> extends BaseFragment {

    protected T mPresenter;

    private boolean isFirstLoad = true; // 是否第一次加载

    @Override
    public void onResume() {
        if (isFirstLoad) {
            lazyLoad();
            isFirstLoad = false;
        }
        super.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    /**
     * 不是第一次加载时.
     */
    protected abstract void lazyLoad();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroyView();
    }


}
