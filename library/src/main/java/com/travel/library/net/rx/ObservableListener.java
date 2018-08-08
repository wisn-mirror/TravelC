package com.travel.library.net.rx;

import com.travel.library.beans.BaseResponse;

/**
 * Created by Wisn on 2018/4/2 下午4:50.
 */

public interface ObservableListener<T extends BaseResponse> {

    void onNetStart(String msg);
    void onNext(T result);
    void onComplete();
    void onNetError(NetWorkCodeException.ResponseThrowable responseThrowable);
}
