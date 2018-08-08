package com.travel.library.router.service;

import android.content.Context;
import android.net.Uri;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.PathReplaceService;
import com.alibaba.android.arouter.utils.TextUtils;
import com.travel.library.router.RouterConstant;

/**
 * 路由替换
 * 1.没有 path 或者 path不符合要求的都 直接跳转到 webview
 * Created by kevin.tian on 2017/4/18.
 * email 1076174020@qq.com
 */
@Route(path = "/service/pathReplaceService")
public class PathReplaceServiceImpl implements PathReplaceService {

    @Override
    public String forString(String path) {
        return path;
    }

    @Override
    public Uri forUri(Uri uri) {
        if (uri != null) {
            //没有 path 或者 path不符合要求的都 直接跳转到 webview
            if (!verPath(uri.getPath())) {
                String url = uri.toString();
                uri = uri.buildUpon().path(RouterConstant.WEBVIEW).clearQuery().appendQueryParameter("url", url).build();
            }
        }
        return uri;
    }

    @Override
    public void init(Context context) {

    }

    /**
     * Extract the default group from path.
     */
    private boolean verPath(String path) {
        if (TextUtils.isEmpty(path) || !path.startsWith("/")) {
            return false;
        }

        try {
            String defaultGroup = path.substring(1, path.indexOf("/", 1));
            if (TextUtils.isEmpty(defaultGroup)) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

}
