package com.jungan.www.model_liveplay.chat.preview;


import com.jungan.www.model_liveplay.base.BasePresenter;
import com.jungan.www.model_liveplay.base.BaseView;

/**
 * Created by wangkangfei on 17/5/13.
 */

public interface ChatPictureViewContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {
        void showSaveDialog(byte[] bmpArray);
    }
}
