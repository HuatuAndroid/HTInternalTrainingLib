package com.baijiayun.videoplayer.ui.component;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.baijiayun.videoplayer.event.BundlePool;
import com.baijiayun.videoplayer.event.EventKey;
import com.baijiayun.videoplayer.event.OnPlayerEventListener;
import com.baijiayun.videoplayer.log.BJLog;
import com.baijiayun.videoplayer.player.PlayerStatus;
import com.baijiayun.videoplayer.ui.R;
import com.baijiayun.videoplayer.ui.event.UIEventKey;
import com.baijiayun.videoplayer.ui.listener.OnTouchGestureListener;
import com.baijiayun.videoplayer.ui.utils.NetworkUtils;
import com.baijiayun.videoplayer.util.Utils;


/**
 * Created by yongjiaming on 2018/8/7
 */

public class ControllerComponent extends BaseComponent implements OnTouchGestureListener {

    private final int MSG_CODE_DELAY_HIDDEN_CONTROLLER = 101;

    View mTopContainer;
    View mBottomContainer;
    ImageView mBackIcon;
    TextView mTopTitle;
    ImageView mStateIcon;
    TextView mCurrTime;
    TextView mTotalTime;
    ImageView mSwitchScreen;
    SeekBar mSeekBar;

    private int mBufferPercentage;

