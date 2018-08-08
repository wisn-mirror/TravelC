package com.travel.library.router;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;

/**
 * Created by kevin.tian on 2017/4/18.
 * email 1076174020@qq.com
 */
public class RouterObj {

    private Postcard postcard;
    private InterceptorCallback callback;

    public RouterObj() {
    }

    public RouterObj(Postcard postcard, InterceptorCallback callback) {
        this.postcard = postcard;
        this.callback = callback;
    }

    public Postcard getPostcard() {
        return postcard;
    }

    public void setPostcard(Postcard postcard) {
        this.postcard = postcard;
    }

    public InterceptorCallback getCallback() {
        return callback;
    }

    public void setCallback(InterceptorCallback callback) {
        this.callback = callback;
    }
}
