package com.travel;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.RadioGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.travel.library.base.BaseFragment;
import com.travel.library.commons.Auth;
import com.travel.library.commons.bean.UserInfo;
import com.travel.library.commons.bean.update.UpdateVersionRequest;
import com.travel.library.commons.common.CommonActivity;
import com.travel.library.commons.common.CommonFragment;
import com.travel.library.commons.constants.ARoutePath;
import com.travel.library.commons.event.MainPageEvent;
import com.travel.library.commons.service.UpdateVersionService;
import com.travel.library.utils.AppUtils;
import com.travel.library.utils.LogUtilsLib;
import com.travel.library.utils.Utils;
import com.travel.library.view.dialog.ChooseDialog;
import com.travel.library.view.tipview.TipRadioButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

@Route(path = ARoutePath.App.MainActivity)
public class MainActivity extends CommonActivity implements RadioGroup.OnCheckedChangeListener, UpdateVersionService.UpdateCallback {
    private static final String TAG = "MainActivity";
    Fragment oldFragment;
    Fragment homePager;
    Fragment classification;
    Fragment fragmentIM;
    Fragment shopcart;
    Fragment mine;
    private RadioGroup rg_main_bottom;
    private TipRadioButton rb_homepage;
    private TipRadioButton rb_classification;
    private TipRadioButton rb_im;
    private TipRadioButton rb_shopcart;
    private TipRadioButton rb_mine;
    private UserInfo userInfo;
    private ChooseDialog updateVersionDialog;

    @Override
    public int bindLayout() {
        return R.layout.app_activity_main;
    }

    @Override
    public void initView(Activity activity) {
        EventBus.getDefault().register(this);
        userInfo = Auth.getUserInfo();
        swipeBackLayout.setEnableGesture(false);
        homePager = (Fragment) ARouter.getInstance().build(ARoutePath.account.MineFragment).navigation();
        classification = (Fragment) ARouter.getInstance().build(ARoutePath.account.MineFragment).navigation();
        fragmentIM = (Fragment) ARouter.getInstance().build(ARoutePath.account.MineFragment).navigation();
        shopcart = (Fragment) ARouter.getInstance().build(ARoutePath.account.MineFragment).navigation();
        mine = (Fragment) ARouter.getInstance().build(ARoutePath.account.MineFragment).navigation();
        rb_homepage = (TipRadioButton) findViewById(R.id.rb_homepage);
        rb_classification = (TipRadioButton) findViewById(R.id.rb_classification);
        rb_im = (TipRadioButton) findViewById(R.id.rb_im);
        rb_shopcart = (TipRadioButton) findViewById(R.id.rb_shopcart);
        rb_mine = (TipRadioButton) findViewById(R.id.rb_mine);
        rg_main_bottom = (RadioGroup) findViewById(R.id.rg_main_bottom);
        setFirstFragment();
        rg_main_bottom.setOnCheckedChangeListener(this);
    }

    public void setFirstFragment() {
        int fragmenttag = getIntent().getIntExtra(CommonActivity.Index_Tag, 0);
        int fragmenttagIndex = getIntent().getIntExtra(CommonFragment.Index_Tag, 0);
        if (fragmenttag != 0) {
            tipMessage(new MainPageEvent(fragmenttag, true));
        }
        Bundle bundle = new Bundle();
        bundle.putInt(CommonActivity.Index_Tag, fragmenttag);
        bundle.putInt(BaseFragment.Index_Tag, fragmenttagIndex);
        Fragment fragmentByIndex = getFragmentByIndex(fragmenttag);
        if(fragmentByIndex!=null){
            fragmentByIndex.setArguments(bundle);
            addFragment(fragmentByIndex);
        }
    }


