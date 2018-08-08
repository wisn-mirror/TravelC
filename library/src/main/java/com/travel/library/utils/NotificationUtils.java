package com.travel.library.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
/**
 * Created by Wisn on 2018/6/12 下午5:49.
 */
public class NotificationUtils {
    /**
     * 悬挂式，支持6.0以上系统
     * @param largeIcon
     * @param smallIcon
     * @param ticker
     * @param contentTitle
     * @param contentText
     * @param intent
     */
    public static void showFullScreen(int largeIcon, int smallIcon, String ticker, String contentTitle, String contentText, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(Utils.getApp());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(Utils.getApp(), 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setTicker(ticker);
        builder.setContentText(contentText);
        builder.setSmallIcon(smallIcon);
        builder.setLargeIcon(BitmapFactory.decodeResource(Utils.getApp().getResources(), largeIcon));
        builder.setAutoCancel(true);
        builder.setContentTitle(contentTitle);
        builder.setDefaults(Notification.DEFAULT_SOUND);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //如果描述的PendingIntent已经存在，则在产生新的Intent之前会先取消掉当前的
        PendingIntent hangPendingIntent = PendingIntent.getActivity(Utils.getApp(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setFullScreenIntent(hangPendingIntent, true);
        NotificationManager notificationManager = (NotificationManager) Utils.getApp().getSystemService(Utils.getApp().NOTIFICATION_SERVICE);
        notificationManager.notify(3, builder.build());
    }

    /**
     * @param largeIcon
     * @param smallIcon
     * @param ticker
     * @param contentTitle
     * @param contentText
     * @param intent
     */
    public static void showNotification(int largeIcon, int smallIcon, String ticker, String contentTitle, String contentText, Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Notification notification = new NotificationCompat.Builder(Utils.getApp())
                /**设置通知左边的大图标**/
                .setLargeIcon(BitmapFactory.decodeResource(Utils.getApp().getResources(), largeIcon))
                /**设置通知右边的小图标**/
                .setSmallIcon(smallIcon)
                /**通知首次出现在通知栏，带上升动画效果的**/
                .setTicker(ticker)
                /**设置通知的标题**/
                .setContentTitle(contentTitle)
                /**设置通知的内容**/
                .setContentText(contentText)
                /**通知产生的时间，会在通知信息里显示**/
                .setWhen(System.currentTimeMillis())
                /**设置该通知优先级**/
                .setPriority(Notification.PRIORITY_DEFAULT)
                /**设置这个标志当用户单击面板就可以让通知将自动取消**/
                .setAutoCancel(true)
                /**设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)**/
                .setOngoing(true)
                /**向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：**/
                .setDefaults(Notification.DEFAULT_SOUND)
                .setContentIntent(PendingIntent.getActivity(Utils.getApp(), 1, intent, PendingIntent.FLAG_CANCEL_CURRENT))
                .build();
        NotificationManager notificationManager = (NotificationManager) Utils.getApp().getSystemService(Utils.getApp().NOTIFICATION_SERVICE);
        /**发起通知**/
        notificationManager.notify(1, notification);
    }


}
