package com.travel.library.net.rx;

import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.travel.library.beans.BaseResponse;
import com.travel.library.commons.Auth;
import com.travel.library.commons.constants.ARoutePath;
import com.travel.library.utils.LogUtilsLib;
import com.travel.library.utils.ToastUtils;
import com.travel.library.utils.Utils;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by Wisn on 2018/4/2 下午1:29.
 */

public class RxManager {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public <T extends BaseResponse> DisposableObserver<T> addObserver(final Observable<T > netWorkObservable,
                                                 final RxObservableListener<T> rxObservableListener) {
        DisposableObserver<T> observer = netWorkObservable.compose(RxSchedulers.<T>io_main())
                .subscribeWith(new RxSubscriber<T>() {
                    @Override
                    protected void onStart() {
                        super.onStart();
                        rxObservableListener.onNetStart(null);
                    }

                    @Override
                    public void _onNext(T t) {
                        if (t != null && t.code == 99) {
                            //身份过期
                            String token = Auth.getToken();
                            if (!TextUtils.isEmpty(token)) {
                                Auth.putToken("");
                                Utils.exitApp(null);
                                ToastUtils.show("登录过期");
                                ARouter.getInstance().build(ARoutePath.account.LoginActivity).navigation();
                            }
                        }
                        LogUtilsLib.d("_onNext"+t);
                        rxObservableListener.onNext(t);
                    }

                    @Override
                    public void _onError(NetWorkCodeException.ResponseThrowable e) {
                        LogUtilsLib.d("_onError" + e.getMessage());
                        rxObservableListener.onNetError(e);
                    }

                    @Override
                    public void _onComplete() {
                        LogUtilsLib.d("_onComplete");
                        rxObservableListener.onComplete();
                    }
                });

        if (observer != null) {
            compositeDisposable.add(observer);
        }
        return observer;
    }

    public void clear() {
        compositeDisposable.dispose();
    }
}
