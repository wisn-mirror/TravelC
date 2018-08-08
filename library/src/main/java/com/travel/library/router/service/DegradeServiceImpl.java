package com.travel.library.router.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.DegradeService;
import com.alibaba.android.arouter.launcher.ARouter;
import com.travel.library.router.RouterConstant;

/**
 * 路由降级  找不到路由时被调用
 * Created by kevin.tian on 2017/4/18.
 * email 1076174020@qq.com
 */
@Route(path = "/service/degradeService")
public class DegradeServiceImpl implements DegradeService {

    @Override
    public void onLost(Context context, Postcard postcard) {
        if(postcard!=null && postcard.getUri()!=null){
            ARouter.getInstance().build(RouterConstant.WEBVIEW).withString("url", postcard.getUri().toString()).navigation(context);
        }
    }

    @Override
    public void init(Context context) {

    }

}
