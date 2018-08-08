package com.travel.mediaselector.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.travel.library.utils.ImageUtils;
import com.travel.library.utils.ToastUtils;
import com.travel.library.commons.bean.mediaselector.MediaInfo;
import com.travel.mediaselector.R;

import java.util.ArrayList;
import java.util.List;

public class MediaInfoAdapter extends RecyclerView.Adapter<MediaInfoAdapter.ViewHolder> {

    private boolean isSingleVideo;
    private Context mContext;
    private ArrayList<MediaInfo> mImages;
    private LayoutInflater mInflater;

    //保存选中的图片
    private ArrayList<MediaInfo> mSelectImages = new ArrayList<>();
    private OnImageSelectListener mSelectListener;
    private OnItemClickListener mItemClickListener;
    private int mMaxCount;
    private boolean isSingle;

    private static final int TYPE_CAMERA = 1;
    private static final int TYPE_IMAGE = 2;

    private boolean useCamera;

    /**
     * @param maxCount 图片的最大选择数量，小于等于0时，不限数量，isSingle为false时才有用。
     * @param isSingle 是否单选
     */
    public MediaInfoAdapter(Context context, int maxCount, boolean isSingle, boolean isSingleVideo) {
        mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        mMaxCount = maxCount;
        this.isSingle = isSingle;
        this.isSingleVideo = isSingleVideo;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_IMAGE) {
            View view = mInflater.inflate(R.layout.photoselect_adapter_media_item, parent, false);
            return new ViewHolder(view);
        } else {
            View view = mInflater.inflate(R.layout.photoselect_adapter_camera, parent, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_IMAGE) {
            final MediaInfo mediaInfo = getImage(position);
            ImageUtils.loadImage(mContext, mediaInfo.getPath(), holder.ivImage);
            setItemSelect(holder, mSelectImages.contains(mediaInfo));
            holder.ivGif.setVisibility(mediaInfo.isGif() ? View.VISIBLE : View.GONE);
            if (mediaInfo.isVideo()) {
                holder.tv_video_duration.setVisibility(mediaInfo.isVideo() ? View.VISIBLE : View.GONE);
                holder.tv_video_duration.setText(mediaInfo.duration);
            } else {
                holder.tv_video_duration.setVisibility(View.GONE);
            }

            //点击选中/取消选中图片
            holder.ivSelectIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mSelectImages.contains(mediaInfo)) {
                        //如果图片已经选中，就取消选中
                        unSelectImage(mediaInfo);
                        setItemSelect(holder, false);
                    } else if (isSingle) {
                        //如果是单选，就先清空已经选中的图片，再选中当前图片
                        clearImageSelect();
                        selectImage(mediaInfo);
                        setItemSelect(holder, true);
                    } else if (isSingleVideo) {
                        if (mSelectImages != null && mSelectImages.size() > 0) {
                            if (mediaInfo.isVideo()) {
                                for (MediaInfo mediaInfo1 : mSelectImages) {
                                    if (mediaInfo1.isVideo()) {
                                        ToastUtils.show("视频只能单选");
                                    } else {
                                        ToastUtils.show( "视频和图片不能同时选择");
                                    }
                                    break;
                                }
                                return;
                            }else{
                                for (MediaInfo mediaInfo1 : mSelectImages) {
                                    if (mediaInfo1.isVideo()) {
                                        ToastUtils.show(  "视频只能单选");
                                        return ;
                                    }
                                }
                            }
                        }
                        selectImage(mediaInfo);
                        setItemSelect(holder, true);
                    } else if (mMaxCount <= 0 || mSelectImages.size() < mMaxCount) {
                        //如果不限制图片的选中数量，或者图片的选中数量
                        // 还没有达到最大限制，就直接选中当前图片。
                        selectImage(mediaInfo);
                        setItemSelect(holder, true);
                    }
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        int p = holder.getAdapterPosition();
                        mItemClickListener.OnItemClick(mediaInfo, useCamera ? p - 1 : p);
                    }
                }
            });
        } else if (getItemViewType(position) == TYPE_CAMERA) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.OnCameraClick();
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (useCamera && position == 0) {
            return TYPE_CAMERA;
        } else {
            return TYPE_IMAGE;
        }
    }

    /**
     * 选中图片
     *
     * @param image
     */
    private void selectImage(MediaInfo image) {
        mSelectImages.add(image);
        if (mSelectListener != null) {
            mSelectListener.OnImageSelect(mSelectImages, true);
        }
    }

    /**
     * 取消选中图片
     *
     * @param image
     */
    private void unSelectImage(MediaInfo image) {
        mSelectImages.remove(image);
        if (mSelectListener != null) {
            mSelectListener.OnImageSelect(mSelectImages, false);
        }
    }


    @Override
    public int getItemCount() {
        return useCamera ? getImageCount() + 1 : getImageCount();
    }

    private int getImageCount() {
        return mImages == null ? 0 : mImages.size();
    }

    public ArrayList<MediaInfo> getData() {
        return mImages;
    }

    public void refresh(ArrayList<MediaInfo> data, boolean useCamera) {
        mImages = data;
        this.useCamera = useCamera;
        notifyDataSetChanged();
    }

    private MediaInfo getImage(int position) {
        return mImages.get(useCamera ? position - 1 : position);
    }

    public MediaInfo getFirstVisibleImage(int firstVisibleItem) {
        if (mImages != null) {
            if (useCamera) {
                return mImages.get(firstVisibleItem == 0 ? 0 : firstVisibleItem - 1);
            } else {
                return mImages.get(firstVisibleItem);
            }
        }
        return null;
    }

    /**
     * 设置图片选中和未选中的效果
     */
    private void setItemSelect(ViewHolder holder, boolean isSelect) {
        if (isSelect) {
            holder.ivSelectIcon.setImageResource(R.drawable.photoselect_icon_image_select);
            holder.ivMasking.setAlpha(0.5f);
        } else {
            holder.ivSelectIcon.setImageResource(R.drawable.photoselect_icon_image_un_select);
            holder.ivMasking.setAlpha(0.1f);
        }
    }

    private void clearImageSelect() {
        if (mImages != null && mSelectImages.size() == 1) {
            int index = mImages.indexOf(mSelectImages.get(0));
            mSelectImages.clear();
            if (index != -1) {
                notifyItemChanged(useCamera ? index + 1 : index);
            }
        }
    }

    public void setSelectedImages(ArrayList<MediaInfo> selected) {
        if (selected != null) {
            for (MediaInfo image : selected) {
                if (isFull()) {
                    return;
                }
                if (!mSelectImages.contains(image)) {
                    mSelectImages.add(image);
                }
            }
            notifyDataSetChanged();
        }
        if (mSelectListener != null) {
            mSelectListener.OnImageSelect(mSelectImages, true);
        }
    }


    private boolean isFull() {
        if (isSingle && mSelectImages.size() == 1) {
            return true;
        }
        if (isSingleVideo && mSelectImages.size() == 1 && mSelectImages.get(0).isVideo()) {
            return true;
        } else if (mMaxCount > 0 && mSelectImages.size() == mMaxCount) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<MediaInfo> getSelectImages() {
        return mSelectImages;
    }

    public void setOnImageSelectListener(OnImageSelectListener listener) {
        this.mSelectListener = listener;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImage;
        ImageView ivSelectIcon;
        ImageView ivMasking;
        ImageView ivGif;
        ImageView ivCamera;
        TextView tv_video_duration;

        public ViewHolder(View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.iv_image);
            ivSelectIcon = itemView.findViewById(R.id.iv_select);
            ivMasking = itemView.findViewById(R.id.iv_masking);
            ivGif = itemView.findViewById(R.id.iv_gif);
            ivCamera = itemView.findViewById(R.id.iv_camera);
            tv_video_duration = itemView.findViewById(R.id.tv_video_duration);
        }
    }

    public interface OnImageSelectListener {
        void OnImageSelect(List<MediaInfo> selectImage, boolean isSelect);
    }

    public interface OnItemClickListener {
        void OnItemClick(MediaInfo image, int position);

        void OnCameraClick();
    }
}
