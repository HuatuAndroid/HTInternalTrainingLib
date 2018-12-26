package com.jungan.www.model_liveplay.cloudrecord;


import com.jungan.www.model_liveplay.base.BasePresenter;
import com.jungan.www.model_liveplay.base.BaseView;

/**
 * Created by wangkangfei on 17/5/8.
 */

public interface CloudRecordContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {
        void cancelCloudRecord();
    }
}