    @Override
    public void initData(Context context) {
        //版本更新
        UpdateVersionRequest updateVersionRequest = new UpdateVersionRequest(AppUtils.getPackageName(), AppUtils.getAppVersionName());
        ((UpdateVersionService) ARouter.getInstance().build(ARoutePath.update.UpdateVersionServiceImpl).navigation()).checkUpdateVersion(updateVersionRequest, this);
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_homepage:
                switchFragment(homePager);
                break;
            case R.id.rb_classification:
                switchFragment(classification);
                break;
            case R.id.rb_im:
                switchFragment(fragmentIM);
                break;
            case R.id.rb_shopcart:
                switchFragment(shopcart);
                break;
            case R.id.rb_mine:
                switchFragment(mine);
                break;
            default:
                break;
        }
    }

    public Fragment getFragmentByIndex(int index) {
        if (index == 0) return homePager;
        else if (index == 1) return classification;
        else if (index == 2) return fragmentIM;
        else if (index == 3) return shopcart;
        else return mine;
    }

    /**
     * 控制底部消息，提醒，选择状态
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void tipMessage(MainPageEvent event) {
        LogUtilsLib.d("tipMessage" + event.toString());
        if (event.data == null) return;
        switch (event.data) {
            case 0:
                if (event.type == -1) {
                    rb_homepage.setTip();
                } else if (event.type == 1) {
                    rb_homepage.setTipText(event.msg);
                }
                if (event.isSelect) {
                    rg_main_bottom.check(R.id.rb_homepage);
                }
                break;
            case 1:
                if (event.type == -1) {
                    rb_classification.setTip();
                } else if (event.type == 1) {
                    rb_classification.setTipText(event.msg);
                }
                if (event.isSelect) {
                    rg_main_bottom.check(R.id.rb_classification);
                }
                break;
            case 2:
                if (event.type == -1) {
                    rb_im.setTip();
                } else if (event.type == 1) {
                    rb_im.setTipText(event.msg);
                }
                if (event.isSelect) {
                    rg_main_bottom.check(R.id.rb_im);
                }
                break;
            case 3:
                if (event.type == -1) {
                    rb_shopcart.setTip();
                } else if (event.type == 1) {
                    rb_shopcart.setTipText(event.msg);
                }
                if (event.isSelect) {
                    rg_main_bottom.check(R.id.rb_shopcart);
                }
                break;
            case 4:
                if (event.type == -1) {
                    rb_mine.setTip();
                } else if (event.type == 1) {
                    rb_mine.setTipText(event.msg);
                }
                if (event.isSelect) {
                    rg_main_bottom.check(R.id.rb_mine);
                }
                break;
            default:
                break;
        }
    }

    public void switchFragment(Fragment to) {
        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.centerlayout, to).commitAllowingStateLoss();
        if (oldFragment == null) {
            addFragment(to);
        } else if (to.isAdded() || fragmentManager.getFragments().contains(to)) {
            // 隐藏当前的fragment，显示下一个
            fragmentManager.beginTransaction().hide(oldFragment).show(to).commitAllowingStateLoss();
        } else {
            // 隐藏当前的fragment，add下一个到Activity中
            fragmentManager.beginTransaction().hide(oldFragment).add(R.id.centerlayout, to).commitAllowingStateLoss();
        }
        oldFragment = to;
    }

    public void addFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (!fragment.isAdded()) {
            fragmentManager.beginTransaction().add(R.id.centerlayout, fragment).commitAllowingStateLoss();
            oldFragment = fragment;
        }
    }

    /**
     * 版本更新
     *
     * @param mandatory   是否强制升级
     * @param downloadUrl 下载的url
     * @param versionName
     * @param description 版本描述
     */
    @Override
    public void newVersionCallBack(final boolean mandatory, final String downloadUrl, final String versionName, final String description) {
        updateVersionDialog = ChooseDialog.getInstance(new ChooseDialog.ChrooseCallBack() {
            @Override
            public void call(boolean ischroose) {
                if (ischroose) {
                    ((UpdateVersionService) ARouter.getInstance().build(ARoutePath.update.UpdateVersionServiceImpl).navigation()).addDownloadTask(downloadUrl, versionName, description);
                } else if (mandatory) {
                    //如果是强制升级，取消了，将退出app
                    Utils.exitApp(null);
                }
            }
        });
        if (mandatory) {
            updateVersionDialog.setText("需要升级新版本" + versionName, description, "升级", null, false, true);
        } else {
            updateVersionDialog.setText("发现新版本" + versionName, description, "立即升级", "暂不升级", false, false);
        }
        updateVersionDialog.show(getSupportFragmentManager(), "update");
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

}
