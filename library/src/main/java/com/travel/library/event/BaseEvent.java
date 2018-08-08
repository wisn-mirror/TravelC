package com.travel.library.event;

/**
 * @author Wisn
 * @time 2018/5/27 11:57
 */

public class BaseEvent<T> {
    public int type;
    public String msg;
    public T data;

    public BaseEvent() {
    }

    public BaseEvent(int type) {
        this.type = type;
    }

    public BaseEvent(int type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public BaseEvent(int type, String msg, T data) {
        this.type = type;
        this.msg = msg;
        this.data = data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseEvent{" +
                "type=" + type +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
