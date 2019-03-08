package com.baijiahulian.live.ui.speakerspanel;

import android.text.TextUtils;
import android.view.View;

import com.baijiahulian.live.ui.activity.LiveRoomRouterListener;
import com.baijiahulian.live.ui.ppt.MyPPTView;
import com.baijiahulian.live.ui.utils.RxUtils;
import com.baijiayun.livecore.context.LPConstants;
import com.baijiayun.livecore.context.LPError;
import com.baijiayun.livecore.context.LiveRoom;
import com.baijiayun.livecore.models.LPMediaModel;
import com.baijiayun.livecore.models.LPUserModel;
import com.baijiayun.livecore.models.imodels.IMediaModel;
import com.baijiayun.livecore.models.imodels.IUserModel;
import com.baijiayun.livecore.models.launch.LPEnterRoomNative;
import com.baijiayun.livecore.utils.LPKVOSubjectWithLastValue;
import com.baijiayun.livecore.wrapper.LPPlayer;
import com.baijiayun.livecore.wrapper.LPRecorder;
import com.baijiayun.livecore.wrapper.listener.LPPlayerListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static com.baijiahulian.live.ui.speakerspanel.SpeakersContract.PPT_TAG;
import static com.baijiahulian.live.ui.speakerspanel.SpeakersContract.RECORD_TAG;
import static com.baijiahulian.live.ui.speakerspanel.SpeakersContract.VIEW_TYPE_APPLY;
import static com.baijiahulian.live.ui.speakerspanel.SpeakersContract.VIEW_TYPE_PPT;
import static com.baijiahulian.live.ui.speakerspanel.SpeakersContract.VIEW_TYPE_PRESENTER;
import static com.baijiahulian.live.ui.speakerspanel.SpeakersContract.VIEW_TYPE_RECORD;
import static com.baijiahulian.live.ui.speakerspanel.SpeakersContract.VIEW_TYPE_SPEAKER;
import static com.baijiahulian.live.ui.speakerspanel.SpeakersContract.VIEW_TYPE_VIDEO_PLAY;
import static com.baijiahulian.live.ui.utils.Precondition.checkNotNull;

/**
 * 发言者列表遵从{[PPT]---[主讲人视频|头像]---[自己视频]---[其他人视频]---[其他发言用户音频]---[请求发言用户]}的顺序
 * 如果全屏或没有对应的项目则不在此列表中
 * 如果老师不是主讲人并切没有被全屏则在其他人视频里
 * currentFullScreenTag 为当前全屏的tag VideoView为UserId
 * Created by Shubo on 2017/6/5.
 */

public class SpeakerPresenter implements SpeakersContract.Presenter {

    private final boolean disableSpeakQueuePlaceholder;
    private LiveRoomRouterListener routerListener;
    private SpeakersContract.View view;
    private LPKVOSubjectWithLastValue<String> fullScreenKVO;
    private static final int MAX_VIDEO_COUNT = 6;
    private boolean autoPlayPresenterVideo = true, isInit = false;

    // 显示视频或发言用户的分段Map, key为SpeakerType， value为List，保存每个SpeakType下相应的tag或者userId;
    private LinkedHashMap<SpeakersType, ArrayList<String>> displayMap;
    private IUserModel tempUserIn;
    private HashMap<String, Integer> awardRecord = new HashMap<>();

    private Disposable subscriptionOfMediaPublish, subscriptionSpeakApply, subscriptionSpeakResponse,
            subscriptionOfActiveUser, subscriptionOfFullScreen, subscriptionOfUserOut,
            subscriptionOfPresenterChange, subscriptionOfShareDesktopAndPlayMedia,
            subscriptionOfUserIn, subscriptionOfAutoFullScreenTeacher, subscriptionOfAward,
            subscriptionOfClassEnd, subscriptionOfMediaPublishExt;

    public SpeakerPresenter(SpeakersContract.View view, boolean disableSpeakQueuePlaceholder) {
        this.view = view;
        this.disableSpeakQueuePlaceholder = disableSpeakQueuePlaceholder;
        fullScreenKVO = new LPKVOSubjectWithLastValue<>(PPT_TAG);
        initDisplayMap();
    }

    @Override
    public void setRouter(LiveRoomRouterListener liveRoomRouterListener) {
        routerListener = liveRoomRouterListener;
    }

    private int indexOfUserId(String userId) {
        if (TextUtils.isEmpty(userId))
            return -1;
        ArrayList<String> totalList = getList();
        return totalList.indexOf(userId);
    }

    private ArrayList<String> getList() {
        Set<SpeakersType> sets = displayMap.keySet();
        ArrayList<String> totalList = new ArrayList<>();
        for (SpeakersType set : sets) {
            if (!displayMap.get(set).isEmpty())
                totalList.addAll(displayMap.get(set));
        }
        return totalList;
    }

    private SpeakersType getSpeakersType(String userId) {
        Set<SpeakersType> sets = displayMap.keySet();
        for (SpeakersType set : sets) {
            if (!displayMap.get(set).isEmpty()) {
                if (displayMap.get(set).contains(userId))
                    return set;
            }
        }
        return null;
    }

    @Override
    public void setPPTToFullScreen() {
        if (!PPT_TAG.equals(fullScreenKVO.getParameter())) {
            // if full screen shows someone's video, we will switch it with PPT,
            // we need all video and speaking in the list;
            View ppt = view.removeViewAt(indexOfUserId(PPT_TAG));
            View fullScreenView = routerListener.removeFullScreenView();
            String fullId = fullScreenKVO.getParameter();
            displayMap.get(SpeakersType.PPT).remove(PPT_TAG);
            if (!displayMap.get(SpeakersType.Presenter).isEmpty()) {
                // if last presenter is in the list (full screen not shows presenter's video)
                if (fullId.equals(RECORD_TAG)) {// full screen shows record
                    displayMap.get(SpeakersType.Record).clear();
                    displayMap.get(SpeakersType.Record).add(RECORD_TAG);
                } else {// full screen shows other's video
                    displayMap.get(SpeakersType.VideoPlay).add(fullId);
                }
                view.notifyViewAdded(fullScreenView, indexOfUserId(fullId));
            } else {
                // if full screen shows last presenter's video, then we switch it to presenter position;
                if (!routerListener.isTeacherOrAssistant()) {
                    displayMap.get(SpeakersType.Presenter).add(fullId);
                } else {
                    if (RECORD_TAG.equals(fullId)) {
                        displayMap.get(SpeakersType.Record).add(RECORD_TAG);
                    } else if (routerListener.getLiveRoom().getPresenterUser() != null && routerListener.getLiveRoom().getPresenterUser().getUserId().equals(fullId)) {
                        displayMap.get(SpeakersType.Presenter).add(fullId);
                    } else {
                        displayMap.get(SpeakersType.VideoPlay).add(fullId);
                        if (fullScreenView instanceof VideoView && getSpeakModel(fullId) != null && getSpeakModel(fullId).getUser() != null)
                            ((VideoView) fullScreenView).setName(getSpeakModel(fullId).getUser().getName());
                    }
                }
                view.notifyViewAdded(fullScreenView, indexOfUserId(fullId));
            }
            routerListener.setFullScreenView(ppt);// set full screen to PPT
            fullScreenKVO.setParameterWithoutNotify(PPT_TAG);
        }
    }


