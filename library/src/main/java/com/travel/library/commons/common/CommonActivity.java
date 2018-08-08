package com.travel.library.commons.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.travel.library.base.BaseActivity;
import com.travel.library.commons.mvp.BaseModel;
import com.travel.library.commons.mvp.BasePresenter;
import com.travel.library.utils.ObjectGetByClassUtils;
import com.travel.library.R;

/**
 * Created by Wisn on 2018/5/13 上午10:04.
 */
public class CommonActivity<T extends BaseModel, E extends BasePresenter> extends BaseActivity{
    public T mModel;
    public E mPresenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mModel = ObjectGetByClassUtils.getClass(this, 0);
        mPresenter = ObjectGetByClassUtils.getClass(this, 1);
        if (mModel != null && mPresenter != null) {
            mPresenter.setMV(mModel, this);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public int bindLayout() {
        return R.layout.lib_swipeback_layout;
    }

    @Override
    public void initView(Activity activity) {

    }

    @Override
    public void initData(Context context) {

    }

  /*  @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {//判断其他Activity启动本Activity时传递来的intent是否为空
            //获取intent中对应Tag的布尔值
            boolean isExist = intent.getBooleanExtra(EXIST, false);
            //如果为真则退出本Activity
            if (isExist) {
                this.finish();
            }
        }
    }

    public static void ExitAllActivity(Context context) {
        Intent intent = new Intent(context, CommonActivity.class);
        intent.putExtra(CommonActivity.EXIST, true);
        context.startActivity(intent);

    }*/

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.onDestroy();
    }

}
