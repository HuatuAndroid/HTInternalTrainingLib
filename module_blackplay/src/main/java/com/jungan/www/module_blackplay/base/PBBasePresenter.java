package com.jungan.www.module_blackplay.base;

import com.jungan.www.module_blackplay.activity.PBRouterListener;

/**
 * Created by wangkangfei on 17/8/17.
 */

public interface PBBasePresenter {
    void setRouter(PBRouterListener listener);

    void create();

    void destroy();

    void subscribe();

    void unSubscribe();
}
