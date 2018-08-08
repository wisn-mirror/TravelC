package com.travel.library.commons.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * Created by Wisn on 2018/6/4 上午11:46.
 */
public interface IMChatService extends IProvider {
    /**
     * 是否是自动接单时间段
     *
     * @return
     */
    void start(Context context, String account);
}
