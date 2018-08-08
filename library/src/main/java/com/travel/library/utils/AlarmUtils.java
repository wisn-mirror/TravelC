package com.travel.library.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

/**
 * Created by wisn on 2017/12/13.
 */
public class AlarmUtils {
    public static final int REQUEST_CODE = 0;

    /**
     * 取消闹钟
     *
     * @param action
     */
    public static void canalAlarm(String action) {
        Intent intent = new Intent(action);
        PendingIntent pi = PendingIntent.getBroadcast(Utils.getApp(), REQUEST_CODE, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager am = (AlarmManager) Utils.getApp().getSystemService(Context.ALARM_SERVICE);
        am.cancel(pi);
    }

    /**
     * @param action            广播action
     * @param elapsedRealtime   开机时间 elapsedRealtime
     * @param windowLenthMillis 时间误差
     */
    public static void startAlarmElapsedRealtime(String action, long elapsedRealtime, long windowLenthMillis) {
        Intent alarm = new Intent(action);
        alarm.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        PendingIntent recurringAlarm = PendingIntent.getBroadcast(Utils.getApp(), REQUEST_CODE, alarm,
                PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) Utils.getApp().getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setWindow(AlarmManager.ELAPSED_REALTIME_WAKEUP, elapsedRealtime, windowLenthMillis, recurringAlarm);
            //参数2是开始时间、参数3是允许系统延迟的时间 6.0 7.0误差太大
            //alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, elapsedRealtime, recurringAlarm);
        } else {
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, elapsedRealtime, windowLenthMillis, recurringAlarm);
        }
    }

    /**
     * @param action            广播action
     * @param time              当前日期时间戳
     * @param windowLenthMillis 时间误差
     */
    public static void startAlarmTime(String action, long time, long windowLenthMillis) {
        Intent alarm = new Intent(action);
        alarm.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        PendingIntent recurringAlarm = PendingIntent.getBroadcast(Utils.getApp(), REQUEST_CODE, alarm,
                PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) Utils.getApp().getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setWindow(AlarmManager.RTC_WAKEUP, time, windowLenthMillis, recurringAlarm);
            //参数2是开始时间、参数3是允许系统延迟的时间 setExact在6.0 7.0，8.0版本中误差太大
            //alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, recurringAlarm);
        } else {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, windowLenthMillis, recurringAlarm);
        }
    }
}
