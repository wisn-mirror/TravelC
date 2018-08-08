package com.travel.library.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.alibaba.android.arouter.launcher.ARouter;
import com.travel.library.base.BaseFragment;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Wisn on 2018/5/14 下午5:09.
 */
public class MFragmentPagerAdapter extends FragmentPagerAdapter {
    HashMap<String, BaseFragment> data;
    List<String> pages = null;
    List<String> titles = null;

    public MFragmentPagerAdapter(FragmentManager fm, List<String> pages) {
        super(fm);
        data = new HashMap<>(pages.size());
        this.pages = pages;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    @Override
    public BaseFragment getItem(int position) {
        BaseFragment fragment = data.get(pages.get(position));
        if (fragment == null) {
            fragment = (BaseFragment) ARouter.getInstance().build(pages.get(position)).navigation();
            Bundle bundle = new Bundle();
            bundle.putInt(BaseFragment.Index_Tag, position);
            fragment.setArguments(bundle);
            if(titles!=null){
                data.put(titles.get(position), fragment);
            }else{
                data.put(pages.get(position), fragment);
            }
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return pages.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (titles != null && titles.size() > position) return titles.get(position);
        return "";
    }
}

