package com.travel.library.commons.event;

import com.travel.library.event.BaseEvent;

/**
 * Created by Wisn on 2018/6/11 上午11:28.
 */
public class MainPageEvent extends BaseEvent<Integer> {
    public boolean isSelect = false;

    /**
     * @param type     -1为底部提醒,1为提示数量
     * @param msg      msg下标数量
     * @param index    data为底部下标0, 1，2，3
     * @param isSelect 是否选中
     */
    public MainPageEvent(int type, String msg, Integer index, boolean isSelect) {
        this.type = type;
        this.msg = msg;
        this.data = index;
        this.isSelect = isSelect;
    }

    public MainPageEvent(Integer index, boolean isSelect) {
        this.type = 0;
        this.msg = null;
        this.data = index;
        this.isSelect = isSelect;
    }

    @Override
    public String toString() {
        return "MainPageEvent{" +
                "isSelect=" + isSelect +
                ", type=" + type +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
