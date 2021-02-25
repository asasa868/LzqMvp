package com.shuyi.lzqmvp;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.shuyi.lzqmvp.Utils.LogUtil;
import com.shuyi.lzqmvp.Utils.PermissionsUtils;
import com.shuyi.lzqmvp.baseMVP.base.BaseActivity;
import com.uber.autodispose.AutoDisposeConverter;

public class MainActivity extends BaseActivity {

    private String[] permissions = new String[]{Manifest.permission.WRITE_CALENDAR, Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.LOCATION_HARDWARE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {


    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initPresenters() {

    }



    @Override
    public <T> AutoDisposeConverter<T> bindAutoDispose() {
        return null;
    }
}