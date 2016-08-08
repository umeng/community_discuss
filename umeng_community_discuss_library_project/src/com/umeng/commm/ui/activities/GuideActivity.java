package com.umeng.commm.ui.activities;

import android.content.DialogInterface;

import com.umeng.commm.ui.adapters.viewholders.NavigationCommandImpl;
import com.umeng.common.ui.fragments.RecommendUserFragment;
import com.umeng.common.ui.activities.GuideBaseActivity;
import com.umeng.common.ui.fragments.RecommendTopicFragment;

/**
 * Created by wangfei on 16/1/25.
 */
public class GuideActivity extends GuideBaseActivity {
    @Override
    protected void showTopicFragment() {
        RecommendTopicFragment topicRecommendDialog = RecommendTopicFragment.newRecommendTopicFragment();
        topicRecommendDialog.isShowSearchBar(false);
        topicRecommendDialog.setShowActionbar(true);
        topicRecommendDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                showRecommendUserFragment();
            }

        });
        topicRecommendDialog.setNavigation(new NavigationCommandImpl(this));
        addFragment(mContainer, topicRecommendDialog);
    }

    @Override
    protected void showRecommendUserFragment() {
        setFragmentContainerId(mContainer);
        RecommendUserFragment recommendUserFragment = new RecommendUserFragment();
        recommendUserFragment.setNavigation(new NavigationCommandImpl(this));
        recommendUserFragment.setShowActionbar(true);
        replaceFragment(mContainer, recommendUserFragment);
    }
}
