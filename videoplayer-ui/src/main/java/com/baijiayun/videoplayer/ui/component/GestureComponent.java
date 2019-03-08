package com.baijiayun.videoplayer.ui.component;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baijiayun.videoplayer.event.BundlePool;
import com.baijiayun.videoplayer.event.EventKey;
import com.baijiayun.videoplayer.event.OnPlayerEventListener;
import com.baijiayun.videoplayer.player.PlayerStatus;
import com.baijiayun.videoplayer.ui.R;
import com.baijiayun.videoplayer.ui.event.UIEventKey;
import com.baijiayun.videoplayer.ui.listener.OnTouchGestureListener;
import com.baijiayun.videoplayer.util.Utils;

/**
 * Created by yongjiaming on 2018/8/7
 */
public class GestureComponent extends BaseComponent implements OnTouchGestureListener {

    private LinearLayout volumeLl;
    private TextView volumeTv;
    private ImageView volumeIv;
    private LinearLayout brightnessLl;
    private TextView brightnessTv;
    private LinearLayout fastForwardLl;
    private TextView fastForwardTv;
    private ImageView fastForwardIv;

    private AudioManager audioManager;
    private int mMaxVolume;
    private float brightness = -1;
    private int volume = -1;
    private volatile boolean enableBrightnessCtrl = true; // 允许通过手势控制亮度
    private volatile boolean enableVolumeCtrl = true; // 运行通过手势控制音量
    private volatile boolean enableSeekCtrl = true; // 运行通过手势进行seek
    private int mWidth, mHeight;

    private boolean toSeek;
    private boolean volumeControl;
    private boolean firstTouch;
    private int newPosition = -1;

    public GestureComponent(Context context) {
        super(context);
        initAudioManager(context);
    }

    @Override
    protected View onCreateComponentView(Context context) {
        return View.inflate(context, R.layout.layout_guesture_component, null);
    }

    @Override
    protected void onInitView() {
        volumeLl = findViewById(R.id.cover_player_gesture_operation_volume_box);
        volumeTv = findViewById(R.id.cover_player_gesture_operation_volume_text);
        volumeIv = findViewById(R.id.cover_player_gesture_operation_volume_icon);
        brightnessLl = findViewById(R.id.cover_player_gesture_operation_brightness_box);
        brightnessTv = findViewById(R.id.cover_player_gesture_operation_brightness_text);
        fastForwardLl = findViewById(R.id.cover_player_gesture_operation_fast_forward_box);
        fastForwardIv = findViewById(R.id.cover_player_gesture_operation_fast_forward_text_view_step_time);
        fastForwardTv = findViewById(R.id.cover_player_gesture_operation_fast_forward_text_view_progress_time);
    }

    @Override
    protected void setKey() {
        key = UIEventKey.KEY_GESTURE_COMPONENT;
    }

    private void initAudioManager(Context context) {
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        assert audioManager != null;
        mMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    }

