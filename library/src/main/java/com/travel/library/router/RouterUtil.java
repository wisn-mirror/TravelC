package com.travel.library.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;

import com.alibaba.android.arouter.core.LogisticsCenter;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IRouteGroup;
import com.alibaba.android.arouter.launcher.ARouter;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 路由工具类
 * Created by kevin.tian on 2017/4/18.
 * email 1076174020@qq.com
 */
public final class RouterUtil {

    /**
     * 缓存路由与class的map
     */
    private static ArrayMap<Class<?>, String> cacheRoutClsMap = new ArrayMap<>();

    /**
     * 路由跳转 外部跳转
     *
     * @param url
     */
    public static Object navigation(Context context, String url) {
        return navigation(context, url, (Bundle) null);
    }

    /**
     * 路由跳转 外部跳转
     *
     * @param url
     */
    public static Object navigation(Context context, String url, Bundle bundle) {
        return navigation(context, url, bundle, -1);
    }

    /**
     * 路由跳转 外部跳转
     *
     * @param url
     */
    public static Object navigation(Context context, String url, Bundle bundle, int requestCode) {
        return navigation(context, url, bundle, requestCode, null);
    }

    /**
     * 路由跳转 外部跳转
     *
     * @param url
     */
    public static Object navigation(Context context, String url, Bundle bundle, int requestCode, NavCallback callback) {
        if (url == null || "".equals(url) || context == null) {
            return null;
        }
        Uri uri = Uri.parse(url);
        return navigation(context, uri, bundle, requestCode, callback);
    }

    /**
     * 路由跳转 外部跳转
     */
    public static Object navigation(Context context, Uri uri, Bundle bundle, int requestCode, NavCallback callback) {
        if (uri == null || context == null) {
            return null;
        }
        try {
            Postcard postcard = ARouter.getInstance().build(uri).with(bundle);
            if (requestCode == -1) {
                return postcard.navigation(context, callback);
            } else {
                if (context instanceof Activity) {
                    postcard.navigation((Activity) context, requestCode, callback);
                }
            }
        } catch (Exception e) {
            if (callback != null) {
                callback.onLost(null);
            }
        }
        return null;
    }

    /**
     * 路由跳转 外部跳转
     */
    public static Object navigation(Context context, Uri uri) {
        return navigation(context, uri, null, -1, null);
    }

    /**
     * 路由跳转 外部跳转
     *
     * @param uri
     */
    public static void navigation(Fragment fragment, Uri uri, Bundle bundle, int requestCode) {
        if (uri == null || fragment == null) {
            return;
        }
        try {
            Postcard postcard = ARouter.getInstance().build(uri).with(bundle);
            LogisticsCenter.completion(postcard);
            if (postcard.getDestination() != null) {

                Intent intent = new Intent(fragment.getActivity(), postcard.getDestination());
                intent.putExtras(postcard.getExtras());

                int flags = postcard.getFlags();
                if (flags != -1) {
                    intent.setFlags(flags);
                }

                fragment.startActivityForResult(intent, requestCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 路由跳转 外部跳转
     *
     * @param url
     */
    public static void navigation(Fragment fragment, String url, Bundle bundle, int requestCode) {
        if (url == null || "".equals(url) || fragment == null) {
            return;
        }
        Uri uri = Uri.parse(url);
        navigation(fragment, uri, bundle, requestCode);
    }

    /**
     * 路由跳转 外部跳转
     *
     * @param url
     */
    public static void navigation(Fragment fragment, String url, int requestCode) {
        navigation(fragment, url, null, requestCode);
    }

    /**
     * 获取路由
     *
     * @param cls
     */
    public static String getRoutePath(Class<?> cls) {
        if (cacheRoutClsMap.size() == 0) {
            try {
                Field field = Class.forName("com.alibaba.android.arouter.core.Warehouse").getDeclaredField("groupsIndex");
                field.setAccessible(true);
                Map<String, Class<? extends IRouteGroup>> groupsIndexMap = (Map<String, Class<? extends IRouteGroup>>) field.get(null);
                if (groupsIndexMap != null && groupsIndexMap.size() != 0) {
                    Map<String, RouteMeta> routesMap = new HashMap<>();

                    for (Class<? extends IRouteGroup> routeCls : groupsIndexMap.values()) {
                        IRouteGroup iGroupInstance = routeCls.getConstructor().newInstance();
                        iGroupInstance.loadInto(routesMap);
                    }

                    for (Map.Entry<String, RouteMeta> entry : routesMap.entrySet()) {
                        cacheRoutClsMap.put(entry.getValue().getDestination(), entry.getKey());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return cacheRoutClsMap.get(cls);
    }

}
