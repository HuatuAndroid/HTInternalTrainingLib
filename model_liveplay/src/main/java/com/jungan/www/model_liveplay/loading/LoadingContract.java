package com.jungan.www.model_liveplay.loading;

import com.baijiahulian.livecore.context.LPError;
import com.baijiahulian.livecore.context.LiveRoom;
import com.baijiahulian.livecore.launch.LPLaunchListener;
import com.baijiahulian.livecore.models.imodels.IUserModel;

import com.jungan.www.model_liveplay.base.BasePresenter;
import com.jungan.www.model_liveplay.base.BaseView;

/**
 * Created by Shubo on 2017/2/14.
 */

interface LoadingContract {
    interface View extends BaseView<Presenter> {
        void showLoadingSteps(int currentStep, int totalSteps);

        void showLaunchError(LPError lpError);

        void setTechSupportVisibility(boolean shouldShow);
    }

    interface Presenter extends BasePresenter {
        LPLaunchListener getLaunchListener();

        String getCode();

        String getName();

        String getAvatar();

        String getSign();

        IUserModel getUser();

        long getRoomId();

        void setLiveRoom(LiveRoom liveRoom);

        boolean isJoinCode();
    }
}