    @Override
    public void onViewAttachedToWindow(View v) {
        super.onViewAttachedToWindow(v);
        getView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mWidth = getView().getWidth();
                mHeight = getView().getHeight();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    getView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });
    }

    @Override
    public void onErrorEvent(int eventCode, Bundle bundle) {
        super.onErrorEvent(eventCode, bundle);
        setComponentVisibility(View.GONE);
    }

    @Override
    public void onPlayerEvent(int eventCode, Bundle bundle) {
        super.onPlayerEvent(eventCode, bundle);
        switch (eventCode) {
            case OnPlayerEventListener.PLAYER_EVENT_ON_STATUS_CHANGE:
                PlayerStatus playerStatus = (PlayerStatus) bundle.getSerializable(EventKey.SERIALIZABLE_DATA);
                if (playerStatus == null) {
                    return;
                }
                switch (playerStatus) {
                    case STATE_STARTED:
                        setComponentVisibility(View.VISIBLE);
                        break;
                    case STATE_ERROR:
                    case STATE_PAUSED:
                    case STATE_PLAYBACK_COMPLETED:
                        setComponentVisibility(View.GONE);
                        break;
                }
                break;
            case UIEventKey.PLAYER_CODE_BUFFERING_START:
                setComponentVisibility(View.GONE);
                break;
            case UIEventKey.PLAYER_CODE_BUFFERING_END:
                setComponentVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    private void onProgressSlide(float percent) {
        int position = getStateGetter().getCurrentPosition();
        int duration = getStateGetter().getDuration();
        int deltaMax = Math.min(100, duration - position);
        int delta = (int) (deltaMax * percent);

        newPosition = delta + position;
        if (newPosition > duration) {
            newPosition = duration;
        } else if (newPosition <= 0) {
            newPosition = 0;
            delta = -position;
        }
        setBrightnessBoxState(false);
        setVolumeBoxState(false);
        setFastForwardState(true);

        String durationText = Utils.formatDuration(duration);
        String positionText = Utils.formatDuration(position + delta, duration >= 3600);
        fastForwardIv.setImageResource(delta > 0 ? R.mipmap.bjplayer_ic_kuaijin : R.mipmap.bjplayer_ic_huitui);
        fastForwardTv.setText(String.format("%s/%s", positionText, durationText));
    }

    private void onBrightnessSlide(float percent) {
        Activity activity = getActivity();
        if (activity == null) {
            return;
        }
        if (brightness < 0) {
            brightness = activity.getWindow().getAttributes().screenBrightness;
            if (brightness <= 0.00f) {
                brightness = 0.50f;
            } else if (brightness < 0.01f) {
                brightness = 0.01f;
            }
        }

        WindowManager.LayoutParams lpa = activity.getWindow().getAttributes();
        lpa.screenBrightness = brightness + percent;
        if (lpa.screenBrightness > 1.0f) {
            lpa.screenBrightness = 1.0f;
        } else if (lpa.screenBrightness < 0.01f) {
            lpa.screenBrightness = 0.01f;
        }
        activity.getWindow().setAttributes(lpa);

        setVolumeBoxState(false);
        setFastForwardState(false);
        setBrightnessBoxState(true);
        brightnessTv.setText((int) (lpa.screenBrightness * 100) + "%");
    }

    private void onVolumeSlide(float percent) {
        if (volume == -1) {
            volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            volume = Math.max(0, volume);
        }

        int index = (int) (percent * mMaxVolume) + volume;
        index = Math.min(Math.max(0, index), mMaxVolume);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, index, 0);

        setVolumeBoxState(true);
        setBrightnessBoxState(false);
        setFastForwardState(false);
        int value = index * 100 / mMaxVolume;
        if (value == 0) {
            volumeTv.setText("off");
            volumeIv.setImageResource(R.mipmap.ic_volume_off_white);
        } else {
            volumeTv.setText(value + "%");
            volumeIv.setImageResource(R.mipmap.ic_volume_up_white);
        }
    }

    @Override
    public void onSingleTapUp(MotionEvent event) {

    }

    @Override
    public void onDoubleTap(MotionEvent event) {

    }

    @Override
    public void onDown(MotionEvent event) {
        firstTouch = true;
    }

    @Override
    public void onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if(getView().getVisibility() == View.GONE){
            return;
        }
        float oldX = e1.getX(), oldY = e1.getY();
        float deltaY = oldY - e2.getY();
        float deltaX = oldX - e2.getX();

        if (firstTouch) {
            toSeek = Math.abs(distanceX) >= Math.abs(distanceY);
            volumeControl = oldX > mWidth * 0.5f;
            if (enableVolumeCtrl && !enableBrightnessCtrl) {
                volumeControl = true;
            } else if (!enableVolumeCtrl && enableBrightnessCtrl) {
                volumeControl = false;
            }
            firstTouch = false;
        }

        if (toSeek) {
            if (enableSeekCtrl) {
                onProgressSlide(-deltaX / mWidth);
            }
        } else {
            float percent = deltaY / mHeight;
            if (volumeControl) {
                if (enableVolumeCtrl) {
                    onVolumeSlide(percent);
                }
            } else {
                if (enableBrightnessCtrl) {
                    onBrightnessSlide(percent);
                }
            }
        }
    }

    @Override
    public void onEndGesture() {
        volume = -1;
        brightness = -1f;
        setVolumeBoxState(false);
        setBrightnessBoxState(false);
        setFastForwardState(false);
        if (newPosition > 0) {
            requestSeek(BundlePool.obtainPrivate(UIEventKey.KEY_BJYVIDEOPLAYER, newPosition));
            newPosition = 0;
        }
    }

    public void setVolumeBoxState(boolean state) {
        if (volumeLl != null) {
            volumeLl.setVisibility(state ? View.VISIBLE : View.GONE);
        }
    }


    public void setBrightnessBoxState(boolean state) {
        if (brightnessLl != null) {
            brightnessLl.setVisibility(state ? View.VISIBLE : View.GONE);
        }
    }

    private void setFastForwardState(boolean state) {
        fastForwardLl.setVisibility(state ? View.VISIBLE : View.GONE);
    }

    private Activity getActivity() {
        Context context = getContext();
        if (context instanceof Activity) {
            return (Activity) context;
        }
        return null;
    }
}
