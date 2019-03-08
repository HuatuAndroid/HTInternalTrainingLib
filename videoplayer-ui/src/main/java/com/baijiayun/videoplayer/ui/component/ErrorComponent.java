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
import com.baijiayun.videoplayer.ui.utils.NetworkUtils;

/**
 * Created by yongjiaming on 2018/8/7
 */

public class ErrorComponent extends BaseComponent {

    private TextView errorMsgTv;
    private TextView errorCodeTv;
    private TextView retryBtn;

    public ErrorComponent(Context context) {
        super(context);
    }

    @Override
    protected View onCreateComponentView(Context context) {
        return View.inflate(context, R.layout.layout_error_component, null);
    }

    @Override
    protected void onInitView() {
        errorMsgTv = findViewById(R.id.error_msg_tv);
        errorCodeTv = findViewById(R.id.error_code_tv);
        retryBtn = findViewById(R.id.retry_btn);
    }

    @Override
    protected void setKey() {
        key = UIEventKey.KEY_ERROR_COMPONENT;
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
                    case STATE_PREPARED:
                    case STATE_STARTED:
                    case STATE_PLAYBACK_COMPLETED:
                        setComponentVisibility(View.GONE);
                        break;
                }
                break;
            case UIEventKey.PLAYER_CODE_BUFFERING_START:
                setComponentVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    @Override
    public void onErrorEvent(int eventCode, Bundle bundle) {
        switch (eventCode) {
            default:
                break;
        }
        setComponentVisibility(View.VISIBLE);
        errorMsgTv.setText(bundle.getString(EventKey.STRING_DATA));
        errorCodeTv.setText("[" + eventCode + "]");
        retryBtn.setOnClickListener(v -> notifyComponentEvent(UIEventKey.CUSTOM_CODE_REQUEST_REPLAY, null));
    }

    @Override
    public void onCustomEvent(int eventCode, Bundle bundle) {
        switch (eventCode) {
            case UIEventKey.CUSTOM_CODE_NETWORK_CHANGE_TO_MOBILE:
                setComponentVisibility(View.VISIBLE);
                errorMsgTv.setText(getContext().getString(R.string.bjplayer_play_no_wifi));
                retryBtn.setText(getContext().getString(R.string.bjplayer_still_play));
                retryBtn.setOnClickListener(v -> {
                    setComponentVisibility(View.GONE);
                    notifyComponentEvent(UIEventKey.CUSTOM_CODE_REQUEST_PLAY, null);
                });
                break;
            case UIEventKey.CUSTOM_CODE_NETWORK_DISCONNETCT:
                setComponentVisibility(View.VISIBLE);
                errorMsgTv.setText(getContext().getString(R.string.bjplayer_video_player_error_no_network));
                retryBtn.setText(getContext().getString(R.string.bjplayer_video_reload));
                retryBtn.setOnClickListener(v -> {
                    if (NetworkUtils.isNetConnected(getContext())) {
                        setComponentVisibility(View.GONE);
                        notifyComponentEvent(UIEventKey.CUSTOM_CODE_REQUEST_PLAY, null);
                    }
                });
                break;
            default:
                break;
        }
    }
}
