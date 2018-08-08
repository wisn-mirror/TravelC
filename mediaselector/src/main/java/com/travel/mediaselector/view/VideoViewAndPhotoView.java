package com.travel.mediaselector.view;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;

/**
 * Created by Wisn on 2018/7/30 上午10:00.
 */
public class VideoViewAndPhotoView extends FrameLayout {

    private  Context mContext;
    private VideoView fullScreenVideoView;
    private PhotoView photoViews;

    public VideoViewAndPhotoView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public VideoViewAndPhotoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public VideoViewAndPhotoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public void initView(Context context) {
        this.mContext=context;
        fullScreenVideoView = new VideoView(context);
        LayoutParams fullScreeVideoViewParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        fullScreeVideoViewParams.gravity= Gravity.CENTER_VERTICAL;
        addView(fullScreenVideoView, fullScreeVideoViewParams);
        photoViews = new PhotoView(context);
        LayoutParams photoViewsParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(photoViews, photoViewsParams);

    }

    public void showPicByPath(String path) {
        fullScreenVideoView.setVisibility(View.GONE);
        photoViews.setVisibility(View.VISIBLE);
        resetRes();
        Glide.with(mContext).load(new File(path))
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(photoViews);
    }
    public void resetRes(){
        fullScreenVideoView.stopPlayback();
        photoViews.setImageDrawable(null);
    }

    public void playVideoByPath(String path) {
        try {
            fullScreenVideoView.setVisibility(View.VISIBLE);
            photoViews.setVisibility(View.GONE);
            resetRes();
            fullScreenVideoView.setVideoPath(path);
            fullScreenVideoView.requestFocus();
            fullScreenVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    if (mp.isPlaying()) {
                        mp.stop();
                        mp.release();
                        mp = new MediaPlayer();
                    }
                    mp.setVolume(0f, 0f);
                    mp.setLooping(true);
                    mp.start();
                }
            });
            fullScreenVideoView.setFocusable(false);
            fullScreenVideoView.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
