package com.jungan.www.model_liveplay.chat;

import com.baijiahulian.livecore.models.imodels.IMessageModel;

import com.baijiahulian.livecore.models.imodels.IUserModel;
import com.jungan.www.model_liveplay.base.BasePresenter;
import com.jungan.www.model_liveplay.base.BaseView;

/**
 * Created by Shubo on 2017/2/15.
 */

interface ChatContract {

    interface View extends BaseView<Presenter> {
        void notifyDataChanged();

        void clearScreen();

        void unClearScreen();

        void notifyItemChange(int position);

        void notifyItemInserted(int position);

        void showHavingPrivateChat(IUserModel privateChatUser);

        void showNoPrivateChat();
    }

    interface Presenter extends BasePresenter {
        int getCount();

        IMessageModel getMessage(int position);

        void showBigPic(int position);

        void reUploadImage(int position);

        void endPrivateChat();

        IUserModel getCurrentUser();

        boolean isPrivateChatMode();

        void showPrivateChat(IUserModel userModel);

        boolean isLiveCanWhisper();

    }
}
