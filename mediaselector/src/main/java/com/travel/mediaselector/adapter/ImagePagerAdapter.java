package com.travel.mediaselector.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.travel.library.commons.bean.mediaselector.MediaInfo;
import com.travel.mediaselector.view.VideoViewAndPhotoView;

import java.util.ArrayList;
import java.util.List;

public class ImagePagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<VideoViewAndPhotoView> viewList = new ArrayList<>(4);
    List<MediaInfo> mImgList;
    private OnItemClickListener mListener;

    public ImagePagerAdapter(Context context, List<MediaInfo> imgList) {
        this.mContext = context;
        createImageViews();
        mImgList = imgList;
    }

    private void createImageViews() {
        for (int i = 0; i < 4; i++) {
            VideoViewAndPhotoView videoViewAndPhotoView = new VideoViewAndPhotoView(mContext);
            viewList.add(videoViewAndPhotoView);
        }
    }

    @Override
    public int getCount() {
        return mImgList == null ? 0 : mImgList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (object instanceof VideoViewAndPhotoView) {
            VideoViewAndPhotoView view = (VideoViewAndPhotoView) object;
            view.resetRes();
            viewList.add(view);
            container.removeView(view);
        }

    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final VideoViewAndPhotoView currentView = viewList.remove(0);
        final MediaInfo image = mImgList.get(position);
        container.addView(currentView);
        if (!image.isVideo()) {
            currentView.showPicByPath(image.getPath());
        } else {
            currentView.playVideoByPath(image.getPath());
        }
        currentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(position, image);
                }
            }
        });
        return currentView;
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        mListener = l;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, MediaInfo image);
    }

}