    private void initDisplayMap() {
        displayMap = new LinkedHashMap<>();
        displayMap.put(SpeakersType.PPT, new ArrayList<String>(1));
        displayMap.put(SpeakersType.Presenter, new ArrayList<String>(2));
        displayMap.put(SpeakersType.Record, new ArrayList<String>(1));
        displayMap.put(SpeakersType.VideoPlay, new ArrayList<String>());
        displayMap.put(SpeakersType.Speaking, new ArrayList<String>());
        displayMap.put(SpeakersType.Applying, new ArrayList<String>());
    }

    private void initView() {

        LPPlayerListener lpOnPlayReadyListener = new LPPlayerListener() {
            @Override
            public void onReadyToPlay(int userId) {
                if (userId < 0) {
                    view.stopLoadingAnimation(-1);
                    return;
                }
                int pos = indexOfUserId(String.valueOf(userId));
                try {
                    if (pos >= 0)
                        view.stopLoadingAnimation(pos);
                    else if (fullScreenKVO.getParameter() != null && fullScreenKVO.getParameter().equals(String.valueOf(userId))) {

                        if (routerListener.getBackgroundContainer().getChildCount() > 0 && routerListener.getBackgroundContainer().getChildAt(0) instanceof VideoView)
                            ((VideoView) routerListener.getBackgroundContainer().getChildAt(0)).stopRotate();
                    } else {
                        view.stopLoadingAnimation(-1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    view.stopLoadingAnimation(-1);
                }
            }

            @Override
            public void onPlayAudioSuccess(int userId) {

            }

            @Override
            public void onPlayVideoSuccess(int userId) {

            }

            @Override
            public void onPlayClose(int userId) {

            }
        };

        routerListener.getLiveRoom().getPlayer().addPlayerListener(lpOnPlayReadyListener);

        if (disableSpeakQueuePlaceholder)
            view.disableSpeakQueuePlaceholder();

        if (!PPT_TAG.equals(fullScreenKVO.getParameter())) {
            displayMap.get(SpeakersType.PPT).add(PPT_TAG);
        }

        if (displayMap.get(SpeakersType.Presenter).isEmpty() && routerListener.getLiveRoom().getPresenterUser() != null &&
                !routerListener.getLiveRoom().getPresenterUser().getUserId().equals(routerListener.getLiveRoom().getCurrentUser().getUserId())) {
            displayMap.get(SpeakersType.Presenter).add(routerListener.getLiveRoom().getPresenterUser().getUserId());
        }

        if (routerListener.getLiveRoom().getRecorder().isVideoAttached() && displayMap.get(SpeakersType.Record).isEmpty()) {
            displayMap.get(SpeakersType.Record).add(RECORD_TAG);
        }

        for (IMediaModel model : routerListener.getLiveRoom().getSpeakQueueVM().getSpeakQueueList()) {
            // 老师有第二路流 辅助摄像头的教室不让切主讲
            if (model.hasExtraStreams()
                    && model.getUser().getUserId().equals(routerListener.getLiveRoom().getPresenterUser().getUserId())) {
                displayMap.get(SpeakersType.Presenter).add(model.getExtraStreams().get(0).getMediaId());
                continue;
            }
            if (routerListener.getLiveRoom().getPresenterUser() == null) {
                if (model.isVideoOn())
                    displayMap.get(SpeakersType.VideoPlay).add(model.getUser().getUserId());
                else
                    displayMap.get(SpeakersType.Speaking).add(model.getUser().getUserId());
            } else {
                if (!model.getUser().getUserId().equals(routerListener.getLiveRoom().getPresenterUser().getUserId()) && model.isVideoOn())
                    displayMap.get(SpeakersType.VideoPlay).add(model.getUser().getUserId());
                else if (!model.getUser().getUserId().equals(routerListener.getLiveRoom().getPresenterUser().getUserId()) && !model.isVideoOn())
                    displayMap.get(SpeakersType.Speaking).add(model.getUser().getUserId());
            }
        }

        for (IUserModel model : routerListener.getLiveRoom().getSpeakQueueVM().getApplyList()) {
            // add other Speaking users
            displayMap.get(SpeakersType.Speaking).add(model.getUserId());
        }
        // init view
        for (int i = 0; i < getList().size(); i++) {
            view.notifyItemInserted(i, null);
            if (getItemViewType(i) != VIEW_TYPE_SPEAKER)
                continue;
            if (getSpeakModel(i).isVideoOn())
                playVideo(getSpeakModel(i).getUser().getUserId());
        }
    }

    @Override
    public void subscribe() {

        Consumer<List<IMediaModel>> activeUserSubscriber = iMediaModels -> {
            initView();
            isInit = true;
        };
        subscriptionOfActiveUser = routerListener.getLiveRoom().getSpeakQueueVM().getObservableOfActiveUsers().observeOn(AndroidSchedulers.mainThread()).subscribe(activeUserSubscriber);
        routerListener.getLiveRoom().getSpeakQueueVM().requestActiveUsers();

        subscriptionOfMediaPublish = routerListener.getLiveRoom().getSpeakQueueVM().getObservableOfMediaPublish()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(iMediaModel -> {
                    if (fullScreenKVO.getParameter() != null && fullScreenKVO.getParameter().equals(iMediaModel.getUser().getUserId())) {
                        if (!iMediaModel.isVideoOn()) {
                            setPPTToFullScreen();
                            if (displayMap.get(SpeakersType.VideoPlay).contains(iMediaModel.getUser().getUserId())) {
                                view.notifyItemDeleted(indexOfUserId(iMediaModel.getUser().getUserId()));
                                displayMap.get(SpeakersType.VideoPlay).remove(iMediaModel.getUser().getUserId());
                                if (iMediaModel.isAudioOn()) {
                                    displayMap.get(SpeakersType.Speaking).add(iMediaModel.getUser().getUserId());
                                    view.notifyItemInserted(indexOfUserId(iMediaModel.getUser().getUserId()), null);
                                }
                            } else if (displayMap.get(SpeakersType.Presenter).contains(iMediaModel.getUser().getUserId())) {
                                view.notifyItemChanged(0, iMediaModel);
                            }
                        } else {
                            if (iMediaModel.skipRelease() != 1)
                                routerListener.getLiveRoom().getPlayer().replay(iMediaModel.getUser().getUserId());
                        }
                        return;
                    }


                    int position = indexOfUserId(iMediaModel.getUser().getUserId());
                    if (position >= 0) {// 窗口已经打开
                        if (iMediaModel.isVideoOn()) { // 窗口已经打开，音频调整；
                            if (displayMap.get(SpeakersType.Speaking).contains(iMediaModel.getUser().getUserId())) { // 音频
                                view.notifyItemDeleted(position);
                                displayMap.get(SpeakersType.Speaking).remove(iMediaModel.getUser().getUserId());
                                displayMap.get(SpeakersType.VideoPlay).add(iMediaModel.getUser().getUserId());
                                view.notifyItemInserted(indexOfUserId(iMediaModel.getUser().getUserId()), null);
                            } else { // 刷新窗口
                                view.updateView(position, view.getChildAt(position));
                            }
                        } else if (!iMediaModel.isVideoOn() && !iMediaModel.isAudioOn()) {
                            if (displayMap.get(SpeakersType.Presenter).contains(iMediaModel.getUser().getUserId()))
                                view.notifyItemChanged(position, iMediaModel);
                            else {
                                view.notifyItemDeleted(position);
                                if (displayMap.get(SpeakersType.VideoPlay).contains(iMediaModel.getUser().getUserId())) {
                                    displayMap.get(SpeakersType.VideoPlay).remove(iMediaModel.getUser().getUserId());
                                } else if (displayMap.get(SpeakersType.Speaking).contains(iMediaModel.getUser().getUserId())) {
                                    displayMap.get(SpeakersType.Speaking).remove(iMediaModel.getUser().getUserId());
                                }
                            }
                        } else if (iMediaModel.isAudioOn()) {
                            if (displayMap.get(SpeakersType.Presenter).contains(iMediaModel.getUser().getUserId()))
                                view.notifyItemChanged(position, iMediaModel);
                            else if (displayMap.get(SpeakersType.VideoPlay).contains(iMediaModel.getUser().getUserId())) {
                                view.notifyItemDeleted(position);
                                displayMap.get(SpeakersType.VideoPlay).remove(iMediaModel.getUser().getUserId());
                                displayMap.get(SpeakersType.Speaking).add(iMediaModel.getUser().getUserId());
                                view.notifyItemInserted(indexOfUserId(iMediaModel.getUser().getUserId()), null);
                            }
                        }
                    } else { // 窗口未打开
                        if (routerListener.getLiveRoom().getPresenterUser() != null &&
                                iMediaModel.getUser().getUserId().equals(routerListener.getLiveRoom().getPresenterUser().getUserId())) {
                            displayMap.get(SpeakersType.Presenter).add(iMediaModel.getUser().getUserId());
                            view.notifyItemInserted(indexOfUserId(iMediaModel.getUser().getUserId()), null);
                        } else {
                            if (iMediaModel.isVideoOn()) {
                                displayMap.get(SpeakersType.VideoPlay).add(iMediaModel.getUser().getUserId());
                                view.notifyItemInserted(indexOfUserId(iMediaModel.getUser().getUserId()), null);
                            } else if (iMediaModel.isAudioOn()) {
                                displayMap.get(SpeakersType.Speaking).add(iMediaModel.getUser().getUserId());
                                view.notifyItemInserted(indexOfUserId(iMediaModel.getUser().getUserId()), null);
                            }
                        }
                    }
                });

        subscriptionOfMediaPublishExt = routerListener.getLiveRoom().getSpeakQueueVM().getObservableOfMediaPublishExt()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(iMediaModel -> {
                    if (iMediaModel.isVideoOn() && !displayMap.get(SpeakersType.Presenter).contains(iMediaModel.getMediaId())) {
                        displayMap.get(SpeakersType.Presenter).add(iMediaModel.getMediaId());
                        view.notifyItemInserted(indexOfUserId(iMediaModel.getMediaId()), null);
                    } else if (!iMediaModel.isVideoOn()) {
                        view.notifyItemDeleted(indexOfUserId(iMediaModel.getMediaId()));
                        displayMap.get(SpeakersType.Presenter).remove(iMediaModel.getMediaId());
                    }
                });

        subscriptionOfUserIn = routerListener.getLiveRoom().getObservableOfUserIn().observeOn(AndroidSchedulers.mainThread())
                .subscribe(iUserInModel -> tempUserIn = iUserInModel.getUser());

        subscriptionOfPresenterChange = routerListener.getLiveRoom().getSpeakQueueVM().getObservableOfPresenterChange()
                .filter(s -> {
//                        return (!displayMap.get(SpeakersType.Presenter).isEmpty() || !fullScreenKVO.getParameter().equals(PPT_TAG));
                    return isInit;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newPresenter -> {
                    if (TextUtils.isEmpty(newPresenter)) {
                        // presenter is null
                        return;
                    }
                    setPPTToFullScreen();

                    if (routerListener.isTeacherOrAssistant()) {
                        if (displayMap.get(SpeakersType.Presenter).isEmpty()) {
                            if (newPresenter.equals(routerListener.getLiveRoom().getCurrentUser().getUserId()))
                                return;
                            int switchIndex = indexOfUserId(newPresenter);
                            if (switchIndex < 0) {
                                displayMap.get(SpeakersType.Presenter).add(newPresenter);
                                view.notifyItemInserted(0, null);
                                return;
                            }
                            View removedView = null;
                            if (displayMap.get(SpeakersType.VideoPlay).contains(newPresenter)) {
                                removedView = view.notifyItemDeleted(switchIndex);
                                displayMap.get(SpeakersType.VideoPlay).remove(newPresenter);

                            } else if (displayMap.get(SpeakersType.Speaking).contains(newPresenter)) {
                                removedView = view.notifyItemDeleted(switchIndex);
                                displayMap.get(SpeakersType.Speaking).remove(newPresenter);
                            }
                            displayMap.get(SpeakersType.Presenter).add(newPresenter);
                            view.notifyItemInserted(0, removedView);
                        } else {
                            String lastPresenter = displayMap.get(SpeakersType.Presenter).get(0);
                            if (newPresenter.equals(lastPresenter)) return;

                            int switchIndex = indexOfUserId(newPresenter);
                            View removedView = null;
                            if (switchIndex >= 0) {
                                if (displayMap.get(SpeakersType.VideoPlay).contains(newPresenter)) {
                                    removedView = view.notifyItemDeleted(switchIndex);
                                    displayMap.get(SpeakersType.VideoPlay).remove(newPresenter);

                                } else if (displayMap.get(SpeakersType.Speaking).contains(newPresenter)) {
                                    removedView = view.notifyItemDeleted(switchIndex);
                                    displayMap.get(SpeakersType.Speaking).remove(newPresenter);
                                }
                            }
                            View removePresenterView = null;
                            if (newPresenter.equals(routerListener.getLiveRoom().getCurrentUser().getUserId())) {
                                removePresenterView = view.notifyItemDeleted(0);
                                displayMap.get(SpeakersType.Presenter).clear();
                                view.showToast("您成为了主讲");
                            } else {
                                displayMap.get(SpeakersType.Presenter).clear();
                                displayMap.get(SpeakersType.Presenter).add(newPresenter);
                                removePresenterView = view.notifyItemDeleted(0);
                                view.notifyItemInserted(0, removedView);
                                view.showToast(getSpeakModel(newPresenter).getUser().getName() + "成为了主讲");
                            }

                            IMediaModel lastPresenterModel = getSpeakModel(lastPresenter);
                            if (lastPresenterModel != null && (lastPresenterModel.isVideoOn() || lastPresenterModel.isAudioOn())) {
                                if (lastPresenterModel.isVideoOn()) {
                                    displayMap.get(SpeakersType.VideoPlay).add(lastPresenter);
                                    view.notifyItemInserted(indexOfUserId(lastPresenter), removePresenterView);
                                } else if (lastPresenterModel.isAudioOn()) {
                                    displayMap.get(SpeakersType.Speaking).add(lastPresenter);
                                    view.notifyItemInserted(indexOfUserId(lastPresenter), removePresenterView);
                                }
                            }
                        }
                        return;
                    }

                    if (displayMap.get(SpeakersType.Presenter).isEmpty()) return;

                    if (newPresenter.equals(getCurrentUser().getUserId()) && !routerListener.isTeacherOrAssistant()) {
                        view.showToast("您不能被设为主讲！");
                        return;
                    }

                    if (!displayMap.get(SpeakersType.Presenter).isEmpty()) { // presenter is not empty
                        String lastPresenter = displayMap.get(SpeakersType.Presenter).get(0);
                        if (TextUtils.isEmpty(lastPresenter)) {
                            return;
                        }

                        if (indexOfUserId(newPresenter) < 0) {
                            //new presenter has no position in the list
                            displayMap.get(SpeakersType.Presenter).clear();
                            displayMap.get(SpeakersType.Presenter).add(newPresenter);// add new presenter to the displayMap

                            View removedPresenterView = view.notifyItemDeleted(0);
                            view.notifyItemInserted(0, null); // update presenter view, new presenter position ~= 0;
                            IMediaModel lastModel = getSpeakModel(lastPresenter);
                            if (lastModel != null && (lastModel.isAudioOn() || lastModel.isVideoOn())) {
                                // if new presenter's camera or mic is on, we will insert a new position for him
                                if (lastModel.isVideoOn()) {
                                    if (!displayMap.get(SpeakersType.VideoPlay).contains(lastPresenter)) {
                                        displayMap.get(SpeakersType.VideoPlay).add(lastPresenter);
                                        view.notifyItemInserted(indexOfUserId(lastPresenter), removedPresenterView);
                                    }
                                } else if (!lastModel.isVideoOn()) {
                                    if (!displayMap.get(SpeakersType.Speaking).contains(lastPresenter)) {
                                        displayMap.get(SpeakersType.Speaking).add(lastPresenter);
                                        view.notifyItemInserted(indexOfUserId(lastPresenter), removedPresenterView);
                                    }
                                }
                            }
                        } else {
                            // new presenter was at list
                            int switchIndex = indexOfUserId(newPresenter); //  find out new presenter's last position
                            // switch presenter in displayMap
                            displayMap.get(SpeakersType.Presenter).clear();
                            displayMap.get(SpeakersType.Presenter).add(newPresenter);

//                                View removePresenterView = view.notifyItemDeleted(0);
//                                view.notifyItemInserted(0, null);

                            IMediaModel lastSpeakModel = getSpeakModel(lastPresenter);

                            if (lastSpeakModel != null && (lastSpeakModel.isVideoOn() || lastSpeakModel.isAudioOn())) {
                                // if last presenter's camera or mic is on, it will replace new presenter's position in the list
                                if (lastSpeakModel.isVideoOn()) {
                                    if (displayMap.get(SpeakersType.VideoPlay).contains(newPresenter)) {
                                        int newPresenterIndex = displayMap.get(SpeakersType.VideoPlay).indexOf(newPresenter);
                                        displayMap.get(SpeakersType.VideoPlay).set(newPresenterIndex, lastPresenter);
                                        View removeView = view.notifyItemDeleted(switchIndex);

                                        View removePresenterView = view.notifyItemDeleted(0);
                                        view.notifyItemInserted(0, removeView);
                                        view.notifyItemInserted(switchIndex, removePresenterView);
                                    } else if (displayMap.get(SpeakersType.Speaking).contains(newPresenter)) {
                                        View removePresenterView = view.notifyItemDeleted(switchIndex);
                                        displayMap.get(SpeakersType.Speaking).remove(newPresenter);
                                        displayMap.get(SpeakersType.VideoPlay).add(lastPresenter);
                                        view.notifyItemInserted(indexOfUserId(lastPresenter), null);

                                        view.notifyItemDeleted(0);
                                        view.notifyItemInserted(0, removePresenterView);
                                    }
                                } else {
                                    if (displayMap.get(SpeakersType.Speaking).contains(newPresenter)) {
                                        int newPresenterIndex = displayMap.get(SpeakersType.Speaking).indexOf(newPresenter);
                                        displayMap.get(SpeakersType.Speaking).set(newPresenterIndex, lastPresenter);
                                        view.notifyItemChanged(switchIndex, null);

                                        view.notifyItemDeleted(0);
                                        view.notifyItemInserted(0, null);
                                    } else if (displayMap.get(SpeakersType.VideoPlay).contains(newPresenter)) {
                                        View removePresenterView = view.notifyItemDeleted(switchIndex);
                                        displayMap.get(SpeakersType.VideoPlay).remove(newPresenter);
                                        displayMap.get(SpeakersType.Speaking).add(lastPresenter);
                                        view.notifyItemInserted(indexOfUserId(lastPresenter), null);

                                        view.notifyItemDeleted(0);
                                        view.notifyItemInserted(0, removePresenterView);
                                    } else {
                                        throw new RuntimeException("new presenter not in the speakList");
                                    }
                                }

                            } else {
                                // last presenter's camera and mic is off, simply delete new presenter's last position
                                android.view.View removeView = null;
                                if (switchIndex != 0 && getSpeakersType(newPresenter) != SpeakersType.Record)
                                    removeView = view.notifyItemDeleted(switchIndex);
                                if (displayMap.get(SpeakersType.VideoPlay).contains(newPresenter)) {
                                    displayMap.get(SpeakersType.VideoPlay).remove(newPresenter);
//                                        getPlayer().playAVClose(newPresenter);
                                } else if (displayMap.get(SpeakersType.Speaking).contains(newPresenter))
                                    displayMap.get(SpeakersType.Speaking).remove(newPresenter);

                                view.notifyItemDeleted(0); //delete last presenter
                                view.notifyItemInserted(0, removeView);
                            }
                        }
                    } else {
                        displayMap.get(SpeakersType.Presenter).add(newPresenter);
                        view.notifyItemInserted(indexOfUserId(newPresenter), null);
                    }
                    view.showToast(getSpeakModel(newPresenter).getUser().getName() + "成为了主讲");
                });

        // 主讲人退出教室
        subscriptionOfUserOut = routerListener.getLiveRoom()
                .getObservableOfUserOut()
                .observeOn(AndroidSchedulers.mainThread())
                .filter(s -> {
                    IUserModel presenterModel = routerListener.getLiveRoom().getPresenterUser();
                    return presenterModel != null &&
                            presenterModel.getUserId().equals(s); // 主讲人退出教室
                })
                .subscribe(s -> {
                    if (s.equals(fullScreenKVO.getParameter())) {
//                            fullScreenKVO.setParameter(null);
                        return;
                    }
                    if (indexOfUserId(s) < 0) return;
                    if (getSpeakersType(s) == SpeakersType.Presenter) {
                        if (displayMap.get(SpeakersType.Presenter).size() == 2) {
                            String extMediaUserId = displayMap.get(SpeakersType.Presenter).get(1);
                            view.notifyItemDeleted(indexOfUserId(extMediaUserId));
                            getPlayer().playAVClose(extMediaUserId);
                        }
                        view.notifyItemDeleted(indexOfUserId(s));
                        getPlayer().playAVClose(s);
                        displayMap.get(SpeakersType.Presenter).clear();
                    }
                });

        subscriptionOfShareDesktopAndPlayMedia = routerListener.getLiveRoom().
                getObservableOfPlayMedia().mergeWith(routerListener.getLiveRoom().
                getObservableOfShareDesktop())
                .filter(aBoolean -> {
                    // 不是老师都自动全屏
                    return routerListener.getLiveRoom().getCurrentUser() != null
                            && routerListener.getLiveRoom().getCurrentUser().getType() != LPConstants.LPUserType.Teacher;
                })
                .filter(aBoolean -> aBoolean &&
                        routerListener.getLiveRoom().getPresenterUser() != null &&
                        routerListener.getLiveRoom().getTeacherUser() != null &&
                        routerListener.getLiveRoom().getPresenterUser().getUserId().equals(
                                routerListener.getLiveRoom().getTeacherUser().getUserId()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    if (routerListener.getLiveRoom().getTeacherUser() == null)
                        return;
                    String teacherId = routerListener.getLiveRoom().getTeacherUser().getUserId();
                    if (!fullScreenKVO.getParameter().equals(teacherId)
                            && getSpeakModel(teacherId) != null
                            && getSpeakModel(teacherId).isVideoOn() && autoPlayPresenterVideo) {
                        fullScreenKVO.setParameter(teacherId);
                    }
                });

        subscriptionOfAutoFullScreenTeacher = Observable
                .zip(routerListener.getLiveRoom().getSpeakQueueVM().getObservableOfActiveUsers(),
                        routerListener.getLiveRoom().getDocListVM().getObservableOfDocAll(), (iMediaModels, lpResRoomDocAllModel) -> {
                            boolean needFullScreenTeacherVideo = false;
                            for (IMediaModel mediaModel : iMediaModels) {
                                if (mediaModel.getUser().getType() == LPConstants.LPUserType.Teacher &&
                                        mediaModel.isVideoOn()) {
                                    needFullScreenTeacherVideo = true;
                                }
                            }
                            return lpResRoomDocAllModel.docList.size() == 1 && needFullScreenTeacherVideo;
                        })
                .filter(aBoolean -> aBoolean)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    String teacherId = routerListener.getLiveRoom().getTeacherUser().getUserId();

                    if (!TextUtils.isEmpty(teacherId) && !teacherId.equals(fullScreenKVO.getParameter())
                            && getSpeakModel(teacherId) != null
                            && getSpeakModel(teacherId).isVideoOn()) {
                        fullScreenKVO.setParameter(teacherId);
                    }
                });

        subscriptionOfFullScreen = fullScreenKVO.newObservableOfParameterChanged()
                .filter(s -> LPConstants.EMPTY_LABEL.equals(s) || !s.equals(fullScreenKVO.getLastParameter()))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    if (TextUtils.isEmpty(s)) {
                        // full screen ppt
                        fullScreenKVO.setParameter(PPT_TAG);
                    } else {
                        String lastTag = fullScreenKVO.getLastParameter();
                        String tag = fullScreenKVO.getParameter();

                        View view1 = routerListener.removeFullScreenView();
                        View view2 = view.removeViewAt(indexOfUserId(tag));

                        if (view1 == null || view2 == null) return;
                        routerListener.setFullScreenView(view2);

                        if (PPT_TAG.equals(tag)) {
                            displayMap.get(SpeakersType.PPT).clear();
                        } else if (displayMap.get(SpeakersType.Presenter).contains(tag)) {
                            displayMap.get(SpeakersType.Presenter).remove(tag);
                        } else if (RECORD_TAG.equals(tag)) {
                            displayMap.get(SpeakersType.Record).clear();
                        } else if (tag != null) { // video
                            displayMap.get(SpeakersType.VideoPlay).remove(tag);
                        }

                        boolean hasPresenter = routerListener.getLiveRoom().getPresenterUser() != null;
                        ArrayList<String> presenters = new ArrayList<>();
                        if (hasPresenter) {
                            String presenterUserId = routerListener.getLiveRoom().getPresenterUser().getUserId();
                            presenters.add(presenterUserId);
                            presenters.add(String.valueOf(Integer.parseInt(presenterUserId) + 1)); // mediaId 喜加一
                        }

                        if (!TextUtils.isEmpty(lastTag)) {
                            if (PPT_TAG.equals(lastTag)) {
                                displayMap.get(SpeakersType.PPT).clear();
                                displayMap.get(SpeakersType.PPT).add(lastTag);
                            } else if (presenters.contains(lastTag)) {
                                displayMap.get(SpeakersType.Presenter).add(presenters.indexOf(lastTag), lastTag);
                            } else if (RECORD_TAG.equals(lastTag)) {
                                displayMap.get(SpeakersType.Record).clear();
                                displayMap.get(SpeakersType.Record).add(lastTag);
                            } else { // video
                                displayMap.get(SpeakersType.VideoPlay).add(lastTag);
                            }
                            view.notifyViewAdded(view1, indexOfUserId(lastTag));
                        }

                    }
                });

