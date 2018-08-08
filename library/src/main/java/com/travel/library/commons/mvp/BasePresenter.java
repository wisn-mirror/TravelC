package com.travel.library.commons.mvp;

import com.travel.library.net.rx.RxManager;

/**
 * Created by Wisn on 2018/4/2 下午1:29.
 */

public abstract class BasePresenter<E, T> {
    public E mModel;
    public T mView;
    private RxManager rxManager;

    public void setMV(E model, T view) {
        this.mModel = model;
        this.mView = view;
    }

    public RxManager getRxManager() {
        if (rxManager == null) {
            synchronized (BasePresenter.class) {
                if (rxManager == null) {
                    rxManager = new RxManager();
                }
            }
        }
        return rxManager;
    }

    public void onDestroy() {
        getRxManager().clear();
        if (rxManager != null) {
            rxManager = null;
        }
    }
}
