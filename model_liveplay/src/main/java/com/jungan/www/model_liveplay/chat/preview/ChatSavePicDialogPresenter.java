package com.jungan.www.model_liveplay.chat.preview;


import com.jungan.www.model_liveplay.activity.LiveRoomRouterListener;

/**
 * Created by wangkangfei on 17/5/13.
 */

public class ChatSavePicDialogPresenter implements ChatSavePicDialogContract.Presenter {
    private LiveRoomRouterListener routerListener;
    private byte[] bmpArray;

    public ChatSavePicDialogPresenter(byte[] bmpArray) {
        this.bmpArray = bmpArray;
    }

    @Override
    public void setRouter(LiveRoomRouterListener liveRoomRouterListener) {
        this.routerListener = liveRoomRouterListener;
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
    public void realSavePic() {
        routerListener.realSaveBmpToFile(bmpArray);
    }
}