        if (routerListener.isTeacherOrAssistant()) {

            subscriptionSpeakApply = routerListener.getLiveRoom().getSpeakQueueVM().getObservableOfSpeakApply()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(iMediaModel -> {
                        displayMap.get(SpeakersType.Applying).add(iMediaModel.getUser().getUserId());
                        view.notifyItemInserted(indexOfUserId(iMediaModel.getUser().getUserId()), null);
                    });

            subscriptionSpeakResponse = routerListener.getLiveRoom().getSpeakQueueVM().getObservableOfSpeakResponse()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(iMediaControlModel -> {
                        int position = indexOfUserId(iMediaControlModel.getUser().getUserId());
                        if (position >= 0) {
                            view.notifyItemDeleted(position);
                            displayMap.get(SpeakersType.Applying).remove(iMediaControlModel.getUser().getUserId());
                        } else if (displayMap.get(SpeakersType.Applying).size() == 1) { // 处理极少数userId 不在列表的情况
                            view.notifyItemDeleted(indexOfUserId(displayMap.get(SpeakersType.Applying).get(0)));
                            displayMap.get(SpeakersType.Applying).clear();
                        }
                    });
        }
        subscriptionOfAward = routerListener.getLiveRoom()
                .getObservableOfAward()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(awardModel -> {
                    awardRecord.putAll(awardModel.value.record);
                    if (awardModel.isFromCache && awardModel.value.record != null) {
                        for (Map.Entry<String, Integer> entry : awardModel.value.record.entrySet()) {
                            IMediaModel iMediaModel = getSpeakModelWithUserNumber(entry.getKey());
                            notifyAward(iMediaModel, entry.getValue(), false);
                        }
                    } else {
                        IMediaModel iMediaModel = getSpeakModelWithUserNumber(awardModel.value.to);
                        notifyAward(iMediaModel, awardModel.value.record.get(awardModel.value.to), true);
                    }
                });

