package com.jungan.www.module_blackplay.chat;

import com.jungan.www.module_blackplay.base.PBBasePresenter;
import com.jungan.www.module_blackplay.base.PBBaseView;
import com.baijiahulian.livecore.models.imodels.IMessageModel;

/**
 * Created by wangkangfei on 17/8/17.
 */

public interface PBChatContract {
    public interface View extends PBBaseView<Presenter> {
    }

    public interface Presenter extends PBBasePresenter {
    }

}
