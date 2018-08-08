package com.travel.mediaselector.entry;


import android.text.TextUtils;

import com.travel.library.commons.bean.mediaselector.MediaInfo;

import java.util.ArrayList;

/**
 * 图片文件夹实体类
 */
public class Folder {

    private boolean useCamera; // 是否可以调用相机拍照。只有“全部”文件夹才可以拍照
    private String name;
    private ArrayList<MediaInfo> images;

    public Folder(String name) {
        this.name = name;
    }

    public Folder(String name, ArrayList<MediaInfo> images) {
        this.name = name;
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<MediaInfo> getImages() {
        return images;
    }

    public void setImages(ArrayList<MediaInfo> images) {
        this.images = images;
    }

    public boolean isUseCamera() {
        return useCamera;
    }

    public void setUseCamera(boolean useCamera) {
        this.useCamera = useCamera;
    }

    public void addImage(MediaInfo image) {
        if (image != null && !TextUtils.isEmpty(image.getPath())) {
            if (images == null) {
                images = new ArrayList<>();
            }
            images.add(image);
        }
    }

    @Override
    public String toString() {
        return "Folder{" +
                "name='" + name + '\'' +
                ", images=" + images +
                '}';
    }
}
