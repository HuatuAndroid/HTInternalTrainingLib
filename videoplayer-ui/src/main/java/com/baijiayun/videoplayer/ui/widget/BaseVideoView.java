package com.baijiayun.videoplayer.ui.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.baijiayun.constant.MediaPlayerDebugInfo;
import com.baijiayun.constant.VideoDefinition;
import com.baijiayun.videoplayer.IBJYVideoPlayer;
import com.baijiayun.videoplayer.bean.BJYVideoInfo;
import com.baijiayun.videoplayer.event.EventKey;
import com.baijiayun.videoplayer.log.BJLog;
import com.baijiayun.videoplayer.player.PlayerStatus;
import com.baijiayun.videoplayer.ui.event.UIEventKey;
import com.baijiayun.videoplayer.ui.listener.IComponentEventListener;
import com.baijiayun.videoplayer.ui.listener.IFilter;
import com.baijiayun.videoplayer.ui.listener.PlayerStateGetter;
import com.baijiayun.videoplayer.ui.utils.NetworkUtils;

/**
 * Created by yongjiaming on 2018/9/12 13:48
 */
public class BaseVideoView extends FrameLayout implements PlayerStateGetter{

    protected IBJYVideoPlayer bjyVideoPlayer;
    protected ComponentContainer componentContainer;
    private IComponentEventListener componentEventListener;

    //默认检测网络变化
    protected boolean useDefaultNetworkListener = true;
    //默认不允许移动网络播放
    protected boolean enablePlayWithMobileNetwork = false;
    private NetChangeBroadcastReceiver mBroadcastReceiver;

    public BaseVideoView(@NonNull Context context){
        this(context, null);
    }

    public BaseVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public BaseVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    protected void init(Context context, AttributeSet attributeSet, int defStyleAttr){

    }

    /**
     * 底部控制栏播放按钮点击事件
     */
    protected void requestPlayAction(){

    }

    protected IComponentEventListener internalComponentEventListener = new IComponentEventListener() {
        @Override
        public void onReceiverEvent(int eventCode, Bundle bundle) {
            switch (eventCode) {
                case UIEventKey.CUSTOM_CODE_REQUEST_PAUSE:
                    bjyVideoPlayer.pause();
                    break;
                case UIEventKey.CUSTOM_CODE_REQUEST_REPLAY:
                    bjyVideoPlayer.rePlay();
                    break;
                case UIEventKey.CUSTOM_CODE_REQUEST_SEEK:
                    int seekToPosition = bundle.getInt(EventKey.INT_DATA);
                    bjyVideoPlayer.seek(seekToPosition);
                    break;
                case UIEventKey.CUSTOM_CODE_REQUEST_SET_RATE:
                    bjyVideoPlayer.setPlayRate(bundle.getFloat(EventKey.FLOAT_DATA));
                    break;
                case UIEventKey.CUSTOM_CODE_REQUEST_SET_DEFINITION:
                    bjyVideoPlayer.changeDefinition((VideoDefinition) bundle.getSerializable(EventKey.SERIALIZABLE_DATA));
                    break;
                case UIEventKey.CUSTOM_CODE_REQUEST_PLAY:
                    enablePlayWithMobileNetwork = true;
                    requestPlayAction();
                    break;
            }
            if (componentEventListener != null) {
                componentEventListener.onReceiverEvent(eventCode, bundle);
            }
        }
    };

    public void setComponentEventListener(IComponentEventListener componentEventListener) {
        this.componentEventListener = componentEventListener;
    }

    /**
     * 供外部发射自定义事件到各个component
     *
     * @param eventCode
     * @param bundle
     */
    public void sendCustomEvent(int eventCode, Bundle bundle) {
        componentContainer.dispatchCustomEvent(eventCode, bundle);
    }

    public void sendCustomEvent(IFilter filter, int eventCode, Bundle bundle){
        componentContainer.dispatchCustomEvent(filter, eventCode, bundle);
    }

    /**
     * 是否监听网络变化
     *
     * @param enable true 监听
     */
    public void useDefaultNetworkListener(boolean enable) {
        useDefaultNetworkListener = enable;
    }

    protected void registerNetChangeReceiver() {
        if (mBroadcastReceiver == null) {
            unregisterNetChangeReceiver();
            mBroadcastReceiver = new NetChangeBroadcastReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            getContext().registerReceiver(mBroadcastReceiver, intentFilter);
        }
    }

