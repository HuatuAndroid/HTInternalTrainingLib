package com.jungan.www.model_liveplay.ppt;

import android.widget.FrameLayout;

import com.jungan.www.model_liveplay.base.BasePresenter;
import com.jungan.www.model_liveplay.base.BaseView;


/**
 * Created by Shubo on 2017/2/18.
 */

interface PPTContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {
        void clearScreen();

        void showQuickSwitchPPTView(int currentIndex, int maxIndex);

        void updateQuickSwitchPPTView(int maxIndex);

        FrameLayout getBackgroundContainer();

        void notifyPPTResumeInSpeakers();
    }
}
