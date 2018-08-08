package com.travel.library.commons.common;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.travel.library.base.BaseFragment;
import com.travel.library.commons.mvp.BaseModel;
import com.travel.library.commons.mvp.BasePresenter;
import com.travel.library.utils.ObjectGetByClassUtils;

/**
 * Created by Wisn on 2018/5/13 上午10:05.
 */
public abstract  class CommonFragment<T extends BaseModel, E extends BasePresenter>   extends BaseFragment {

    public T mModel;
    public E mPresenter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mModel = ObjectGetByClassUtils.getClass(this, 0);
        mPresenter = ObjectGetByClassUtils.getClass(this, 1);
        if (mModel != null && mPresenter != null) {
            mPresenter.setMV(mModel, this);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.onDestroy();
    }
}
