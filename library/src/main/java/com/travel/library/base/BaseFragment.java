package com.travel.library.base;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.travel.library.R;
import com.travel.library.commons.mvp.BaseView;
import com.travel.library.utils.ToastUtils;
import com.travel.library.view.dialog.ProgressDialog;

/**
 * Created by Wisn on 2018/5/6 上午10:20.
 */

public abstract class BaseFragment extends Fragment implements FragmentUserVisibleController.UserVisibleCallback, BaseView {
    public static String Index_Tag = "Index_Tag_fragment";
    private TextView tv_title;
    private ImageView iv_back;
    public ProgressDialog progressDialog;
    private int progressDialogCount;
    private boolean isInit; // 是否可以开始加载数据
    private boolean isCreated;
    /**
     * 当前Fragment渲染的视图View
     **/
    private View mContextView = null;
    private FragmentUserVisibleController userVisibleController;

    public BaseFragment() {
        userVisibleController = new FragmentUserVisibleController(this, this);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isCreated = true;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null != mContextView) {
            ViewGroup parent = (ViewGroup) mContextView.getParent();
            if (null != parent) parent.removeView(mContextView);
        } else {
            try {
                mContextView = inflater.inflate(bindLayout(), null);
                initPublicView(mContextView);
                initView(mContextView);
            } catch (Exception e) {
                getActivity().finish();
                return mContextView;
            }
        }
        initEvent();
        return mContextView;
    }

    public void initEvent() {

    }

    protected void initPublicView(View view) {
        tv_title = view.findViewById(R.id.tv_title);
        iv_back = view.findViewById(R.id.iv_back);
        if (iv_back != null) {
            iv_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });
        }
    }

    public void setTitle(String title, boolean isShowBack) {
        if (tv_title != null) {
            tv_title.setText(title);
        }
        if (isShowBack && iv_back != null) {
            iv_back.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isInit = true;
        userVisibleController.activityCreated();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        userVisibleController.resume();
        if (getUserVisibleHint()) {
            if (isInit && isCreated) {
                isInit = false;// 加载数据完成
                //System.out.println("应该去加载数据了");
                initData();
            }
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        userVisibleController.setUserVisibleHint(isVisibleToUser);
        // 每次切换fragment时调用的方法
        if (isVisibleToUser) {
            if (isInit && isCreated) {
                isInit = false;//加载数据完成
                initData();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        userVisibleController.pause();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (progressDialog != null && !progressDialog.isHidden()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public abstract int bindLayout();

    public abstract void initView(View view);

    public abstract void initData();

    @Override
    public void setWaitingShowToUser(boolean waitingShowToUser) {
        userVisibleController.setWaitingShowToUser(waitingShowToUser);
    }

    @Override
    public boolean isWaitingShowToUser() {
        return userVisibleController.isWaitingShowToUser();
    }

    @Override
    public boolean isVisibleToUser() {
        return userVisibleController.isVisibleToUser();
    }

    @Override
    public void callSuperSetUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onVisibleToUserChanged(boolean isVisibleToUser, boolean invokeInResumeOrPause) {

    }

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
                progressDialog.show(getActivity().getSupportFragmentManager(), progressDialog.getTag());
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
