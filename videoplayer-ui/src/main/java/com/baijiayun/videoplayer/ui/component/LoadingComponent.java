package com.baijiayun.videoplayer.ui.component;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.baijiayun.videoplayer.event.EventKey;
import com.baijiayun.videoplayer.event.OnPlayerEventListener;
import com.baijiayun.videoplayer.player.PlayerStatus;
import com.baijiayun.videoplayer.ui.R;
import com.baijiayun.videoplayer.ui.event.UIEventKey;

/**
 * Created by yongjiaming on 2018/8/7
 */

public class LoadingComponent extends BaseComponent {

    private TextView loadingTipTv;

    public LoadingComponent(Context context) {
        super(context);
    }

    @Override
    protected View onCreateComponentView(Context context) {
        return View.inflate(context, R.layout.layout_loading_component, null);
    }

    @Override
    protected void onInitView() {
        loadingTipTv = findViewById(R.id.loading_tips_tv);
    }

    @Override
    protected void setKey() {
        super.key = UIEventKey.KEY_LOADING_COMPONENT;
    }

    @Override
    public void onPlayerEvent(int eventCode, Bundle bundle) {
        switch (eventCode) {
            case OnPlayerEventListener.PLAYER_EVENT_ON_STATUS_CHANGE:
                PlayerStatus playerStatus = (PlayerStatus) bundle.getSerializable(EventKey.SERIALIZABLE_DATA);
                if (playerStatus == null) {
                    return;
                }
                switch (playerStatus) {
                    case STATE_INITIALIZED:
                        setLoadingState(true);
                        break;
                    case STATE_ERROR:
                    case STATE_PAUSED:
                    case STATE_STARTED:
                    case STATE_STOPPED:
                    case STATE_PREPARED:
                    case STATE_PLAYBACK_COMPLETED:
                        setLoadingState(false);
                        break;
                }
                break;
            case UIEventKey.PLAYER_CODE_BUFFERING_START:
                setLoadingState(true);
                break;
            case UIEventKey.PLAYER_CODE_BUFFERING_END:
                setLoadingState(false);
                break;
        }
    }

    @Override
    public void onErrorEvent(int eventCode, Bundle bundle) {
        setLoadingState(false);
    }

    @Override
    public void onCustomEvent(int eventCode, Bundle bundle) {
        switch (eventCode) {
            case UIEventKey.CUSTOM_CODE_ENTER_ROOM_ERROR:
            case UIEventKey.CUSTOM_CODE_NETWORK_CHANGE_TO_MOBILE:
                setLoadingState(false);
                break;
            case UIEventKey.CUSTOM_CODE_REQUEST_VIDEO_INFO:
                setLoadingState(true);
                break;
            default:
                break;
        }
    }

    private void setLoadingState(boolean show) {
        setComponentVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void setTips(String tips) {
        loadingTipTv.setText(tips);
    }

}
