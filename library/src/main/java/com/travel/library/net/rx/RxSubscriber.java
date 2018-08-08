package com.travel.library.net.rx;


import io.reactivex.observers.DisposableObserver;

/**
 * Created by Wisn on 2018/4/2 下午5:25.
 */

public abstract class RxSubscriber<T> extends DisposableObserver<T> {

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    @Override
    public void onError(Throwable e) {
        _onError(NetWorkCodeException.getResponseThrowable(e));
    }

    @Override
    public void onComplete() {
        _onComplete();
    }

    /**
     * 定义处理事件
     */
    public abstract void _onNext(T t);
    public abstract void _onError(NetWorkCodeException.ResponseThrowable e);
    public abstract void _onComplete();
}
