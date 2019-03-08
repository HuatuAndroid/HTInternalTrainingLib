package com.baijiahulian.live.ui.cloudrecord;

import com.baijiahulian.live.ui.activity.LiveRoomRouterListener;
import com.baijiahulian.live.ui.utils.RxUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by wangkangfei on 17/5/8.
 */

public class CloudRecordPresenter implements CloudRecordContract.Presenter {
    private LiveRoomRouterListener liveRoomRouterListener;
    private Disposable subscriptionOfCloudRecord;

    @Override
    public void setRouter(LiveRoomRouterListener liveRoomRouterListener) {
        this.liveRoomRouterListener = liveRoomRouterListener;
    }

    @Override
    public void subscribe() {
        subscriptionOfCloudRecord = liveRoomRouterListener.getLiveRoom().getObservableOfCloudRecordStatus()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        if (liveRoomRouterListener.isTeacherOrAssistant()) {
                            liveRoomRouterListener.navigateToCloudRecord(true);
                        } else {
                            liveRoomRouterListener.navigateToCloudRecord(false);
                        }
                    } else {
                        liveRoomRouterListener.navigateToCloudRecord(false);
                    }
                });
    }

    @Override
    public void unSubscribe() {
        RxUtils.dispose(subscriptionOfCloudRecord);
    }

    @Override
    public void destroy() {
        liveRoomRouterListener = null;
    }

    @Override
    public void cancelCloudRecord() {
        liveRoomRouterListener.getLiveRoom().requestCloudRecord(false);
    }
}
