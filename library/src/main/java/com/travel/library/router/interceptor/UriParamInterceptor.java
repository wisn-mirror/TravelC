package com.travel.library.router.interceptor;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.alibaba.android.arouter.utils.TextUtils;

import java.util.Map;

/**
 * 处理 uri 的参数
 * Created by kevin.tian on 2017/4/18.
 * email 1076174020@qq.com
 */
@Interceptor(priority = 0)
public class UriParamInterceptor implements IInterceptor {

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        if(postcard.getUri()!=null){
            Map<String, String> resultMap = TextUtils.splitQueryParameters(postcard.getUri());
            for (Map.Entry<String, String> params : resultMap.entrySet()) {
                postcard.withString(params.getKey(), params.getValue());
            }
        }
        callback.onContinue(postcard);
    }

    @Override
    public void init(Context context) {

    }

}
