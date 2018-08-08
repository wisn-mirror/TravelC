package com.travel.library.router.interceptor;

import android.content.Context;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.alibaba.android.arouter.launcher.ARouter;
import com.travel.library.router.LoginRouterManager;
import com.travel.library.router.RouterConstant;
import com.travel.library.router.RouterObj;

/**
 * 登录拦截
 * Created by kevin.tian on 2017/4/18.
 * email 1076174020@qq.com
 */
@Interceptor(priority = 1)
public class LoginRouterInterceptor implements IInterceptor {

    @Override
    public void process(Postcard postcard, final InterceptorCallback callback) {
        Bundle bundle = postcard.getExtras();
        if(bundle == null){
            bundle = new Bundle();
        }
        if(postcard.getUri()!=null){
            String token = bundle.getString("token");
            int code = postcard.getExtra();
            if("!".equalsIgnoreCase(token)|| code ==1){  //需要登录
                if(!false){//TODO
                    ARouter.getInstance().build(RouterConstant.LOGIN).navigation(null, new NavCallback() {

                        @Override
                        public void onLost(Postcard postcard) {
                            callback.onInterrupt(null);
                        }

                        @Override
                        public void onArrival(Postcard postcard) {
                            LoginRouterManager.getInstance().register(new RouterObj(postcard, callback));
                        }

                        @Override
                        public void onInterrupt(Postcard postcard) {
                            callback.onInterrupt(null);
                        }
                    });
                }else{
                    callback.onContinue(postcard);
                }
            }else{  //不需要登录
                callback.onContinue(postcard);
            }
        }else{
            callback.onContinue(postcard);
        }
    }

    @Override
    public void init(Context context) {

    }

}
