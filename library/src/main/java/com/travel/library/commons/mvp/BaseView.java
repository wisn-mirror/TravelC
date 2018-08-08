package com.travel.library.commons.mvp;

/**
 * Created by Wisn on 2018/4/2 下午1:29.
 */

public interface BaseView {
    void onNetError(String tag,String errorMsg);
    void onNetStart(String tag,String startMsg);
    void onNetFinish(String tag,String startMsg);

}
