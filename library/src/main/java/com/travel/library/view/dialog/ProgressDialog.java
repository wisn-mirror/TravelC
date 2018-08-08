package com.travel.library.view.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.travel.library.R;
import com.travel.library.utils.GlideUtil;


/**
 * Created by Wisn on 2018/4/16 上午11:07.
 */

public class ProgressDialog extends DialogFragment {
    private String text;

    public static ProgressDialog getInstance(String text) {
        ProgressDialog customViewDialog = new ProgressDialog();
        customViewDialog.setText(text);
        return customViewDialog;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogProgress);
    }

    public void setText(String text) {
        this.text = text;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setDimAmount(0.4f);//背景黑暗度
        return inflater.inflate(R.layout.lib_dialog_progress, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            ImageView imageView = view.findViewById(R.id.iv_load);
            GlideUtil.load(getContext(), R.drawable.lib_dialog_loading, imageView);
            TextView textView = view.findViewById(R.id.tv_msg);
            textView.setText(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = Gravity.CENTER | Gravity.CENTER;
        window.setAttributes(attributes);
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                dialog.dismiss();
                return false;
            }
        });

    }

    public void onStart() {
        super.onStart();

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }


}