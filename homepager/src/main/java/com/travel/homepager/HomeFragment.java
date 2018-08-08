package com.travel.homepager;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.travel.library.commons.common.CommonFragment;
import com.travel.library.commons.constants.ARoutePath;
import com.travel.library.commons.event.MainPageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Wisn on 2018/5/14 下午3:07.
 */
@Route(path = ARoutePath.homepager.HomeFragment)
public class HomeFragment extends CommonFragment implements   View.OnClickListener {


    @Override
    public int bindLayout() {
        return R.layout.home_fragment_homepager;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void initEvent() {
        super.initEvent();
        boolean registered = EventBus.getDefault().isRegistered(this);
        if (!registered) {
            EventBus.getDefault().register(this);
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void homeEvent(MainPageEvent event) {}

    @Override
    public void initData() {

    }


    @Override
    public void onClick(View v) {

    }



    @Override
    public void onDestroy() {
        boolean registered = EventBus.getDefault().isRegistered(this);
        if (registered) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }

    @Override
    public void onNetStart(String tag, String startMsg) {
        super.onNetStart(tag, startMsg);
        showLoading();
    }

}
