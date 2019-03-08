package com.baijiahulian.live.ui.more;

import com.baijiahulian.live.ui.activity.LiveRoomRouterListener;
import com.baijiahulian.live.ui.utils.RxUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by Shubo on 2017/4/17.
 */

public class MoreMenuPresenter implements MoreMenuContract.Presenter {

    private MoreMenuContract.View view;
    private LiveRoomRouterListener routerListener;
    private Disposable subscriptionOfCloudRecord, subscriptionOfIsCloudRecordAllowed;
    private boolean recordStatus;

    public MoreMenuPresenter(MoreMenuContract.View view) {
        this.view = view;
    }

    @Override
    public void setRouter(LiveRoomRouterListener liveRoomRouterListener) {
        routerListener = liveRoomRouterListener;
    }

    @Override
    public void subscribe() {
        subscriptionOfCloudRecord = routerListener.getLiveRoom().getObservableOfCloudRecordStatus()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    recordStatus = aBoolean;
                    if (aBoolean)
                        view.showCloudRecordOn();
                    else
                        view.showCloudRecordOff();
                });
        recordStatus = routerListener.getLiveRoom().getCloudRecordStatus();
        if (recordStatus)
            view.showCloudRecordOn();
        else
            view.showCloudRecordOff();
    }

    @Override
    public void unSubscribe() {
        RxUtils.dispose(subscriptionOfCloudRecord);
        RxUtils.dispose(subscriptionOfIsCloudRecordAllowed);
    }

    @Override
    public void destroy() {
        routerListener = null;
        view = null;
    }

    @Override
    public void navigateToAnnouncement() {
        routerListener.navigateToAnnouncement();
    }

    @Override
    public void switchCloudRecord() {
        if (!recordStatus) {
            subscriptionOfIsCloudRecordAllowed = routerListener.getLiveRoom().requestIsCloudRecordAllowed()
                    .subscribe(lpCheckRecordStatusModel -> {
                        if (lpCheckRecordStatusModel.recordStatus == 1) {
                            routerListener.navigateToCloudRecord(true);
                            routerListener.getLiveRoom().requestCloudRecord(true);
                        } else {
                            if (view != null)
                                view.showCloudRecordNotAllowed(lpCheckRecordStatusModel.reason);
                        }
                    });
        } else {
            //ui
            routerListener.navigateToCloudRecord(false);
            //logic
            routerListener.getLiveRoom().requestCloudRecord(false);

        }
    }

    @Override
    public void navigateToHelp() {
        routerListener.navigateToHelp();
    }

    @Override
    public void navigateToSetting() {
        routerListener.navigateToSetting();
    }

    @Override
    public boolean isTeacher() {
        return routerListener.isCurrentUserTeacher();
    }
}
