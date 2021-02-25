package com.shuyi.lzqmvp.baseMVP.base;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.shuyi.lzqmvp.Utils.LogUtil;
import com.shuyi.lzqmvp.Utils.PermissionsUtils;
import com.shuyi.lzqmvp.Utils.ToastUtils;
import com.shuyi.lzqmvp.baseMVP.mvp.BaseView;
import com.shuyi.lzqmvp.baseMVP.net.EventType;
import com.shuyi.lzqmvp.ui.ShapeLoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * created by Lzq
 * on 2021/2/19 0019
 * Describe ：
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    private ShapeLoadingDialog shapeLoadingDialog;

    //！！！！需要在清单列表加上需要的权限
    private String[] permissions = new String[]{ Manifest.permission.CAMERA};


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(EventType event) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取权限
        PermissionsUtils.showSystemSetting = true;
        PermissionsUtils.getInstance().checkPermissions(this, permissions, new PermissionsUtils.IPermissionsResult() {

            @Override
            public void passPermissions() {
                LogUtil.d( "权限通过!");
            }

            @Override
            public void forBitPermissions() {
                LogUtil.d("权限不通过!");
            }
        });
        //限制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //添加布局
        setContentView(getLayoutID());
        //注册异步通讯
        EventBus.getDefault().register(this);

        initBaseViews();
        initBaseData();
        initPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void showLoading(String string) {
        startLoading(string);
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

    @Override
    public void initBaseViews() {
        initView();
    }

    @Override
    public void initBaseData() {
        initData();
    }

    @Override
    public void initPresenter() {
        initPresenters();
    }

    /**
     * 设置布局
     */
    protected abstract int getLayoutID();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化presenter
     */
    protected abstract void initPresenters();


    private void startLoading(String msg) {
        shapeLoadingDialog = new ShapeLoadingDialog.Builder(this).loadText(msg).build();
        shapeLoadingDialog.setCanceledOnTouchOutside(true);
        shapeLoadingDialog.show();
    }

    private void stopLoading() {
        if (shapeLoadingDialog != null) {
            shapeLoadingDialog.dismiss();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //将结果转发给Permissions
        PermissionsUtils.getInstance().onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }


    /**
     * 点击空白区域隐藏键盘.
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent me) {
        if (me.getAction() == MotionEvent.ACTION_DOWN) {  //把操作放在用户点击的时候
            View v = getCurrentFocus();      //得到当前页面的焦点,ps:有输入框的页面焦点一般会被输入框占据
            if (isShouldHideKeyboard(v, me)) { //判断用户点击的是否是输入框以外的区域
                hideKeyboard(v.getWindowToken());   //收起键盘
            }
        }
        return super.dispatchTouchEvent(me);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {  //判断得到的焦点控件是否包含EditText
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],    //得到输入框在屏幕中上下左右的位置
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击位置如果是EditText的区域，忽略它，不收起键盘。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    //不跟随系统设置字体大小
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.fontScale != 1) { //fontScale不为1，需要强制设置为1
            getResources();
        }
    }

    //不跟随系统设置字体大小
    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        if (resources.getConfiguration().fontScale != 1) { //fontScale不为1，需要强制设置为1
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置成默认值，即fontScale为1
            resources.updateConfiguration(newConfig, resources.getDisplayMetrics());
        }
        return resources;
    }
}
