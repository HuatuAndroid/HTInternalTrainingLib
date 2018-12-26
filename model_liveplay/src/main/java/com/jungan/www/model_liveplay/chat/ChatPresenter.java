package com.jungan.www.model_liveplay.chat;

import com.baijiahulian.common.networkv2.BJProgressCallback;
import com.baijiahulian.common.networkv2.BJResponse;
import com.baijiahulian.common.networkv2.HttpException;
import com.baijiahulian.livecore.context.LPConstants;
import com.baijiahulian.livecore.models.LPShortResult;
import com.baijiahulian.livecore.models.LPUploadDocModel;
import com.baijiahulian.livecore.models.imodels.IMessageModel;
import com.baijiahulian.livecore.models.imodels.IUserModel;
import com.baijiahulian.livecore.utils.LPBackPressureBufferedSubscriber;
import com.baijiahulian.livecore.utils.LPChatMessageParser;
import com.baijiahulian.livecore.utils.LPErrorPrintSubscriber;
import com.baijiahulian.livecore.utils.LPJsonUtils;
import com.baijiahulian.livecore.utils.LPLogger;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import com.jungan.www.model_liveplay.activity.LiveRoomRouterListener;
import com.jungan.www.model_liveplay.utils.RxUtils;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

import static com.jungan.www.model_liveplay.utils.Precondition.checkNotNull;


/**
 * Created by Shubo on 2017/2/23.
 */

public class ChatPresenter implements ChatContract.Presenter {

    private LiveRoomRouterListener routerListener;
    private ChatContract.View view;
    private Subscription subscriptionOfDataChange, subscriptionOfMessageReceived;
    private LinkedBlockingQueue<UploadingImageModel> imageMessageUploadingQueue;
    private ConcurrentHashMap<String, List<IMessageModel>> privateChatMessagePool;

    public ChatPresenter(ChatContract.View view) {
        this.view = view;
        privateChatMessagePool = new ConcurrentHashMap<>();
        imageMessageUploadingQueue = new LinkedBlockingQueue<>();
    }

    @Override
    public void setRouter(LiveRoomRouterListener liveRoomRouterListener) {
        this.routerListener = liveRoomRouterListener;
    }

