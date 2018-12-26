package com.jungan.www.model_liveplay.users;

import com.baijiahulian.livecore.models.imodels.IUserModel;

import com.jungan.www.model_liveplay.base.BasePresenter;
import com.jungan.www.model_liveplay.base.BaseView;

/**
 * Created by Shubo on 2017/4/5.
 */

public interface OnlineUserContract {

    interface View extends BaseView<Presenter> {
        void notifyDataChanged();

        void notifyNoMoreData();

        void notifyUserCountChange(int count);
    }

    interface Presenter extends BasePresenter {
        int getCount();

        IUserModel getUser(int position);

        void loadMore();

        boolean isLoading();
    }
}
