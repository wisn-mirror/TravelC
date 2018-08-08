package com.travel.library.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.travel.library.R;
import com.travel.library.commons.mvp.BaseView;
import com.travel.library.utils.DensityUtils;
import com.travel.library.utils.NetUtils;
import com.travel.library.utils.ToastUtils;
import com.travel.library.view.dialog.ProgressDialog;
import com.travel.library.view.swipebacklayout.SwipeBackActivity;

/**
 * Created by Wisn on 2018/5/4 上午9:02.
 */

public abstract class BaseActivity extends SwipeBackActivity implements BaseView,View.OnClickListener {
    public static String Index_Tag = "Index_Tag_activity";
    public ImageView iv_back;
    private boolean isFrist = true;

    public View viewFailed;
    public LinearLayout ll_notdataH5;
    public TextView tv_faild;

    /**
     * TitleView相关--对应（R.layout.common_title_layout）
     */
    protected TextView backView;
    protected TextView contentView;
    protected TextView rightView;

    public ProgressDialog progressDialog;
    private int progressDialogCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(bindLayout());
        hideActionBar();
        initView(this);
    }


    protected void initTitle() {
        backView = (TextView) findViewById(R.id.common_back_iv);
        if (backView != null) {
            backView.setOnClickListener(this);
        }
        contentView = (TextView) findViewById(R.id.common_content_tv);
        rightView = (TextView) findViewById(R.id.common_right_tv);
    }

    public void setTitleText(String title) {
        if (contentView != null) {
            contentView.setText(title);
        }
    }

    public void hideActionBar() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
        }
    }

    public void showActionBar() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.show();
        }
    }


    public abstract int bindLayout();

    public abstract void initView(Activity activity);

    public abstract void initData(Context context);


    /**
     * 显示载入框
     *
     * @param msg
     */
    public void showLoading(String msg) {
        if (progressDialogCount <= 0 || progressDialog == null) {
            if (progressDialog == null) {
                progressDialog = ProgressDialog.getInstance(msg);
            }
            if (progressDialog != null) {
                progressDialog.show(getSupportFragmentManager(), progressDialog.getTag());
            }
        }
        progressDialogCount++;
    }

    public void showLoading() {
        showLoading(getString(R.string.loading_waiting));
    }

    /**
     * 取消载入框
     */
    public void hideLoading() {
        if (--progressDialogCount <= 0) {
            progressDialogCount = 0;
            if (progressDialog != null && !progressDialog.isHidden()) {
                progressDialog.dismiss();
                progressDialog.dismissAllowingStateLoss();            }
        }
    }

    protected void showFailed(boolean showFlag, int type) {
        if (viewFailed != null) {
            viewFailed.setVisibility(showFlag ? View.VISIBLE : View.GONE);
            if (type == 0) {
                tv_faild.setText(R.string.a_o_load_faile);
            } else if (type == 1) {
                tv_faild.setText(R.string.Network_convulsions_please_checking);
            }
        } else {
            initFailed(showFlag, type);
        }
    }

    public void initFailed(boolean showFlag, int type) {
        View view = findViewById(android.R.id.content);
        if (view instanceof FrameLayout) {
            viewFailed = LayoutInflater.from(this).inflate(R.layout.lib_view_failed_load, null);
            viewFailed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadAgain();
                }
            });
            tv_faild = viewFailed.findViewById(R.id.tv_faild);
            ll_notdataH5 = viewFailed.findViewById(R.id.ll_notdataH5);
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            lp.gravity = Gravity.TOP | Gravity.RIGHT;
            lp.setMargins(0, DensityUtils.dip2px(43), 0, 0);
            viewFailed.setLayoutParams(lp);
            ((FrameLayout) view).addView(viewFailed);
            viewFailed.setVisibility(View.GONE);
            showFailed(showFlag, type);
        }
    }
    protected void loadAgain() {
        if (NetUtils.isConnected()) {
            showFailed(false, 1);
            initData(this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isFrist) {
            initData(this);
            isFrist = false;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.common_back_iv) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onNetStart(String tag, String startMsg) {

    }

    @Override
    public void onNetError(String tag, String errorMsg) {
        hideLoading();
        if (!TextUtils.isEmpty(errorMsg)) {
            ToastUtils.show(errorMsg);
        }
    }


    @Override
    public void onNetFinish(String tag, String startMsg) {
        hideLoading();
    }
}