    @Override
    public void subscribe() {
        checkNotNull(routerListener);
        view.notifyDataChanged();
        subscriptionOfDataChange = routerListener.getLiveRoom().getChatVM().getObservableOfNotifyDataChange()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new LPErrorPrintSubscriber<List<IMessageModel>>() {
                    @Override
                    public void call(List<IMessageModel> iMessageModels) {
                        view.notifyDataChanged();
                    }
                });
        subscriptionOfMessageReceived = routerListener.getLiveRoom().getChatVM().getObservableOfReceiveMessage()
                .onBackpressureBuffer()
                .doOnNext(new Action1<IMessageModel>() {
                    @Override
                    public void call(IMessageModel iMessageModel) {
                        if (iMessageModel.isPrivateChat() && iMessageModel.getToUser() != null) {
                            String userNumber = iMessageModel.getFrom().getNumber().equals(routerListener.getLiveRoom().getCurrentUser().getNumber()) ?
                                    iMessageModel.getToUser().getNumber() : iMessageModel.getFrom().getNumber();
                            List<IMessageModel> messageList = privateChatMessagePool.get(userNumber);
                            if (messageList == null) {
                                messageList = new ArrayList<>();
                                privateChatMessagePool.put(userNumber, messageList);
                            }
                            messageList.add(iMessageModel);
                        }
                    }
                })
                .filter(new Func1<IMessageModel, Boolean>() {
                    @Override
                    public Boolean call(IMessageModel iMessageModel) {
                        if (routerListener.isPrivateChat()) return true;
                        if ("-1".equals(iMessageModel.getTo())) return false;
                        if (iMessageModel.getToUser() == null) return false;
                        IUserModel currentPrivateChatUser = routerListener.getPrivateChatUser();
                        if(currentPrivateChatUser == null) return true;
                        return iMessageModel.getToUser().getNumber().equals(currentPrivateChatUser.getNumber())
                                || iMessageModel.getFrom().getNumber().equals(currentPrivateChatUser.getName());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new LPBackPressureBufferedSubscriber<IMessageModel>() {
                    @Override
                    public void call(IMessageModel iMessageModel) {
                        if (iMessageModel.getMessageType() == LPConstants.MessageType.Image
                                && iMessageModel.getFrom().getUserId().equals(routerListener.getLiveRoom().getCurrentUser().getUserId())) {
                            view.notifyItemChange(getCount() - imageMessageUploadingQueue.size() - 1);
                        }
                        view.notifyItemInserted(getCount() - 1);
                    }
                });
    }

    @Override
    public void unSubscribe() {
        RxUtils.unSubscribe(subscriptionOfDataChange);
        RxUtils.unSubscribe(subscriptionOfMessageReceived);
    }

    @Override
    public int getCount() {
        checkNotNull(routerListener);
        if (routerListener.isPrivateChat()) {
            List list = privateChatMessagePool.get(routerListener.getPrivateChatUser().getNumber());
            return list == null ? 0 : list.size() + imageMessageUploadingQueue.size();
        } else {
            return routerListener.getLiveRoom().getChatVM().getMessageCount() + imageMessageUploadingQueue.size();
        }
    }

    @Override
    public IMessageModel getMessage(int position) {
        checkNotNull(routerListener);
        if (routerListener.isPrivateChat()) {
            List<IMessageModel> list = privateChatMessagePool.get(routerListener.getPrivateChatUser().getNumber());
            int messageCount = list == null ? 0 : list.size();
            if (position < messageCount) {
                return list.get(position);
            } else {
                return (IMessageModel) imageMessageUploadingQueue.toArray()[position - messageCount];
            }
        } else {
            int messageCount = routerListener.getLiveRoom().getChatVM().getMessageCount();
            if (position < messageCount) {
                return routerListener.getLiveRoom().getChatVM().getMessage(position);
            } else {
                return (IMessageModel) imageMessageUploadingQueue.toArray()[position - messageCount];
            }
        }
    }

    @Override
    public void showBigPic(int position) {
        checkNotNull(routerListener);
        routerListener.showBigChatPic(getMessage(position).getUrl());
    }

    @Override
    public void reUploadImage(int position) {
        continueUploadQueue();
    }

    @Override
    public void endPrivateChat() {
        routerListener.onPrivateChatUserChange(null);
        view.notifyDataChanged();
    }

    @Override
    public IUserModel getCurrentUser() {
        return routerListener.getLiveRoom().getCurrentUser();
    }

    @Override
    public boolean isPrivateChatMode() {
        return routerListener.isPrivateChat();
    }

    @Override
    public void showPrivateChat(IUserModel userModel) {
        routerListener.onPrivateChatUserChange(userModel);
        routerListener.navigateToMessageInput();
    }

    @Override
    public boolean isLiveCanWhisper() {
        return routerListener.getLiveRoom().getChatVM().isLiveCanWhisper();
    }

    public void onPrivateChatUserChange() {
        checkNotNull(routerListener);
        checkNotNull(view);
        if (routerListener.isPrivateChat()) {
            view.showHavingPrivateChat(routerListener.getPrivateChatUser());
        } else {
            view.showNoPrivateChat();
        }
        view.notifyDataChanged();
    }

    @Override
    public void destroy() {
        view = null;
        routerListener = null;
        imageMessageUploadingQueue.clear();
        imageMessageUploadingQueue = null;
    }

    // add Uploading Image to queue
    public void sendImageMessage(String path) {
        UploadingImageModel model = new UploadingImageModel(path, routerListener.getLiveRoom().getCurrentUser(), routerListener.getPrivateChatUser());
        imageMessageUploadingQueue.offer(model);
        continueUploadQueue();
    }


    private void continueUploadQueue() {
        final UploadingImageModel model = imageMessageUploadingQueue.peek();
        if (model == null) return;
        view.notifyItemInserted(routerListener.getLiveRoom().getChatVM().getMessageCount() + imageMessageUploadingQueue.size() - 1);
        routerListener.getLiveRoom().getDocListVM().uploadImageWithProgress(model.getUrl(), this, new BJProgressCallback() {
            @Override
            public void onProgress(long l, long l1) {
                LPLogger.d(l + "/" + l1);
            }

            @Override
            public void onFailure(HttpException e) {
                model.setStatus(UploadingImageModel.STATUS_UPLOAD_FAILED);
                view.notifyDataChanged();
            }

            @Override
            public void onResponse(BJResponse bjResponse) {
                LPShortResult shortResult;
                try {
                    shortResult = LPJsonUtils.parseString(bjResponse.getResponse().body().string(), LPShortResult.class);
                    LPUploadDocModel uploadModel = LPJsonUtils.parseJsonObject((JsonObject) shortResult.data, LPUploadDocModel.class);
                    String imageContent = LPChatMessageParser.toImageMessage(uploadModel.url);
                    routerListener.getLiveRoom().getChatVM().sendImageMessageToUser(model.getToUser(), imageContent, uploadModel.width, uploadModel.height);
                    imageMessageUploadingQueue.poll();
                    continueUploadQueue();
                } catch (IOException e) {
                    model.setStatus(UploadingImageModel.STATUS_UPLOAD_FAILED);
                    view.notifyDataChanged();
                    e.printStackTrace();
                }
            }
        });
    }
}
