/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2015 Umeng, Inc
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.umeng.commm.ui.fragments;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.TextView;

import com.umeng.comm.core.beans.CommUser;
import com.umeng.comm.core.impl.CommunitySDKImpl;
import com.umeng.comm.core.listeners.Listeners.OnResultListener;
import com.umeng.comm.core.login.LoginListener;
import com.umeng.comm.core.utils.CommonUtils;
import com.umeng.comm.core.utils.ResFinder;
import com.umeng.commm.ui.activities.PostFeedActivity;
import com.umeng.commm.ui.activities.SearchActivity;
import com.umeng.common.ui.presenter.impl.FeedListPresenter;
import com.umeng.common.ui.presenter.impl.FollowedFeedPresenter;

/**
 * 主页的三个Tab之一的消息流页面,该页面返回用户关注的人、话题的Feed
 */
public class AllFeedsFragment extends PostBtnAnimFragment<FeedListPresenter> {
    private TextView mTipView; // 更新条数提示
    private boolean isShowToast = false; // 只有在显示的fragment是当前fragment时，才显示Toast

    private View mLoginHolder;

    @Override
    protected void initViews() {
        super.initViews();
        mLoginHolder = findViewById(ResFinder.getId("umeng_comm_login"));
        findViewById(ResFinder.getId("umeng_comm_tologin")).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CommunitySDKImpl.getInstance().login(getActivity(), new LoginListener() {
                            @Override
                            public void onStart() {
                                if (getActivity() != null && !getActivity().isFinishing()) {
                                    mProcessDialog.show();
                                }
                            }

                            @Override
                            public void onComplete(int stCode, CommUser userInfo) {
                                if (getActivity() != null && !getActivity().isFinishing()) {
                                    mProcessDialog.dismiss();
                                }
                            }
                        });
//                });
                    }
                }
        );
    }

    @Override
    protected FeedListPresenter createPresenters() {
        super.createPresenters();
        FeedListPresenter presenter = new FollowedFeedPresenter(this, true);
        presenter.setIsShowTopFeeds(false);
        presenter.setOnResultListener(mListener);
        return presenter;
    }

    /**
     * 用户回调显示更新数目
     */
    private OnResultListener mListener = new OnResultListener() {

        @Override
        public void onResult(int nums) {
            if (!isShowToast) {
                return;
            }
            if (nums <= 0) {
                mTipView.setText(ResFinder.getString("umeng_comm_no_newfeed_tips"));
            } else {
                mTipView.setText(ResFinder.getString("umeng_comm_newfeed_tips"));
            }
            showNewFeedTips();
        }
    };

    /**
     * 显示[更新N条新feed]】的View
     */
    private void showNewFeedTips() {
        mTipView.setVisibility(View.VISIBLE);
        Animation showAnimation = new AlphaAnimation(0.2f, 1);
        showAnimation.setDuration(400);
        showAnimation.setFillAfter(true);
        showAnimation.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                dismissNewFeedTips();
            }
        });
        mTipView.startAnimation(showAnimation);
    }

    /**
     * 隐藏[更新N条feed]的View。注意：该方法必须由{@link #showNewFeedTips}的AnimationListener回调中被调用
     */
    private void dismissNewFeedTips() {
        Animation animation = new AlphaAnimation(1, 0);
        animation.setStartOffset(800);
        animation.setDuration(500);
        animation.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mTipView.setVisibility(View.GONE);
            }
        });
        mTipView.startAnimation(animation);
    }

    @Override
    protected void showPostButtonWithAnim() {
        AlphaAnimation showAnim = new AlphaAnimation(0.5f, 1.0f);
        showAnim.setDuration(500);

        if (mPostBtn != null) {
            mPostBtn.setVisibility(View.VISIBLE);
            mPostBtn.startAnimation(showAnim);
        }
    }



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isShowToast = isVisibleToUser;
    }

    @Override
    public void onResume() {
        super.onResume();
        showHotView(false);
    }

    @Override
    public void showLoginView() {
        super.showLoginView();
        if (mLoginHolder != null) {
            clearListView();
            mLoginHolder.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideLoginView() {
        super.hideLoginView();
        if (mLoginHolder != null) {
            mLoginHolder.setVisibility(View.GONE);
        }
    }
}
