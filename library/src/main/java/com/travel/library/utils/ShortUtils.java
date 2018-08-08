package com.travel.library.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by wisn on 2017/10/9.
 */

public class ShortUtils {
    /**
     * 检测是否存在快捷键
     *
     * @param activity Activity
     *
     * @return 是否存在桌面图标
     */
    public static boolean hasShortcut(Activity activity,int strid) {
        boolean isInstallShortcut = false;
        final ContentResolver cr = activity.getContentResolver();
        final String AUTHORITY = "com.android.launcher.settings";
        final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
                                          + "/favorites?notify=true");
        Cursor c = cr.query(CONTENT_URI,
                            new String[]{"title", "iconResource"}, "title=?",
                            new String[]{activity.getString(strid).trim()},
                            null);
        if (c != null && c.getCount() > 0) {
            isInstallShortcut = true;
        }
        return isInstallShortcut;
    }

    /**
     * 为程序创建桌面快捷方式
     *
     * @param activity Activity
     * @param res      res
     */
    public static void addShortcut(Activity activity, int res,int strid) {

        Intent shortcut = new Intent(
                "com.android.launcher.action.INSTALL_SHORTCUT");
        // 快捷方式的名称
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME,
                          activity.getString(strid));
        shortcut.putExtra("duplicate", false); // 不允许重复创建
        Intent shortcutIntent = new Intent(Intent.ACTION_MAIN);
        shortcutIntent.setClassName(activity, activity.getClass().getName());
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        // 快捷方式的图标
        Intent.ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(
                activity, res);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);

        activity.sendBroadcast(shortcut);
    }

    /**
     * 删除程序的快捷方式
     *
     * @param activity Activity
     */
    public static void delShortcut(Activity activity,int strid) {

        Intent shortcut = new Intent(
                "com.android.launcher.action.UNINSTALL_SHORTCUT");
        // 快捷方式的名称
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME,
                          activity.getString(strid));
        String appClass = activity.getPackageName() + "."
                          + activity.getLocalClassName();
        ComponentName comp = new ComponentName(activity.getPackageName(),
                                               appClass);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(
                Intent.ACTION_MAIN).setComponent(comp));
        activity.sendBroadcast(shortcut);
    }
}
