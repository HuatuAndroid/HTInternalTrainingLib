package com.jungan.www.model_liveplay.chat;


import com.baijiahulian.livecore.models.imodels.IUserModel;
import com.jungan.www.model_liveplay.base.BasePresenter;
import com.jungan.www.model_liveplay.base.BaseView;

/**
 * Created by Shubo on 2017/3/4.
 */

interface MessageSendContract {

    interface View extends BaseView<Presenter> {
        void showMessageSuccess();

        void showEmojiPanel();

        void showPrivateChatUserPanel();

        void showPrivateChatChange();
//        void showPicture(int position);

        void onPictureSend();
    }

    interface Presenter extends BasePresenter {

        void sendMessageToUser(String message);

        void sendEmojiToUser(String emoji);

        void sendMessage(String message);

        void sendEmoji(String emoji);

        void sendPicture(String path);

        void chooseEmoji();

        void choosePrivateChatUser();

        boolean canSendPicture();

        boolean isPrivateChat();

        boolean isLiveCanWhisper();

        IUserModel getPrivateChatUser();
    }
}
