package com.jungan.www.model_liveplay.rightbotmenu;


import com.jungan.www.model_liveplay.base.BasePresenter;
import com.jungan.www.model_liveplay.base.BaseView;

/**
 * Created by Shubo on 2017/2/15.
 */

interface RightBottomMenuContract {
    interface View extends BaseView<Presenter> {
        void showVideoStatus(boolean isOn);

        void showAudioStatus(boolean isOn);

        void enableSpeakerMode();

        void disableSpeakerMode();

        void clearScreen();

        void unClearScreen();

        void showVolume(int level);

        void showZoomIn();

        void showZoomOut();

        void showZoom();

        void hideZoom();

        void showAudioRoomError();
    }

    interface Presenter extends BasePresenter {
        void changeZoom();

        void changeAudio();

        void changeVideo();

        void more(int anchorX, int anchorY);

        void getSysRotationSetting();

        void setSysRotationSetting();
    }
}
