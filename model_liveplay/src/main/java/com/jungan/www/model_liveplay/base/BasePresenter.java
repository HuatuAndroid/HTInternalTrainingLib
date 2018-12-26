package com.jungan.www.model_liveplay.base;


import com.jungan.www.model_liveplay.activity.LiveRoomRouterListener;

/**
 * Created by Shubo on 2017/2/13.
 */

public interface BasePresenter {

    void setRouter(LiveRoomRouterListener liveRoomRouterListener);

    void subscribe();

    void unSubscribe();

    void destroy();
}
