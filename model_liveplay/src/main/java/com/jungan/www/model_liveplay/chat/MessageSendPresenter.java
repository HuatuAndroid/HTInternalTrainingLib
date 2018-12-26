package com.jungan.www.model_liveplay.chat;

import android.text.TextUtils;

import com.baijiahulian.livecore.context.LPConstants;

import com.baijiahulian.livecore.models.imodels.IUserModel;
import com.jungan.www.model_liveplay.activity.LiveRoomRouterListener;

import static com.jungan.www.model_liveplay.utils.Precondition.checkNotNull;


/**
 * Created by Shubo on 2017/3/4.
 */

public class MessageSendPresenter implements MessageSendContract.Presenter {

    private MessageSendContract.View view;
    private LiveRoomRouterListener routerListener;

    public MessageSendPresenter(MessageSendContract.View view) {
        this.view = view;
    }

    public void setView(MessageSendContract.View view) {
        this.view = view;
    }

    @Override
    public void sendMessage(String message) {
        checkNotNull(routerListener);
        if (!TextUtils.isEmpty(message)) {
            if (message.startsWith("/dev")) {
                routerListener.showDebugBtn();
                return;
            }
        }
        routerListener.getLiveRoom().getChatVM().sendMessage(message);
        view.showMessageSuccess();
    }

    @Override
    public void sendEmoji(String emoji) {
        checkNotNull(routerListener);
        routerListener.getLiveRoom().getChatVM().sendEmojiMessage(emoji);
        view.showMessageSuccess();
    }

    @Override
    public void sendPicture(String path) {
        routerListener.sendImageMessage(path);
        view.onPictureSend();
    }

    @Override
    public void sendMessageToUser(String message) {
        checkNotNull(routerListener);
        if (!TextUtils.isEmpty(message)) {
            if (message.startsWith("/dev")) {
                routerListener.showDebugBtn();
                return;
            }
        }
        IUserModel toUser = routerListener.getPrivateChatUser();
        routerListener.getLiveRoom().getChatVM().sendMessageToUser(toUser, message);
        view.showMessageSuccess();
    }



    @Override
    public void sendEmojiToUser(String emoji) {
        checkNotNull(routerListener);
        IUserModel toUser = routerListener.getPrivateChatUser();
        routerListener.getLiveRoom().getChatVM().sendEmojiMessageToUser(toUser, emoji);
        view.showMessageSuccess();
    }


    @Override
    public void chooseEmoji() {
        view.showEmojiPanel();
    }


    public void choosePrivateChatUser() {
        view.showPrivateChatUserPanel();
    }

    @Override
    public IUserModel getPrivateChatUser() {
        return routerListener.getPrivateChatUser();
    }

    @Override
    public boolean isPrivateChat() {
        return routerListener.isPrivateChat();
    }

    @Override
    public boolean isLiveCanWhisper() {
        return routerListener.getLiveRoom().getChatVM().isLiveCanWhisper();
    }

    @Override
    public boolean canSendPicture() {
        // 大班课只有老师和助教能发图片，一对一、小班课都能发
        return routerListener.getLiveRoom().getRoomType() != LPConstants.LPRoomType.Multi
                || routerListener.isTeacherOrAssistant();
    }

    @Override
    public void setRouter(LiveRoomRouterListener liveRoomRouterListener) {
        this.routerListener = liveRoomRouterListener;
    }

    @Override
    public void subscribe() {
    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void destroy() {
        routerListener = null;
        view = null;
    }


    public void onPrivateChatUserChange() {
        if (view != null) {
            view.showPrivateChatChange();
        }
    }
}
