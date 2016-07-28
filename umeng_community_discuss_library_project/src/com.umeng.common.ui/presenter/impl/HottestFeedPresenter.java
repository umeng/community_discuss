package com.umeng.common.ui.presenter.impl;

import com.umeng.comm.core.beans.FeedItem;
import com.umeng.comm.core.constants.ErrorCode;
import com.umeng.comm.core.db.ctrl.impl.DatabaseAPI;
import com.umeng.comm.core.listeners.Listeners;
import com.umeng.comm.core.nets.responses.FeedsResponse;
import com.umeng.comm.core.nets.uitls.NetworkUtils;
import com.umeng.common.ui.mvpview.MvpFeedView;

import java.util.Comparator;
import java.util.List;

/**
 * Created by umeng on 12/1/15.
 */
public class HottestFeedPresenter extends FeedListPresenter {

    private int mDays = 30;

    private boolean isRefreshing;

    public HottestFeedPresenter(MvpFeedView view) {
        super(view);
    }

    public HottestFeedPresenter(MvpFeedView view, boolean isRegisterReceiver) {
        super(view, isRegisterReceiver);
    }

    public void setHOT_DAYS(int days) {
        if (this.mDays != days) {
            setIsNeedRemoveOldFeeds();
        }
        this.mDays = days;
    }

    @Override
    protected void saveDataToDB(List<FeedItem> newFeedItems) {
        mDatabaseAPI.getFeedDBAPI().saveHotFeedToDB(mDays, newFeedItems);
    }

    @Override
    public void loadDataFromDB() {
        DatabaseAPI.getInstance().getFeedDBAPI().loadHotFeeds(mDays, new Listeners.SimpleFetchListener<List<FeedItem>>() {
            @Override
            public void onComplete(List<FeedItem> response) {
                com.umeng.comm.core.utils.Log.d("db", "loading" + response.size());
                mFeedView.clearListView();
                mDbFetchListener.onComplete(response);
            }
        });
    }

    /**
     * @param days
     */
    public void loadDataFromServer(int days) {
        if (days == 1 || days == 3 || days == 7 || days == 30) {
            setHOT_DAYS(days);
            loadDataFromServer();
        }
    }

    /**
     * @param days
     */
    public void loadDataFromDB(int days) {
        if (days == 1 || days == 3 || days == 7 || days == 30) {
            setHOT_DAYS(days);
            loadDataFromDB();
        }
    }

    @Override
    protected void beforeDeliveryFeeds(FeedsResponse response) {
//        super.beforeDeliveryFeeds(response);
//        mDatabaseAPI.getFeedDBAPI().clearHotFeed(HOT_DAYS);
    }


    @Override
    public void loadDataFromServer() {
        isRefreshing = true;
        mCommunitySDK.fetchTopFeeds(new Listeners.FetchListener<FeedsResponse>() {
            @Override
            public void onStart() {
                mFeedView.onRefreshStart();
                if (mTopFeeds != null) {
                    mTopFeeds.clear();
                }
            }

            @Override
            public void onComplete(FeedsResponse response) {
                if (response.errCode == ErrorCode.NO_ERROR) {
                    mTopFeeds = response.result;
                    for (int i = 0; i < mTopFeeds.size(); i++) {
                        mTopFeeds.get(i).isTop = 1;
                    }
                }
                loadHotFeed();
            }
        });
    }

    private void loadHotFeed() {
        mCommunitySDK.fetchHotestFeeds(new Listeners.FetchListener<FeedsResponse>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onComplete(FeedsResponse response) {
                isRefreshing = false;
                // 根据response进行Toast
                if (NetworkUtils.handleResponseAll(response) && (mTopFeeds == null || mTopFeeds.isEmpty())) {
                    mFeedView.onRefreshEnd();
                    return;
                }

                mFeedView.clearListView();
                mNextPageUrl = response.nextPageUrl;

                final List<FeedItem> newFeedItems = response.result;

                // 对于下拉刷新，仅在下一个地址为空（首次刷新）时设置下一页地址
//                if (TextUtils.isEmpty(mNextPageUrl) && isNeedRemoveOldFeeds.get()) {
//                    mNextPageUrl = response.nextPageUrl;
//                }

                beforeDeliveryFeeds(response);

                addTopFeedToHeader(newFeedItems);
                // 更新数据
                appendFeedItemsToHeader(newFeedItems);

                // 保存加载的数据。如果该数据存在于DB中，则替换成最新的，否则Insert一条新纪录
                saveDataToDB(newFeedItems);

                dealGuestMode(response.isVisit);
                mFeedView.onRefreshEnd();
            }
        }, mDays, 0);
    }

    @Override
    protected void fetchDataFromServerByLogin() {
//        mCommunitySDK.fetchHotestFeeds(mLoginRefreshListener, HOT_DAYS, 0);
//        loadDataFromServer();
    }

    @Override
    protected Comparator<FeedItem> getFeedCompartator() {
        return null;
    }

    public boolean isRefreshing(){
        return isRefreshing;
    }
}
