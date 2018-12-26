package com.jungan.www.module_blackplay.chat.preview;


import com.jungan.www.module_blackplay.activity.PBRouterListener;

/**
 * Created by wangkangfei on 17/5/13.
 */

public class ChatPictureViewPresenter implements ChatPictureViewContract.Presenter {
    private PBRouterListener routerListener;

    @Override
    public void setRouter(PBRouterListener liveRoomRouterListener) {
        this.routerListener = liveRoomRouterListener;
    }

    @Override
    public void create() {

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void destroy() {
        routerListener = null;
    }

    @Override
    public void showSaveDialog(byte[] bmpArray) {
        routerListener.showSavePicDialog(bmpArray);
    }
}
