package com.umeng.commm.ui.activities;


import com.umeng.commm.ui.adapters.viewholders.NavigationCommandImpl;
import com.umeng.common.ui.activities.SearchTopicBaseActivity;

import com.umeng.common.ui.fragments.SearchTopicFragment;

/**
 * Created by wangfei on 15/12/8.
 */
public class SearchTopicActivity extends SearchTopicBaseActivity<SearchTopicFragment> {


    @Override
    protected SearchTopicFragment createSearchTopicFragment() {
        SearchTopicFragment searchTopicFragment = new SearchTopicFragment();
        searchTopicFragment.setNavigation(new NavigationCommandImpl(this));
        return searchTopicFragment;
    }

}
