package com.travel.library.utils;

import android.widget.Toast;

/**
 * Created by wisn on 2017/10/5.
 */

public class ToastUtils {

    private static Toast toast;

    public static void show( String msg) {
        if (toast == null) {
            toast = Toast.makeText(Utils.getApp().getApplicationContext(), msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    public static void show(int id) {
        show(Utils.getApp().getString(id));
    }

}
