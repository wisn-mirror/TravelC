package com.travel.library.commons.bean.mediaselector;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * 图片实体类
 */
public class MediaInfo implements Parcelable, Comparable<MediaInfo> {

    private String path;
    private long time;
    private String name;
    private String mimeType;
    private boolean isVideo;
    public long size;
    public String duration;

    public MediaInfo(String path, long time, String name, String mimeType,long size) {
        this.path = path;
        this.time = time;
        this.name = name;
        this.mimeType = mimeType;
        this.size=size;
    }

    public MediaInfo(String path, long time, String name, String mimeType, boolean isVideo, String duration,long size) {
        this.path = path;
        this.time = time;
        this.name = name;
        this.mimeType = mimeType;
        this.isVideo = isVideo;
        this.duration = duration;
        this.size=size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public boolean isGif() {
        return "image/gif".equals(mimeType);
    }

    public boolean isVideo() {
        return isVideo;
    }

    public void setVideo(boolean video) {
        isVideo = video;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.path);
        dest.writeLong(this.time);
        dest.writeString(this.name);
        dest.writeString(this.mimeType);
        dest.writeByte((byte) (this.isVideo ? 1 : 0));
        dest.writeLong(this.size);
        dest.writeString(this.duration);
    }

    protected MediaInfo(Parcel in) {
        this.path = in.readString();
        this.time = in.readLong();
        this.name = in.readString();
        this.mimeType = in.readString();
        this.isVideo=in.readByte()==1;
        this.size=in.readLong();
        this.duration=in.readString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MediaInfo mediaInfo = (MediaInfo) o;
        return (path == mediaInfo.path) || (path != null && path.equals(mediaInfo.path));
    }

    @Override
    public int hashCode() {
        if (path == null)
            return 0;
        int result = 1;
        result = 31 * result + (path == null ? 0 : path.hashCode());
        return result;
    }

    public static final Creator<MediaInfo> CREATOR = new Creator<MediaInfo>() {
        @Override
        public MediaInfo createFromParcel(Parcel source) {
            return new MediaInfo(source);
        }

        @Override
        public MediaInfo[] newArray(int size) {
            return new MediaInfo[size];
        }
    };

    @Override
    public String toString() {
        return "path='" + path;
    }

    @Override
    public int compareTo(@NonNull MediaInfo o) {
        return o.time > this.time ? 1 : -1;
    }
}
