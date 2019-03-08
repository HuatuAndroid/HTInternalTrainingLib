package com.baijiahulian.live.ui.leftmenu;

import com.baijiahulian.live.ui.activity.LiveRoomRouterListener;
import com.baijiayun.livecore.utils.LPRxUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * Created by Shubo on 2017/2/15.
 */

public class LeftMenuPresenter implements LeftMenuContract.Presenter {

    private LiveRoomRouterListener routerListener;
    private LeftMenuContract.View view;
    private boolean isScreenCleared = false;
    private boolean isSelfForbidden = false;
    private Disposable disposableOfIsSelfChatForbid;

    public LeftMenuPresenter(LeftMenuContract.View view) {
        this.view = view;
    }

    @Override
    public void clearScreen() {
//        isScreenCleared = !isScreenCleared;
//        view.notifyClearScreenChanged(isScreenCleared);
//        if (isScreenCleared) routerListener.clearScreen();
//        else routerListener.unClearScreen();
    }

    @Override
    public void showMessageInput() {
        routerListener.navigateToMessageInput();
    }

    @Override
    public boolean isScreenCleared() {
        return isScreenCleared;
    }

    @Override
    public boolean isAllForbidden() {
        return !routerListener.isTeacherOrAssistant() && !routerListener.isGroupTeacherOrAssistant() && routerListener.getLiveRoom().getForbidStatus();
    }

    @Override
    public boolean isForbiddenByTeacher() {
        if (routerListener.isTeacherOrAssistant() || routerListener.isGroupTeacherOrAssistant()) {
            return false;
        }
        return isSelfForbidden;
    }

    @Override
    public void showHuiyinDebugPanel() {
        routerListener.showHuiyinDebugPanel();
    }

    @Override
    public void showStreamDebugPanel() {
        routerListener.showStreamDebugPanel();
    }

    @Override
    public void showCopyLogDebugPanel() {
        routerListener.showCopyLogDebugPanel();
    }

    @Override
    public void setRouter(LiveRoomRouterListener liveRoomRouterListener) {
        routerListener = liveRoomRouterListener;
    }

    @Override
    public void subscribe() {
        disposableOfIsSelfChatForbid = routerListener.getLiveRoom().getObservableOfIsSelfChatForbid()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) {
                        isSelfForbidden = aBoolean;
                    }
                });

    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void destroy() {
        LPRxUtils.dispose(disposableOfIsSelfChatForbid);
        routerListener = null;
        view = null;
    }
}
