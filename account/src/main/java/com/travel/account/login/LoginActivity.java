package com.travel.account.login;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.travel.account.R;
import com.travel.library.commons.common.CommonActivity;
import com.travel.library.commons.constants.ARoutePath;
import com.travel.library.commons.constants.Constants;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;

/**
 * Created by Wisn on 2018/5/22 下午5:52.
 */
@Route(path = ARoutePath.account.LoginActivity)
public class LoginActivity extends CommonActivity<LoginM, LoginP> implements View.OnClickListener, Login.LoginV {

    private TextView bt_login;
    private EditText et_account;
    private EditText et_password;
    private TextView tv_forgetpassword;
    private ImageView iv_passsword_close;
    private ImageView iv_passsword_eye;
    private ImageView iv_account_close;
    private TextWatcher accountwatcher;
    private TextWatcher passwordwatcher;
    private boolean isShowPassword;

    @Override
    public int bindLayout() {
        return R.layout.account_activity_login;
    }

    @Override
    public void initView(Activity activity) {
        setTitleText("登陆");
        bt_login = (TextView) findViewById(R.id.bt_login);
        tv_forgetpassword = (TextView) findViewById(R.id.tv_forgetpassword);
        et_account = (EditText) findViewById(R.id.et_account);
        et_password = (EditText) findViewById(R.id.et_password);
        iv_passsword_close = (ImageView) findViewById(R.id.iv_passsword_close);
        iv_passsword_eye = (ImageView) findViewById(R.id.iv_passsword_eye);
        iv_account_close = (ImageView) findViewById(R.id.iv_account_close);
        bt_login.setOnClickListener(this);
        tv_forgetpassword.setText("忘记密码请联系 " + Constants.consumer_hotline);
        tv_forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData mClipData = ClipData.newPlainText("客服电话", Constants.consumer_hotline);
                cm.setPrimaryClip(mClipData);
                showTip("客服电话" + Constants.consumer_hotline + "已复制粘贴板");
            }
        });
        et_account.setSelection(et_account.getText().length());
        accountwatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(et_account.getText())) {
                    iv_account_close.setVisibility(View.GONE);
                } else {
                    iv_account_close.setVisibility(View.VISIBLE);
                }
            }
        };
        et_account.addTextChangedListener(accountwatcher);
        passwordwatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(et_password.getText())) {
                    iv_passsword_close.setVisibility(View.INVISIBLE);
                    iv_passsword_eye.setVisibility(View.GONE);
                } else {
                    iv_passsword_close.setVisibility(View.VISIBLE);
                    iv_passsword_eye.setVisibility(View.VISIBLE);
                }
            }
        };
        et_password.addTextChangedListener(passwordwatcher);
        iv_passsword_close.setOnClickListener(this);
        iv_account_close.setOnClickListener(this);
        iv_passsword_eye.setOnClickListener(this);
    }

    @Override
    public void initData(Context context) {

    }

    @Override
    public void onClick(View v) {
        if (v == bt_login) {
            login();
        } else if (v == iv_passsword_close) {
            et_password.removeTextChangedListener(passwordwatcher);
            et_password.setText("");
            et_password.addTextChangedListener(passwordwatcher);
            iv_passsword_close.setVisibility(View.INVISIBLE);
            iv_passsword_eye.setVisibility(View.GONE);
        } else if (v == iv_account_close) {
            et_account.removeTextChangedListener(accountwatcher);
            et_account.setText("");
            et_account.addTextChangedListener(accountwatcher);
            iv_account_close.setVisibility(View.GONE);
        } else if (v == iv_passsword_eye) {
            isShowPassword = !isShowPassword;
            if (isShowPassword) {
                et_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                iv_passsword_eye.setImageResource(R.mipmap.account_login_btn_eyes);
            } else {
                et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                iv_passsword_eye.setImageResource(R.mipmap.account_login_btn_eyes2);
            }
            et_password.setSelection(et_password.getText().length());

        }
    }


    public void login() {
        String account = et_account.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            showTipTop("请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            showTipTop("请输入密码");
            return;
        }
        ((LoginP) mPresenter).Login(account, password);
    }

    @Override
    public void loginSuccess() {
        ARouter.getInstance().build(ARoutePath.App.MainActivity)
                .navigation();
        this.finish();
    }

    @Override
    public void loginFailed(String msg) {
        showTip(msg);
    }

    @Override
    public void onNetStart(String tag, String startMsg) {
        super.onNetStart(tag, startMsg);
        showLoading("登录中...");
    }

    public void showTipTop(String msg) {
        SnackbarManager.show(
                Snackbar.with(getApplicationContext())
                        .position(Snackbar.SnackbarPosition.TOP)
                        .margin(25, 15)
                        .text(msg)
                , (android.view.ViewGroup) findViewById(R.id.rl_content));
    }

    public void showTip(String msg) {
        Snackbar.with(getApplicationContext())
                .text(msg)
                .show(this);
    }

}
