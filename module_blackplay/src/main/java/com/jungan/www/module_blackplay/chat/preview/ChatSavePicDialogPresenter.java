package com.jungan.www.module_blackplay.chat.preview;


import com.jungan.www.module_blackplay.activity.PBRouterListener;

/**
 * Created by wangkangfei on 17/5/13.
 */

public class ChatSavePicDialogPresenter implements ChatSavePicDialogContract.Presenter {
    private PBRouterListener routerListener;
    private byte[] bmpArray;

    public ChatSavePicDialogPresenter(byte[] bmpArray) {
        this.bmpArray = bmpArray;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void setRouter(PBRouterListener listener) {
        this.routerListener = listener;
    }

    @Override
    public void create() {

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
