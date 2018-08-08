package com.travel.mediaselector.model;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;


import com.travel.library.utils.DateUtils;
import com.travel.mediaselector.entry.Folder;
import com.travel.library.commons.bean.mediaselector.MediaInfo;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MediaInfoModel {

    private static final String TAG = "MediaInfoModel";

    /**
     * 从SDCard加载图片
     *
     * @param context
     * @param containsVideo
     * @param callback
     */
    public static void loadImageForSDCard(final Context context, final boolean containsVideo, final DataCallback callback) {
        //由于扫描图片是耗时的操作，所以要在子线程处理。
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<MediaInfo> allImage = getAllImage(context);
                if (containsVideo) {
                    ArrayList<MediaInfo> allVideo = getAllVideo(context);
                    allImage.addAll(allVideo);
                    ArrayList<Folder> folders = splitFolder(allImage);
                    //按照时间排序
                    for (int i = 0; i < folders.size(); i++) {
                        Collections.sort(folders.get(i).getImages());
                    }
                    callback.onSuccess(folders);
                } else {
                    Collections.reverse(allImage);
                    callback.onSuccess(splitFolder(allImage));
                }
            }
        }).start();
    }

    /**
     * 获取手机中所有视频的信息
     */
    public static void getAllVideoInfos(final Context context, final DataCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<MediaInfo> allVideo = getAllVideo(context);
                Collections.reverse(allVideo);
                callback.onSuccess(splitFolder(allVideo));
            }
        }).start();
    }

    public static ArrayList<MediaInfo> getAllImage(Context context) {
        //扫描图片
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver mContentResolver = context.getContentResolver();

        Cursor mCursor = mContentResolver.query(mImageUri, new String[]{
                        MediaStore.Images.Media.DATA,
                        MediaStore.Images.Media.DISPLAY_NAME,
                        MediaStore.Images.Media.DATE_ADDED,
                        MediaStore.Images.Media._ID,
                        MediaStore.Images.Media.SIZE,
                        MediaStore.Images.Media.MIME_TYPE},
                null,
                null,
                MediaStore.Images.Media.DATE_ADDED);

        ArrayList<MediaInfo> images = new ArrayList<>();

        //读取扫描到的图片
        if (mCursor != null) {
            while (mCursor.moveToNext()) {
                // 获取图片的路径
                String path = mCursor.getString(
                        mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                //获取图片名称
                String name = mCursor.getString(
                        mCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                //获取图片时间
                long time = mCursor.getLong(
                        mCursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED));
                long size = mCursor.getLong(mCursor.getColumnIndex(MediaStore.Images.Media.SIZE));
                if (size < 1024 || TextUtils.isEmpty(path)) continue;
                //获取图片类型
                String mimeType = mCursor.getString(
                        mCursor.getColumnIndex(MediaStore.Images.Media.MIME_TYPE));

                if (!"downloading".equals(getExtensionName(path))) { //过滤未下载完成的文件
                    images.add(new MediaInfo(path, time, name, mimeType, size));
                }
            }
            mCursor.close();
        }

        return images;

    }

    public static ArrayList<MediaInfo> getAllVideo(Context context) {
        //扫描视频
        Uri mImageUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        ContentResolver mContentResolver = context.getContentResolver();
        //扫描视频
        String where = MediaStore.Video.Media.MIME_TYPE + "=? or "
                + MediaStore.Video.Media.MIME_TYPE + "=? or "
                + MediaStore.Video.Media.MIME_TYPE + "=? or "
                + MediaStore.Video.Media.MIME_TYPE + "=? or "
                + MediaStore.Video.Media.MIME_TYPE + "=? or "
                + MediaStore.Video.Media.MIME_TYPE + "=? or "
                + MediaStore.Video.Media.MIME_TYPE + "=? or "
                + MediaStore.Video.Media.MIME_TYPE + "=? or "
                + MediaStore.Video.Media.MIME_TYPE + "=?";
        String[] whereArgs = {"video/mp4", "video/3gp", "video/aiv", "video/rmvb", "video/vob", "video/flv",
                "video/mkv", "video/mov", "video/mpg"};
        Cursor allVideo = mContentResolver.query(mImageUri, new String[]{
                        MediaStore.Video.Media.DATA,
                        MediaStore.Video.Media.DISPLAY_NAME,
                        MediaStore.Video.Media.DATE_ADDED,
                        MediaStore.Video.Media._ID,
                        MediaStore.Video.Media.SIZE,
                        MediaStore.Video.Media.DURATION,
                        MediaStore.Video.Media.MIME_TYPE},
                where,
                whereArgs,
                MediaStore.Images.Media.DATE_ADDED);
        ArrayList<MediaInfo> images = new ArrayList<>();
        final DateFormat format = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

        if (allVideo != null && allVideo.moveToFirst()) {
            while (!allVideo.isAfterLast() && allVideo.moveToNext()) {
                // 获取视频的路径
                String path = allVideo.getString(
                        allVideo.getColumnIndex(MediaStore.Video.Media.DATA));
                //获取视频名称
                String name = allVideo.getString(
                        allVideo.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
                //获取视频创建时间
                long time = allVideo.getLong(
                        allVideo.getColumnIndex(MediaStore.Video.Media.DATE_ADDED));
                long size = allVideo.getLong(allVideo.getColumnIndex(MediaStore.Video.Media.SIZE));
                if (size < 1024 || TextUtils.isEmpty(path)) continue;
                //获取视频时间
                long duration = allVideo.getLong(
                        allVideo.getColumnIndex(MediaStore.Video.Media.DURATION));
                format.format(new Date(duration));
                //获取视频类型
                String mimeType = allVideo.getString(
                        allVideo.getColumnIndex(MediaStore.Video.Media.MIME_TYPE));
                if (!"downloading".equals(getExtensionName(path))) { //过滤未下载完成的文件
                    images.add(new MediaInfo(path, time, name, mimeType, true, DateUtils.getTimeDurationStr(duration), size));
                }
            }
            allVideo.close();
        }
        return images;
    }

    /**
     * 把图片按文件夹拆分，第一个文件夹保存所有的图片
     *
     * @param images
     *
     * @return
     */
    private static ArrayList<Folder> splitFolder(ArrayList<MediaInfo> images) {
        ArrayList<Folder> folders = new ArrayList<>();
        folders.add(new Folder("全部图片", images));
        if (images != null && !images.isEmpty()) {
            int size = images.size();
            for (int i = 0; i < size; i++) {
                String path = images.get(i).getPath();
                String name = getFolderName(path);
                if (!TextUtils.isEmpty(name)) {
                    Folder folder = getFolder(name, folders);
                    folder.addImage(images.get(i));
                }
            }
        }
        return folders;
    }

    /**
     * Java文件操作 获取文件扩展名
     */
    public static String getExtensionName(String filename) {
        if (filename != null && filename.length() > 0) {
            int dot = filename.lastIndexOf('.');
            if (dot > -1 && dot < filename.length() - 1) {
                return filename.substring(dot + 1);
            }
        }
        return "";
    }

    /**
     * 根据图片路径，获取图片文件夹名称
     *
     * @param path
     *
     * @return
     */
    private static String getFolderName(String path) {
        if (!TextUtils.isEmpty(path)) {
            String[] strings = path.split(File.separator);
            if (strings.length >= 2) {
                return strings[strings.length - 2];
            }
        }
        return "";
    }

    private static Folder getFolder(String name, List<Folder> folders) {
        if (!folders.isEmpty()) {
            int size = folders.size();
            for (int i = 0; i < size; i++) {
                Folder folder = folders.get(i);
                if (name.equals(folder.getName())) {
                    return folder;
                }
            }
        }
        Folder newFolder = new Folder(name);
        folders.add(newFolder);
        return newFolder;
    }

    public interface DataCallback {
        void onSuccess(ArrayList<Folder> folders);
    }
}
