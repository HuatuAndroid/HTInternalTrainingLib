package com.jungan.www.module_blackplay.chat;

import com.jungan.www.module_blackplay.activity.PBRouterListener;

/**
 * Created by wangkangfei on 17/8/17.
 */

public class PBChatPresenter implements PBChatContract.Presenter {
    private PBChatContract.View view;

    public PBRouterListener routerListener;

    public PBChatPresenter(PBChatContract.View view) {
        this.view = view;
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
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }
}
