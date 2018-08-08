package com.travel.account.login;

import com.travel.account.bean.LoginResponse;
import com.travel.library.beans.BaseResponse;
import com.travel.library.commons.mvp.BaseModel;
import com.travel.library.commons.mvp.BasePresenter;
import com.travel.library.commons.mvp.BaseView;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * Created by Wisn on 2018/5/22 下午2:34.
 */
public interface Login {
    interface LoginM extends BaseModel {
        Observable<LoginResponse> login(RequestBody requestBody);
    }

    interface LoginV extends BaseView {
        void loginSuccess();

        void loginFailed(String msg);
    }

    abstract class Presenter extends BasePresenter<LoginM, LoginV> {
        public abstract void Login(String userName, String password);
    }

}
