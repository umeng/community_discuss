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
package com.umeng.common.ui.presenter.impl;

import android.location.Location;

import com.umeng.comm.core.beans.FeedItem;
import com.umeng.comm.core.listeners.Listeners.SimpleFetchListener;
import com.umeng.comm.core.nets.responses.FeedsResponse;
import com.umeng.comm.core.sdkmanager.LocationSDKManager;
import com.umeng.common.ui.mvpview.MvpFeedView;

import java.util.Comparator;


/**
 * 附近的Feed Presenter
 */
public class NearbyFeedPresenter extends FeedListPresenter {

    /**
     * @param view
     */
    public NearbyFeedPresenter(MvpFeedView view) {
        super(view);
    }

    @Override
    protected void loadDataOnRefresh() {
        super.loadDataOnRefresh();
        LocationSDKManager.getInstance().getCurrentSDK().requestLocation(mContext,
                new SimpleFetchListener<Location>() {

                    @Override
                    public void onComplete(Location location) {
                        if (location == null) {
                            setLoadingState(false);
                            mFeedView.onRefreshEnd();
                            return;
                        }
                        mCommunitySDK.fetchNearByFeed(location, mRefreshListener);
                    }
                });
    }

    @Override
    public void loadDataFromDB() {
        mDatabaseAPI.getFeedDBAPI().loadNearbyFeed(mDbFetchListener);
    }

    @Override
    protected void beforeDeliveryFeeds(FeedsResponse response) {
        if (isNeedRemoveOldFeeds.get()) {
            isNeedRemoveOldFeeds.set(false);
        }
        response.result = response.resultWithoutTop;
    }

    private Comparator<FeedItem> mComparator = new Comparator<FeedItem>() {

        @Override
        public int compare(FeedItem lhs, FeedItem rhs) {
            return lhs.distance - rhs.distance;
        }
    };

    @Override
    protected Comparator<FeedItem> getFeedCompartator() {
        return mComparator;
    }

}