        subscriptionOfClassEnd = routerListener.getLiveRoom()
                .getObservableOfClassEnd()
                .mergeWith(routerListener.getLiveRoom().getObservableOfClassSwitch())
                .subscribe(aVoid -> awardRecord.clear());
    }

    private void notifyAward(IMediaModel iMediaModel, int awardCount, boolean isShowAwardAnimation) {
        if (iMediaModel != null) {
            int index = indexOfUserId(iMediaModel.getUser().getUserId());
            if (index == -1 && iMediaModel.getUser().getNumber().equals(getCurrentUser().getNumber())) {
                index = indexOfUserId(RECORD_TAG);
            }
            view.notifyAwardCountChange(index, awardCount);
            if (isShowAwardAnimation) {
                routerListener.showAwardAnimation(iMediaModel.getUser().getName());
            }
        }
    }

    @Override
    public void unSubscribe() {
        RxUtils.dispose(subscriptionSpeakApply);
        RxUtils.dispose(subscriptionSpeakResponse);
        RxUtils.dispose(subscriptionOfActiveUser);
        RxUtils.dispose(subscriptionOfFullScreen);
        RxUtils.dispose(subscriptionOfUserIn);
        RxUtils.dispose(subscriptionOfUserOut);
        RxUtils.dispose(subscriptionOfPresenterChange);
        RxUtils.dispose(subscriptionOfShareDesktopAndPlayMedia);
        RxUtils.dispose(subscriptionOfAutoFullScreenTeacher);
        RxUtils.dispose(subscriptionOfMediaPublish);
        RxUtils.dispose(subscriptionOfAward);
        RxUtils.dispose(subscriptionOfClassEnd);
        RxUtils.dispose(subscriptionOfMediaPublishExt);
    }

    @Override
    public void destroy() {
        view = null;
        routerListener = null;
        displayMap.clear();
    }

    @Override
    public IUserModel getPresenter() {
        return routerListener.getLiveRoom().getPresenterUser();
    }

    @Override
    public void requestPresenterChange(String userId, boolean isSet) {
        if (!clickableCheck()) {
            view.showToast("请勿频繁切换");
            return;
        }
        if (RECORD_TAG.equals(userId) || !isSet)
            routerListener.getLiveRoom().getSpeakQueueVM().requestSwitchPresenter(routerListener.getLiveRoom().getCurrentUser().getUserId());
        else
            routerListener.getLiveRoom().getSpeakQueueVM().requestSwitchPresenter(userId);
    }

    @Override
    public boolean isHasDrawingAuth(String userId) {
        IMediaModel model = getSpeakModel(userId);
        if (model == null) return false;
        return routerListener.getLiveRoom().getSpeakQueueVM().getStudentsDrawingAuthList().contains(model.getUser().getNumber());
    }

    @Override
    public void requestStudentDrawingAuth(String userId, boolean isAddAuth) {
        IMediaModel model = getSpeakModel(userId);
        if (model == null) return;
        LPError lpError = routerListener.getLiveRoom().getSpeakQueueVM().requestStudentDrawingAuthChange(isAddAuth, model.getUser().getNumber());
//        if (lpError == null && isAddAuth){
//            view.showToast("授权画笔成功");
//        }else if (lpError == null && !isAddAuth){
//            view.showToast("取消画笔成功");
//        }else if ( isAddAuth){
//            view.showToast("授权画笔失败");
//        }else if ( !isAddAuth){
//            view.showToast("取消画笔失败");
//        }
    }

    @Override
    public boolean isEnableGrantDrawing() {
        return routerListener.getLiveRoom().getPartnerConfig().liveDisableGrantStudentBrush == 0;
    }

    @Override
    public boolean isEnableSwitchPresenter() {
        return routerListener.getLiveRoom().getPartnerConfig().isEnableSwitchPresenter == 1;
    }

    @Override
    public LiveRoom getLiveRoom() {
        return routerListener.getLiveRoom();
    }


    @Override
    public int getItemViewType(int position) {

        if (position < 0 || position >= getList().size())
            return -1;
        SpeakersType speakersType = getSpeakersType(getList().get(position));
        if (speakersType == SpeakersType.PPT)
            return VIEW_TYPE_PPT;
        else if (speakersType == SpeakersType.Presenter)
            return VIEW_TYPE_PRESENTER;
        else if (speakersType == SpeakersType.Record)
            return VIEW_TYPE_RECORD;
        else if (speakersType == SpeakersType.VideoPlay)
            return VIEW_TYPE_VIDEO_PLAY;
        else if (speakersType == SpeakersType.Speaking)
            return VIEW_TYPE_SPEAKER;
        else
            return VIEW_TYPE_APPLY;
    }

    @Override
    public int getItemViewType(String userId) {
        return getItemViewType(indexOfUserId(userId));
    }

    @Override
    public LPRecorder getRecorder() {
        return routerListener.getLiveRoom().getRecorder();
    }

    @Override
    public LPPlayer getPlayer() {
        return routerListener.getLiveRoom().getPlayer();
    }

    @Override
    public IUserModel getApplyModel(int position) {
        String userId = getList().get(position);
        for (IUserModel model : routerListener.getLiveRoom().getSpeakQueueVM().getApplyList()) {
            if (model.getUserId().equals(userId)) {
                return model;
            }
        }
        return null;
    }

    @Override
    public IMediaModel getSpeakModel(String userId) {
        if (TextUtils.isEmpty(userId)) return null;
        for (IMediaModel model : routerListener.getLiveRoom().getSpeakQueueVM().getSpeakQueueList()) {
            if (!TextUtils.isEmpty(model.getMediaId())) {
                if (userId.equals(model.getMediaId()))
                    return model;
            } else if (model.getUser().getUserId().equals(userId)) {
                return model;
            }
        }
        // presenter mismatching
        if (routerListener.getLiveRoom().getPresenterUser() != null &&
                userId.equals(routerListener.getLiveRoom().getPresenterUser().getUserId())) {
            LPMediaModel model = new LPMediaModel();
            model.user = (LPUserModel) routerListener.getLiveRoom().getPresenterUser();
            return model;
        }

        if (tempUserIn != null && tempUserIn.getUserId().equals(userId)) {
            LPMediaModel model = new LPMediaModel();
            model.user = (LPUserModel) tempUserIn;
            return model;
        }
        // mismatching
        LPMediaModel model = new LPMediaModel();
        model.user = (LPUserModel) routerListener.getLiveRoom().getOnlineUserVM().getUserById(userId);
        return model;
    }

    private IMediaModel getSpeakModelWithUserNumber(String userNumber) {
        if (TextUtils.isEmpty(userNumber)) return null;
        if (userNumber.equals(routerListener.getLiveRoom().getCurrentUser().getNumber())) {
            LPMediaModel model = new LPMediaModel();
            model.user = (LPUserModel) routerListener.getLiveRoom().getCurrentUser();
            return model;
        }
        for (IMediaModel model : routerListener.getLiveRoom().getSpeakQueueVM().getSpeakQueueList()) {
            if (model.getUser().getNumber().equals(userNumber)) {
                return model;
            }
        }
        // presenter mismatching
        if (routerListener.getLiveRoom().getPresenterUser() != null &&
                userNumber.equals(routerListener.getLiveRoom().getPresenterUser().getNumber())) {
            LPMediaModel model = new LPMediaModel();
            model.user = (LPUserModel) routerListener.getLiveRoom().getPresenterUser();
            return model;
        }

        if (tempUserIn != null && tempUserIn.getNumber().equals(userNumber)) {
            LPMediaModel model = new LPMediaModel();
            model.user = (LPUserModel) tempUserIn;
            return model;
        }
        return null;
    }

    @Override
    public IMediaModel getSpeakModel(int position) {
        String userId = getList().get(position);
        return getSpeakModel(userId);
    }

    @Override
    public void playVideo(String userId) {
        int position = indexOfUserId(userId);

        if (position == -1) return;
        if (getSpeakModel(position) == null || !getSpeakModel(position).isVideoOn()) return;

        if (displayMap.get(SpeakersType.Presenter).contains(userId)) {
            autoPlayPresenterVideo = true;
            view.notifyItemChanged(position, null);
            return;
        }

        IMediaModel model = getSpeakModel(fullScreenKVO.getParameter());
        boolean isFullScreenStudentVideo = model.getUser() != null && model.getUser().getType() == LPConstants.LPUserType.Student;

        if (displayMap.get(SpeakersType.Presenter).size() + displayMap.get(SpeakersType.VideoPlay).size() + (isFullScreenStudentVideo ? 1 : 0) >= MAX_VIDEO_COUNT) {
            view.showMaxVideoExceed();
            return;
        }
        if (displayMap.get(SpeakersType.Speaking).contains(userId)) {
            view.notifyItemDeleted(position);
            displayMap.get(SpeakersType.Speaking).remove(userId);
        }

        if (displayMap.get(SpeakersType.VideoPlay).contains(userId)) {
            view.notifyItemChanged(position, null);
            return;
        }
        displayMap.get(SpeakersType.VideoPlay).add(userId);
        view.notifyItemInserted(indexOfUserId(userId), null);
    }

    @Override
    public void closeVideo(String tag) {
        if (TextUtils.isEmpty(tag)) return;
        if (tag.equals(RECORD_TAG)) {
            if (routerListener.getLiveRoom().getRecorder().isVideoAttached()) {
                routerListener.detachLocalVideo();
//                if (routerListener.getLiveRoom().getRecorder().isPublishing()) {
//                    routerListener.getLiveRoom().getRecorder().stopPublishing();
//                }
            }
            return;
        } else if (tag.equals(PPT_TAG)) {
            throw new RuntimeException("close PPT? wtf");
        }
        int position = indexOfUserId(tag);

        // 在dialog操作过程中 数据发生了变化
        if (position == -1) return;
        IMediaModel model = getSpeakModel(position);
        if (model == null) return;

        if (displayMap.get(SpeakersType.Presenter).contains(tag)) { // presenter
            autoPlayPresenterVideo = false;
            view.notifyItemChanged(position, null);
            return;
        }

        routerListener.getLiveRoom().getPlayer().playAVClose(tag);
        routerListener.getLiveRoom().getPlayer().playAudio(tag);
        if (displayMap.get(SpeakersType.VideoPlay).contains(tag)) {
            view.notifyItemDeleted(position);
            displayMap.get(SpeakersType.VideoPlay).remove(tag);

            displayMap.get(SpeakersType.Speaking).add(tag);
            view.notifyItemInserted(indexOfUserId(tag), null);
        }
    }

    @Override
    public void switchVideoDefinition(String userId, LPConstants.VideoDefinition definition) {
        int position = indexOfUserId(userId);
        if (position == -1) return;
        if (definition == null) return;
        routerListener.getLiveRoom().getPlayer().changeVideoDefinition(userId, definition);
    }

    @Override
    public void closeSpeaking(String userId) {
        int position = indexOfUserId(userId);
        if (position == -1) return;
        routerListener.getLiveRoom().getSpeakQueueVM().closeOtherSpeak(userId);
    }

    @Override
    public boolean isTeacherOrAssistant() {
        return routerListener.isTeacherOrAssistant();
    }

    @Override
    public boolean isCurrentTeacher() {
        return routerListener.isCurrentUserTeacher();
    }


    @Override
    public void changeBackgroundContainerSize(boolean isShrink) {
        routerListener.changeBackgroundContainerSize(isShrink);
    }

    @Override
    public void setFullScreenTag(String tag) {
        fullScreenKVO.setParameter(tag);
    }

    @Override
    public MyPPTView getPPTFragment() {
        return routerListener.getPPTView();
    }

    @Override
    public boolean isFullScreen(String tag) {
        checkNotNull(tag);
        return tag.equals(fullScreenKVO.getParameter());
    }

    @Override
    public void switchCamera() {
        routerListener.getLiveRoom().getRecorder().switchCamera();
    }

    @Override
    public void switchPrettyFilter() {
        if (getRecorder().isBeautyFilterOn()) {
            getRecorder().closeBeautyFilter();
        } else {
            getRecorder().openBeautyFilter();
        }
    }

    private boolean isScreenCleared = false;

    @Override
    public void clearScreen() {
        isScreenCleared = !isScreenCleared;
        if (isScreenCleared) routerListener.clearScreen();
        else routerListener.unClearScreen();
    }

    @Override
    public boolean isAutoPlay() {
        return autoPlayPresenterVideo;
    }

    @Override
    public LPEnterRoomNative.LPWaterMark getWaterMark() {
        return routerListener.getLiveRoom().getPartnerConfig().waterMark;
    }

    @Override
    public String getItem(int position) {
        if (position < getList().size())
            return getList().get(position);
        else
            throw new RuntimeException("position > displayList.size() in getItem");
    }

    @Override
    public int getCount() {
        return getList().size();
    }

    @Override
    public void agreeSpeakApply(String userId) {
        int position = indexOfUserId(userId);
        if (position == -1) {
            throw new RuntimeException("invalid userId:" + userId + " in agreeSpeakApply");
        } else {
            routerListener.getLiveRoom().getSpeakQueueVM().agreeSpeakApply(userId);
        }
    }

    @Override
    public void disagreeSpeakApply(String userId) {
        int position = indexOfUserId(userId);
        if (position == -1) {
            throw new RuntimeException("invalid userId:" + userId + " in disagreeSpeakApply");
        } else {
            routerListener.getLiveRoom().getSpeakQueueVM().disagreeSpeakApply(userId);
        }
    }

    public void attachVideo() {
        if (routerListener.checkCameraPermission()) {
            if (displayMap.get(SpeakersType.Record).isEmpty()) {
                displayMap.get(SpeakersType.Record).add(RECORD_TAG);
                view.notifyItemInserted(indexOfUserId(RECORD_TAG), null);
            } else {
                view.notifyItemChanged(indexOfUserId(RECORD_TAG), null);
            }
        }
        printSections();
    }

    @Override
    public boolean isStopPublish() {
        return isStopPublish;
    }

    @Override
    public void setIsStopPublish(boolean b) {
        isStopPublish = b;
    }

    @Override
    public boolean isMultiClass() {
        return routerListener.getRoomType() == LPConstants.LPRoomType.Multi;
    }

    @Override
    public void notifyNoSpeakers() {
        routerListener.showNoSpeakers();
    }

    @Override
    public void notifyHavingSpeakers() {
        routerListener.showHavingSpeakers();
    }


    private boolean isStopPublish = false;

    public void detachVideo() {
        if (RECORD_TAG.equals(fullScreenKVO.getParameter())) {
            if (getRecorder().isVideoAttached())
                getRecorder().detachVideo();
//            if (getRecorder().isPublishing())
//                getRecorder().stopPublishing();
            fullScreenKVO.setParameter(LPConstants.EMPTY_LABEL);
            return;
        }
        if (displayMap == null) return;
        if (!displayMap.get(SpeakersType.Record).isEmpty()) {
            view.notifyItemDeleted(indexOfUserId(RECORD_TAG));
            displayMap.get(SpeakersType.Record).clear();
        }
        printSections();
    }

    public boolean isPPTInSpeakersList() {
        return displayMap != null && !displayMap.get(SpeakersType.PPT).isEmpty();
    }

    public void showOptionDialog() {
        view.showOptionDialog();
    }

    private void printSections() {
//        LPLogger.i("section: " + _displayPresenterSection + " " + _displayRecordSection + " " + _displayVideoSection + " " +
//                _displaySpeakerSection + " " + _displayApplySection);
    }

    private Disposable subscriptionOfClickable;

    private boolean clickableCheck() {
        if (subscriptionOfClickable != null && !subscriptionOfClickable.isDisposed()) {
            return false;
        }
        subscriptionOfClickable = Observable.timer(2, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) {
                RxUtils.dispose(subscriptionOfClickable);
            }
        });
        return true;
    }

    @Override
    public boolean isNeedLoading(String uid) {
        return !routerListener.getLiveRoom().getPlayer().isRemoteVideoAvailable(uid);
    }

    @Override
    public void requestAward(String userNumber) {
        awardRecord.put(userNumber, (awardRecord.get(userNumber) == null) ? 1 : awardRecord.get(userNumber) + 1);
        routerListener.getLiveRoom().requestAward(userNumber, awardRecord);
    }

    @Override
    public int getRewardCount(String userNumber) {
        if (TextUtils.isEmpty(userNumber)) {
            return 0;
        }
        return awardRecord.get(userNumber) == null ? 0 : awardRecord.get(userNumber);
    }

    @Override
    public IUserModel getCurrentUser() {
        return routerListener.getLiveRoom().getCurrentUser();
    }

    @Override
    public boolean isUseWebRTC() {
        return routerListener.getLiveRoom().isUseWebRTC();
    }

}