    protected void unregisterNetChangeReceiver() {
        if (mBroadcastReceiver != null) {
            getContext().unregisterReceiver(mBroadcastReceiver);
            mBroadcastReceiver = null;
        }
    }

    /**
     * 回收资源
     */
    public void onDestroy() {
        unregisterNetChangeReceiver();
        bjyVideoPlayer.release();
        componentEventListener = null;
        componentContainer.destroy();
    }

    /**
     * 获取当前播放器的状态
     *
     * @return
     */
    @Override
    public PlayerStatus getPlayerStatus() {
        return bjyVideoPlayer.getPlayerStatus();
    }

    /**
     * 获取当前播放进度
     *
     * @return
     */
    @Override
    public int getCurrentPosition() {
        return bjyVideoPlayer.getCurrentPosition();
    }

    /**
     * 获取总时长
     *
     * @return
     */
    @Override
    public int getDuration() {
        return bjyVideoPlayer.getDuration();
    }

    /**
     * 获取视频信息
     *
     * @return
     */
    @Nullable
    @Override
    public BJYVideoInfo getVideoInfo() {
        return bjyVideoPlayer.getVideoInfo();
    }

    @Override
    public MediaPlayerDebugInfo getMediaPlayerDebugInfo() {
        return bjyVideoPlayer.getMediaPlayerDebugInfo();
    }

    /**
     * 获取缓冲百分比
     *
     * @return
     */
    @Override
    public int getBufferPercentage() {
        return bjyVideoPlayer.getBufferPercentage();
    }

    /**
     * 获取播放倍速
     *
     * @return
     */
    @Override
    public float getPlayRate() {
        return bjyVideoPlayer.getPlayRate();
    }


    /**
     * 开始播放
     */
    public void play() {
        bjyVideoPlayer.play();
    }

    /**
     * 从startOffset开始播放
     *
     * @param startOffset
     */
    public void play(int startOffset) {
        bjyVideoPlayer.play(startOffset);
    }

    /**
     * 暂停播放
     */
    public void pause() {
        bjyVideoPlayer.pause();
    }

    /**
     * 快进/快退到指定时间
     *
     * @param time
     */
    public void seek(int time) {
        bjyVideoPlayer.seek(time);
    }

    /**
     * 倍速播放[0.5 ~ 2.0]倍
     *
     * @param playRate 倍率
     */
    public void setPlayRate(float playRate) {
        bjyVideoPlayer.setPlayRate(playRate);
    }

    /**
     * 改变清晰度
     * 播放的时候调用，如果没有对应的清晰度不做处理，播本地文件不生效
     *
     * @param definition 清晰度
     * @return true切换清晰度成功  false切换清晰度失败
     */
    public void changeDefinition(VideoDefinition definition) {
        bjyVideoPlayer.changeDefinition(definition);
    }

    /**
     * 设置第三方用户信息，用于统计
     *
     * @param userName     第三方用户名
     * @param userIdentity 第三方用户标识
     */
    public void setUserInfo(String userName, String userIdentity) {
        bjyVideoPlayer.setUserInfo(userName, userIdentity);
    }


    class NetChangeBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(bjyVideoPlayer.isPlayLocalVideo()){
                return;
            }
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
                int netState = NetworkUtils.getNetworkState(context);
                if (!enablePlayWithMobileNetwork && NetworkUtils.isMobile(netState)) {
                    bjyVideoPlayer.pause();
                    componentContainer.dispatchCustomEvent(UIEventKey.CUSTOM_CODE_NETWORK_CHANGE_TO_MOBILE, null);
                }
                if (!NetworkUtils.isNetConnected(context)) {
                    bjyVideoPlayer.pause();
                    BJLog.d("receive network disconnect, pause video");
//                    Toast.makeText(getContext(), "pause video", Toast.LENGTH_LONG).show();
                    componentContainer.dispatchCustomEvent(UIEventKey.CUSTOM_CODE_NETWORK_DISCONNETCT, null);
                }
//                if(NetworkUtils.isWifiConnected(context)){
//                    bjyVideoPlayer.play();
//                    componentContainer.dispatchCustomEvent(UIEventKey.CUSTOM_CODE_NETWORK_CHANGE_TO_WIFI, null);
//                }
            }
        }
    }
}
