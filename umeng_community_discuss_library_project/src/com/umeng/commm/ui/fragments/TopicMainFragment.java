package com.umeng.commm.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.umeng.comm.core.utils.CommonUtils;
import com.umeng.comm.core.utils.ResFinder;
import com.umeng.commm.ui.adapters.viewholders.NavigationCommandImpl;
import com.umeng.common.ui.configure.MainItem;
import com.umeng.common.ui.configure.MainTopicItem;
import com.umeng.common.ui.configure.parseJson;
import com.umeng.common.ui.fragments.CategoryFragment;
import com.umeng.common.ui.fragments.FocusedTopicFragment;
import com.umeng.common.ui.fragments.RecommendTopicFragment;
import com.umeng.common.ui.fragments.TopicFragment;
import com.umeng.common.ui.fragments.TopicMainBaseFragment;


/**
 * Created by wangfei on 15/11/26.
 */
public class TopicMainFragment extends TopicMainBaseFragment {

    /**
     * 布局加载LayoutInflater
     */
    protected LayoutInflater mLayoutInflater;
    /**
     * 根视图
     */


    private boolean mIsInit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        CommonUtils.saveComponentImpl(getActivity());// 注意此处必须保存登录组件的信息
        mLayoutInflater = inflater;
        mRootView = mLayoutInflater.inflate(ResFinder.getResourceId(ResFinder.ResType.LAYOUT, "umeng_comm_main_topic"), container, false);
        for (MainItem temp:parseJson.mainItems){
            if (temp.style.equals("topic")){
                list = temp.topicItems;
            }
        }
        initFragment();
        init();
        initswitchListener();
        initSwitchView();
        return mRootView;
    }

    protected void init() {
        if (mIsInit) {
            return;
        }
        mIsInit = true;
        if (getFragmentManager() != null) {
            getFragmentManager().beginTransaction().replace(ResFinder.
                    getResourceId(ResFinder.ResType.ID, "id_content"),
                    list.size() == 0 ? topicBaseFragments.get(2) : topicBaseFragments.get(0))
                    .commitAllowingStateLoss();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        init();
//        if (transaction == null){
//            transaction = getFragmentManager().beginTransaction();
//        }
//
//                transaction.replace(ResFinder.getResourceId(ResFinder.ResType.ID,"id_content"),categoryFragment).commit();
    }

    public void ChangeFragment(int num) {
        switch (num) {
            case 0:
                getFragmentManager().beginTransaction().replace(ResFinder.getResourceId(ResFinder.ResType.ID, "id_content"), topicBaseFragments.get(0)).commit();
                break;
            case 1:
                getFragmentManager().beginTransaction().replace(ResFinder.getResourceId(ResFinder.ResType.ID, "id_content"), topicBaseFragments.get(1)).commit();
                break;
            case 2:
                getFragmentManager().beginTransaction().replace(ResFinder.getResourceId(ResFinder.ResType.ID, "id_content"), topicBaseFragments.get(2)).commit();
                break;
        }
    }


    @Override
    public void initFragment() {
        if (list.size() == 0) {
            FocusedTopicFragment focusedTopicFragment = new FocusedTopicFragment();
            focusedTopicFragment.setNavigation(new NavigationCommandImpl(getActivity()));
            topicBaseFragments.add(focusedTopicFragment);
            RecommendTopicFragment recommendTopicFragment = RecommendTopicFragment.newRecommendTopicFragment();
            recommendTopicFragment.setNavigation(new NavigationCommandImpl(getActivity()));
            recommendTopicFragment.setShowActionbar(false);
            topicBaseFragments.add(recommendTopicFragment);
            CategoryFragment categoryFragment = new CategoryFragment();
            categoryFragment.setNavigation(new NavigationCommandImpl(getActivity()));
            categoryFragment.setShowActionbar(false);
            topicBaseFragments.add(categoryFragment);
        } else {
            for (MainTopicItem temp : list) {
                topicBaseFragments.add(getTopicFragment(temp));
            }
        }

    }

    public Fragment getTopicFragment(MainTopicItem item) {
        String name = item.style;
        TopicFragment topicFragment = new TopicFragment();
        topicFragment.setNavigation(new NavigationCommandImpl(getActivity()));
        if (name.equals("focus")) {
            FocusedTopicFragment focusedTopicFragment = new FocusedTopicFragment();
            focusedTopicFragment.setNavigation(new NavigationCommandImpl(getActivity()));
            focusedTopicFragment.isShowSearchBar(item.isSearch);
            return focusedTopicFragment;
        } else if (name.equals("alltopic")) {
            topicFragment.isShowSearchBar(item.isSearch);
            return topicFragment;
        } else if (name.equals("allcategory")) {
            CategoryFragment categoryFragment = new CategoryFragment();
            categoryFragment.setNavigation(new NavigationCommandImpl(getActivity()));
            categoryFragment.isShowSearchBar(item.isSearch);
            return categoryFragment;
        } else if (name.equals("recommend")) {
            RecommendTopicFragment RecommentFragment = new RecommendTopicFragment();
            RecommentFragment.setShowActionbar(false);
            RecommentFragment.setNavigation(new NavigationCommandImpl(getActivity()));
            RecommentFragment.isShowSearchBar(item.isSearch);
            return RecommentFragment;
        }
        return topicFragment;
    }

}
