package com.travel.library.view.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.travel.library.R;


/**
 * Created by Wisn on 2018/4/16 上午11:07.
 */

public class ChooseDialog extends DialogFragment implements View.OnClickListener {
    private ChrooseCallBack merchantCallBack;
    private TextView tv_title;
    private TextView tv_content;
    private TextView bt_ok;
    private TextView bt_cancle;
    private String title, contentStr, okStr, cancelStr;
    private boolean cancelable = false, onlyOkButton = false;
    private View v_cancle_line;

    public static ChooseDialog getInstance(ChrooseCallBack merchantCallBack) {
        ChooseDialog customViewDialog = new ChooseDialog();
        customViewDialog.setChrooseCallBack(merchantCallBack);
        return customViewDialog;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogChoose);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setDimAmount(0.4f);//背景黑暗度
        return inflater.inflate(R.layout.lib_dialog_choose, container, false);
    }

    private void setChrooseCallBack(ChrooseCallBack merchantCallBack) {
        this.merchantCallBack = merchantCallBack;
    }

    public void setText(String title, String okStr, String cancelStr) {
        setText(title, null, okStr, cancelStr, false, false);
    }

    public void setText(String title, String contentStr, String okStr, String cancelStr, boolean cancelable, boolean onlyOkButton) {
        this.title = title;
        this.contentStr = contentStr;
        this.okStr = okStr;
        this.cancelStr = cancelStr;
        this.cancelable = cancelable;
        this.onlyOkButton = onlyOkButton;
    }

    public interface ChrooseCallBack {
        void call(boolean ischroose);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_title = view.findViewById(R.id.tv_title);
        tv_content = view.findViewById(R.id.tv_content);
        bt_ok = view.findViewById(R.id.bt_ok);
        bt_cancle = view.findViewById(R.id.bt_cancle);
        v_cancle_line = view.findViewById(R.id.v_cancle_line);
        bt_ok.setOnClickListener(this);
        bt_cancle.setOnClickListener(this);
        if (!TextUtils.isEmpty(title) && tv_title != null) tv_title.setText(title);
        if (!TextUtils.isEmpty(contentStr) && tv_content != null) {
            tv_content.setText(contentStr);
        } else {
            tv_content.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(okStr) && bt_ok != null) bt_ok.setText(okStr);
        if (!TextUtils.isEmpty(cancelStr) && bt_cancle != null) bt_cancle.setText(cancelStr);
        if (onlyOkButton) {
            bt_cancle.setVisibility(View.GONE);
            v_cancle_line.setVisibility(View.GONE);
        }
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = Gravity.CENTER | Gravity.CENTER;
        window.setAttributes(attributes);
        getDialog().setCancelable(cancelable);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (merchantCallBack != null) {
                        merchantCallBack.call(false);
                    }
                    return true;
                } else if (keyCode == KeyEvent.KEYCODE_MENU) {
                    return true;
                }
                return false;
            }
        });

    }

    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.6), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.bt_cancle) {
            this.dismiss();
            if (merchantCallBack != null) {
                merchantCallBack.call(false);
            }
        } else if (i == R.id.bt_ok) {
            this.dismiss();
            if (merchantCallBack != null) {
                merchantCallBack.call(true);
            }
        }
    }


}