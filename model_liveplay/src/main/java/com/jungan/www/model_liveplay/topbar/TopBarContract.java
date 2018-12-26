package com.jungan.www.model_liveplay.topbar;


import com.jungan.www.model_liveplay.base.BasePresenter;
import com.jungan.www.model_liveplay.base.BaseView;

/**
 * Created by Shubo on 2017/2/13.
 */

interface TopBarContract {

    interface View extends BaseView<Presenter> {

        void showHideShare(boolean show);
    }

    interface Presenter extends BasePresenter {

        void navigateToShare();
    }

}
