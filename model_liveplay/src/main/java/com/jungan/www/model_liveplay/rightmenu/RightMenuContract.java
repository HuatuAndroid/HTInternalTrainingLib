package com.jungan.www.model_liveplay.rightmenu;


import com.jungan.www.model_liveplay.base.BasePresenter;
import com.jungan.www.model_liveplay.base.BaseView;

/**
 * Created by Shubo on 2017/2/15.
 */

public interface RightMenuContract {

    int STUDENT_SPEAK_APPLY_NONE = 0;
    int STUDENT_SPEAK_APPLY_APPLYING = 1;
    int STUDENT_SPEAK_APPLY_SPEAKING = 2;

    interface View extends BaseView<Presenter> {

        void showSpeakClosedByTeacher();

        void showDrawingStatus(boolean isEnable);

        void showSpeakApplyCountDown(int countDownTime, int total);

        void showSpeakApplyAgreed();

        void showSpeakApplyDisagreed();

        void showSpeakApplyCanceled();

        void showTeacherRightMenu();

        void showStudentRightMenu();

        void showForbiddenHand();

        void showNotForbiddenHand();

        void hidePPTDrawBtn();

        void showPPTDrawBtn();

        void showHandUpError();

        void showHandUpForbid();

        void showCantDraw();

        void showCantDrawCauseClassNotStart();

        void showWaitingTeacherAgree();

        void showAutoSpeak();

        void showForceSpeak();

        void hideUserList();

        void showHandUpTimeout();

    }

    interface Presenter extends BasePresenter {
        void visitOnlineUser();

        void changeDrawing();

        void managePPT();

        void speakApply();

        void changePPTDrawBtnStatus(boolean shouldShow);

        void onSpeakInvite(int confirm);
    }
}
