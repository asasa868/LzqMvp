package com.shuyi.lzqmvp.baseMVP.base;

import androidx.lifecycle.Lifecycle;

import com.shuyi.lzqmvp.baseMVP.mvp.BaseView;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

/**
 * created by Lzq
 * on 2021/2/19 0019
 * Describe ：
 */
public abstract class BaseMVPActivity<T extends BasePresenters> extends BaseActivity implements BaseView {

    protected T mPresenter;

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }

        super.onDestroy();
    }

    /**
     * 绑定生命周期 防止MVP内存泄漏
     *
     * @param <T>
     * @return
     */
    @Override
    public <T> AutoDisposeConverter<T> bindAutoDispose() {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider
                .from(this, Lifecycle.Event.ON_DESTROY));
    }
}
