package com.jungan.www.model_liveplay.pptleftmenu;


import com.jungan.www.model_liveplay.base.BasePresenter;
import com.jungan.www.model_liveplay.base.BaseView;

/**
 * Created by wangkangfei on 17/5/4.
 */

public interface PPTLeftContract {
    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {
        void clearPPTAllShapes();
    }
}
