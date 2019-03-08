package com.baijiahulian.live.ui.setting;

import com.baijiahulian.live.ui.base.BasePresenter;
import com.baijiahulian.live.ui.base.BaseView;
import com.baijiayun.livecore.context.LPConstants;

/**
 * Created by Shubo on 2017/3/2.
 */

interface SettingContract {

    interface View extends BaseView<Presenter> {
        void showMicOpen();

        void showMicClosed();

        void showCameraOpen();

        void showCameraClosed();

        void showBeautyFilterEnable();

        void showBeautyFilterDisable();

        void showPPTFullScreen();

        void showPPTOverspread();

        void showDefinitionLow();

        void showDefinitionHigh();

        void showUpLinkTCP();

        void showUpLinkUDP();

        void showDownLinkTCP();

        void showDownLinkUDP();

        void showVisitorFail();

        void showStudentFail();

        void showSmallGroupFail();

        void showCameraFront();

        void showCameraBack();

        void showPPTViewTypeAnim();

        void showPPTViewTypeStatic();

        void showCameraSwitchStatus(boolean whetherShow);

        void showForbidden();

        void showNotForbidden();

        void showAudioRoomError();

        void showForbidRaiseHandOn();

        void showForbidRaiseHandOff();

        void showForbidAllAudioOn();

        void showForbidAllAudioOff();

        void showSwitchLinkTypeError();

        void hidePPTShownType();

        void showSwitchPPTFail();
    }

    interface Presenter extends BasePresenter {
        void changeMic();

        void changeCamera();

        void changeBeautyFilter();

        void setPPTViewAnim();

        void setPPTViewStatic();

        void setPPTFullScreen();

        void setPPTOverspread();

        void setDefinitionLow();

        void setDefinitionHigh();

        void setUpLinkTCP();

        void setUpLinkUDP();

        void setDownLinkTCP();

        void setDownLinkUDP();

        void switchCamera();

        void switchForbidStatus();

        boolean isTeacherOrAssistant();

        void switchForbidRaiseHand();

        void switchForbidAllAudio();

        LPConstants.LPRoomType getRoomType();

        int getCDNCount();

        void setUpCDNLink(int order);

        void setDownCDNLink(int order);

        boolean isUseWebRTC();
    }
}
