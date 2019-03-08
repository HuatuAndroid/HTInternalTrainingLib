package com.baijiahulian.live.ui.ppt;

import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.baijiahulian.live.ui.base.BasePresenter;
import com.baijiahulian.live.ui.base.BaseView;

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

        RelativeLayout getBackgroundContainer();

        void notifyPPTResumeInSpeakers();

        boolean isPPTInSpeakerList();

        void showOptionDialog();

        void showPPTLoadError(int errorCode, String description);
    }
}
