package com.jungan.www.module_blackplay.chat.preview;


import com.jungan.www.module_blackplay.base.PBBasePresenter;
import com.jungan.www.module_blackplay.base.PBBaseView;

/**
 * Created by wangkangfei on 17/5/13.
 */

public interface ChatPictureViewContract {

    interface View extends PBBaseView<Presenter> {

    }

    interface Presenter extends PBBasePresenter {
        void showSaveDialog(byte[] bmpArray);
    }
}
