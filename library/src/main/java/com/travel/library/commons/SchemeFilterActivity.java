package com.travel.library.commons;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.travel.library.base.BaseActivity;
import com.travel.library.commons.constants.ARoutePath;

/**
 * Created by Wisn on 2018/5/7 下午1:27.
 * 接收所有Schema过来跳转
 */
@Route(path = ARoutePath.App.SchemeFilterActivity)
public class SchemeFilterActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Uri uri = getIntent().getData();
        ARouter.getInstance().build(uri).navigation(this, new NavCallback() {
            @Override
            public void onArrival(Postcard postcard) {
                finish();
            }
        });

    }

    @Override
    public int bindLayout() {
        return 0;
    }

    @Override
    public void initView(Activity activity) {

    }

    @Override
    public void initData(Context context) {

    }
}
