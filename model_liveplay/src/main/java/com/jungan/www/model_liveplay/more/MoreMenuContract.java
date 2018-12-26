package com.jungan.www.model_liveplay.more;


import com.jungan.www.model_liveplay.base.BasePresenter;
import com.jungan.www.model_liveplay.base.BaseView;

/**
 * Created by Shubo on 2017/4/17.
 */

interface MoreMenuContract {

    interface View extends BaseView<Presenter> {
        void showCloudRecordOn();

        void showCloudRecordOff();

        void showCloudRecordNotAllowed(String reason);
    }

    interface Presenter extends BasePresenter {
        void navigateToAnnouncement();

        void switchCloudRecord();

        void navigateToHelp();

        void navigateToSetting();

        boolean isTeacher();
    }
}
