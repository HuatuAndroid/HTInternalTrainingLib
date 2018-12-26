package com.jungan.www.model_liveplay.quiz;

import com.baijiahulian.livecore.models.LPJsonModel;
import com.baijiahulian.livecore.models.imodels.IUserModel;

import com.jungan.www.model_liveplay.base.BasePresenter;
import com.jungan.www.model_liveplay.base.BaseView;

/**
 * Created by wangkangfei on 17/5/31.
 */

public interface QuizDialogContract {

    interface View extends BaseView<Presenter> {
        void onStartArrived(LPJsonModel jsonModel);

        void onEndArrived(LPJsonModel jsonModel);

        void onSolutionArrived(LPJsonModel jsonModel);

        void onQuizResArrived(LPJsonModel jsonModel);

        void onGetCurrentUser(IUserModel userModel);

        void dismissDlg();
    }

    interface Presenter extends BasePresenter {
        void submitAnswer(String submitContent);

        void sendCommonRequest(String request);

        void getCurrentUser();

        String getRoomToken();

        void dismissDlg();
    }
}