    private int mSeekProgress = -1;
    private boolean mControllerTopEnable = true;
    private ObjectAnimator mBottomAnimator;
    private ObjectAnimator mTopAnimator;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_CODE_DELAY_HIDDEN_CONTROLLER:
                    setControllerState(false);
                    break;
            }
        }
    };

    public ControllerComponent(Context context) {
        super(context);
    }

    @Override
    protected View onCreateComponentView(Context context) {
        return View.inflate(context, R.layout.layout_controller_component_new, null);
    }

    @Override
    public void onPlayerEvent(int eventCode, Bundle bundle) {
        switch (eventCode) {
            case OnPlayerEventListener.PLAYER_EVENT_ON_STATUS_CHANGE:
                PlayerStatus status = (PlayerStatus) bundle.getSerializable(EventKey.SERIALIZABLE_DATA);
                if (status == null) {
                    break;
                }
                switch (status) {
                    case STATE_PAUSED:
                        mStateIcon.setSelected(true);
                        break;
                    case STATE_STARTED:
                        mStateIcon.setSelected(false);
                        sendDelayHiddenMessage();
                        break;
                    case STATE_INITIALIZED:
                        mBufferPercentage = 0;
                        updateUI(0, 0);
                        if (getStateGetter().getVideoInfo() != null) {
                            setTitle(getStateGetter().getVideoInfo().getVideoTitle());
                        }
                        break;
                }
                break;
            case OnPlayerEventListener.PLAYER_EVENT_ON_TIMER_UPDATE:
                int currentTime = bundle.getInt(EventKey.INT_DATA);
                updateUI(currentTime, getStateGetter().getDuration());
                //通过这种方式判断视频播完完成，因为STATE_PLAYBACK_COMPLETED状态回调之后还会补发一个timer update
                if (getStateGetter().getPlayerStatus() == PlayerStatus.STATE_PLAYBACK_COMPLETED && currentTime == getStateGetter().getDuration()) {
                    mStateIcon.setSelected(true);
                    mSeekBar.setProgress(0);
                    mSeekBar.setSecondaryProgress(0);
                    setCurrTime(0, getStateGetter().getDuration());
                }
                break;
            case OnPlayerEventListener.PLAYER_EVENT_ON_BUFFERING_UPDATE:
                BJLog.d("bjy", "buffering update " + bundle.getInt(EventKey.INT_DATA));
                setSecondProgress(bundle.getInt(EventKey.INT_DATA));
                break;
            default:
                break;
        }
    }

    @Override
    public void onCustomEvent(int eventCode, Bundle bundle) {
        switch (eventCode) {
            case UIEventKey.CUSTOM_CODE_REQUEST_TOGGLE_SCREEN:
                setSwitchScreenIcon(bundle.getBoolean(EventKey.BOOL_DATA));
                sendDelayHiddenMessage();
                break;
            case UIEventKey.CUSTOM_CODE_NETWORK_CHANGE_TO_MOBILE:
                mStateIcon.setSelected(false);
                break;
            case UIEventKey.CUSTOM_CODE_TAP_PPT:
                toggleController();
                break;
            case UIEventKey.CUSTOM_CODE_NETWORK_DISCONNETCT:
                setControllerState(false);
                break;
            default:
                break;
        }
    }

    @Override
    public void onComponentEvent(int eventCode, Bundle bundle) {
        switch (eventCode) {
            case UIEventKey.CUSTOM_CODE_REQUEST_SEEK:
                int seekToPos = bundle.getInt(EventKey.INT_DATA);
                updateUI(seekToPos, getStateGetter().getDuration());
                break;
        }
    }

    @Override
    protected void onInitView() {
        mTopContainer = findViewById(R.id.cover_player_controller_top_container);
        mBottomContainer = findViewById(R.id.cover_player_controller_bottom_container);
        mBackIcon = findViewById(R.id.cover_player_controller_image_view_back_icon);
        mTopTitle = findViewById(R.id.cover_player_controller_text_view_video_title);
        mStateIcon = findViewById(R.id.cover_player_controller_image_view_play_state);
        mCurrTime = findViewById(R.id.cover_player_controller_text_view_curr_time);
        mTotalTime = findViewById(R.id.cover_player_controller_text_view_total_time);
        mSwitchScreen = findViewById(R.id.cover_player_controller_image_view_switch_screen);
        mSeekBar = findViewById(R.id.cover_player_controller_seek_bar);

        mBackIcon.setOnClickListener(v -> notifyComponentEvent(UIEventKey.CUSTOM_CODE_REQUEST_BACK, null));

        mStateIcon.setOnClickListener(v -> {
            boolean selected = mStateIcon.isSelected();
            if (selected) {
                requestPlay(null);
            } else {
                requestPause(null);
            }
            mStateIcon.setSelected(!selected);
        });

        mSwitchScreen.setOnClickListener(v -> notifyComponentEvent(UIEventKey.CUSTOM_CODE_REQUEST_TOGGLE_SCREEN, null));


        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    updateUI(progress, seekBar.getMax());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mSeekProgress = seekBar.getProgress();
                mHandler.removeCallbacks(mSeekEventRunnable);
                mHandler.postDelayed(mSeekEventRunnable, 300);
            }
        });
    }

    @Override
    protected void setKey() {
        super.key = UIEventKey.KEY_CONTROLLER_COMPONENT;
    }

    private Runnable mSeekEventRunnable = () -> {
        if (mSeekProgress < 0)
            return;
        Bundle bundle = BundlePool.obtain();
        bundle.putInt(EventKey.INT_DATA, mSeekProgress);
        requestSeek(bundle);
    };

    private void setControllerState(boolean state) {
        if (state) {
            sendDelayHiddenMessage();
        } else {
            removeDelayHiddenMessage();
        }
        setTopContainerState(state);
        setBottomContainerState(state);
    }

    private boolean isControllerShow() {
        return mBottomContainer.getVisibility() == View.VISIBLE;
    }

    private void toggleController() {
        if (!NetworkUtils.isNetConnected(getContext())) {
            return;
        }
        if (isControllerShow()) {
            setControllerState(false);
        } else {
            setControllerState(true);
        }
    }

    private void sendDelayHiddenMessage() {
        removeDelayHiddenMessage();
        mHandler.sendEmptyMessageDelayed(MSG_CODE_DELAY_HIDDEN_CONTROLLER, 5000);
    }

    private void removeDelayHiddenMessage() {
        mHandler.removeMessages(MSG_CODE_DELAY_HIDDEN_CONTROLLER);
    }

    private void setCurrTime(int curr, int duration) {
        mCurrTime.setText(Utils.formatDuration(curr, duration >= 3600));
    }

    private void setTotalTime(int duration) {
        mTotalTime.setText(Utils.formatDuration(duration));
    }

    private void setSeekProgress(int curr, int duration) {
        mSeekBar.setMax(duration);
        mSeekBar.setProgress(curr);
        float secondProgress = mBufferPercentage * 1.0f / 100 * duration;
        mSeekBar.setSecondaryProgress((int) secondProgress);
    }

    private void setSecondProgress(int bufferPercent) {
        mBufferPercentage = bufferPercent;
        float secondProgress = mBufferPercentage * 1.0f / 100 * mSeekBar.getMax();
        mSeekBar.setSecondaryProgress((int) secondProgress);
    }

    private void setTitle(String text) {
        mTopTitle.setText(text);
    }

    private void setSwitchScreenIcon(boolean isFullScreen) {
        mSwitchScreen.setImageResource(isFullScreen ? R.mipmap.icon_exit_full_screen : R.mipmap.icon_full_screen);
    }

    private void setScreenSwitchEnable(boolean screenSwitchEnable) {
        mSwitchScreen.setVisibility(screenSwitchEnable ? View.VISIBLE : View.GONE);
    }

    private void updateUI(int curr, int duration) {
        setSeekProgress(curr, duration);
        setCurrTime(curr, duration);
        setTotalTime(duration);
    }

    private void setTopContainerState(final boolean state) {
        if (mControllerTopEnable) {
            mTopContainer.clearAnimation();
            cancelTopAnimation();
            mTopAnimator = ObjectAnimator.ofFloat(mTopContainer,
                    "alpha", state ? 0 : 1, state ? 1 : 0).setDuration(300);
            mTopAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    if (state) {
                        mTopContainer.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    if (!state) {
                        mTopContainer.setVisibility(View.GONE);
                    }
                }
            });
            mTopAnimator.start();
        } else {
            mTopContainer.setVisibility(View.GONE);
        }
    }

    private void cancelBottomAnimation() {
        if (mBottomAnimator != null) {
            mBottomAnimator.cancel();
            mBottomAnimator.removeAllListeners();
            mBottomAnimator.removeAllUpdateListeners();
        }
    }

    private void cancelTopAnimation() {
        if (mTopAnimator != null) {
            mTopAnimator.cancel();
            mTopAnimator.removeAllListeners();
            mTopAnimator.removeAllUpdateListeners();
        }
    }

    private void setBottomContainerState(final boolean state) {
        mBottomContainer.clearAnimation();
        cancelBottomAnimation();
        mBottomAnimator = ObjectAnimator.ofFloat(mBottomContainer,
                "alpha", state ? 0 : 1, state ? 1 : 0).setDuration(300);
        mBottomAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                if (state) {
                    mBottomContainer.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (!state) {
                    mBottomContainer.setVisibility(View.GONE);
                }
            }
        });
        mBottomAnimator.start();
    }

    @Override
    public void onSingleTapUp(MotionEvent event) {
        toggleController();
    }

    @Override
    public void onDoubleTap(MotionEvent event) {

    }

    @Override
    public void onDown(MotionEvent event) {

    }

    @Override
    public void onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

    }

    @Override
    public void onEndGesture() {

    }
}
