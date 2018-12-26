package com.jungan.www.model_liveplay.rollcall;


import com.jungan.www.model_liveplay.base.BasePresenter;
import com.jungan.www.model_liveplay.base.BaseView;

/**
 * Created by wangkangfei on 17/5/31.
 */

public interface RollCallDialogContract {

    interface View extends BaseView<Presenter> {
        void timerDown(int time);

        void timeOutSoDismiss();
    }

    interface Presenter extends BasePresenter {
        void rollCallConfirm();

        void timeOut();
    }
}
