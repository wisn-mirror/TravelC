package com.travel.library.view.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.travel.library.R;

/**
 * Created by ody on 2016/8/29.
 */
public class BasePopupWindow<T> extends PopupWindow implements View.OnClickListener {

    public View mMenuView;
    public Context mContext;
    public T mData;
    WindowManager.LayoutParams params;

    public BasePopupWindow(Context mContext) {
        super(mContext);
        this.mContext = mContext;
//        WindowManager manager = ((Activity) mContext).getWindowManager();
        if (null == params) {
            params = ((Activity) mContext).getWindow().getAttributes();
        }
    }

    public BasePopupWindow(Context mContext, T data) {
        super(mContext);
        this.mContext = mContext;
        this.mData = data;
//        WindowManager manager = ((Activity) mContext).getWindowManager();
        if (null == params) {
            params = ((Activity) mContext).getWindow().getAttributes();
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        params.alpha = 1f;
        ((Activity) mContext).getWindow().setAttributes(params);
    }

    @Override
    public void showAsDropDown(View anchor) {
        super.showAsDropDown(anchor);
        params.alpha = 0.7f;
        ((Activity) mContext).getWindow().setAttributes(params);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        params.alpha = 0.7f;
        ((Activity) mContext).getWindow().setAttributes(params);
    }

    public void init(Context mContext, int layout) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(layout, null);
        // 设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimBottom);

//         实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(00000000);
//         设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
    }

    /**
     * 设置点击以外部分关闭弹窗
     *
     * @param view
     */
    public void dismissWindow(final View view) {

        mMenuView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int[] location = new int[2];
                view.getLocationOnScreen(location);
//                int x = location[0];
                final int height = location[1];
//                int height = view.getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}