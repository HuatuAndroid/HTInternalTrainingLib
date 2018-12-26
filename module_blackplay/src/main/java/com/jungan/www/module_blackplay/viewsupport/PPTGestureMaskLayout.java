package com.jungan.www.module_blackplay.viewsupport;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.jungan.www.module_blackplay.R;
import com.jungan.www.module_blackplay.utils.Query;
import com.baijiahulian.player.BJPlayerView;
import com.baijiahulian.player.mediaplayer.IMediaPlayer;
import com.baijiahulian.player.utils.Utils;


/**
 * Created by yongjiaming on 2018/3/27.
 */

public class PPTGestureMaskLayout extends FrameLayout {

    private GestureDetector mGestureDetector;
    private OnPlayerTapListener playerTapListener;

    //是否支持手势
    private boolean isGestureEnable = false;

    private volatile Boolean enableBrightnessCtrl = true; // 允许通过手势控制亮度
    private volatile Boolean enableVolumeCtrl = true; // 运行通过手势控制音量
    private volatile Boolean enableSeekCtrl = true; // 运行通过手势进行seek

    private AudioManager mAudioManager;
    private int mVolume = -1, mMaxVolume = 0;
    private float brightness = -1;
    private long mProgressSlideNewPosition = -1L;

    private View mCenterView;
    private Query $;
    private BJPlayerView bjPlayerView;

    public PPTGestureMaskLayout(Context context) {
        this(context, null);
    }

    public PPTGestureMaskLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        mMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        mGestureDetector = new GestureDetector(context, new PlayerGestureListener());

        LayoutInflater inflater = LayoutInflater.from(context);
        mCenterView = inflater.inflate(R.layout.ppt_mask_layout_center_controller, this, false);
        addView(mCenterView);

        $ = Query.with(mCenterView);
    }

    public void setBjPlayerView(BJPlayerView bjPlayerView){
        this.bjPlayerView = bjPlayerView;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(bjPlayerView == null || bjPlayerView.getVideoView().getMediaPlayer() == null ||
                bjPlayerView.getVideoView().getMediaPlayer().getPlayState() < IMediaPlayer.PlayerState.STATE_PREPARING){
            return false;
        }
        if (isGestureEnable && mGestureDetector.onTouchEvent(event)) {
            return true;
        }
        if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
            if (mProgressSlideNewPosition - bjPlayerView.getCurrentPosition() != 0 && mProgressSlideNewPosition >= 0) {
                bjPlayerView.seekVideo((int) mProgressSlideNewPosition);
                mCenterView.setVisibility(View.GONE);
            }
            mProgressSlideNewPosition = -1L;
            mVolume = -1;
            brightness = -1;
        }
        return super.onTouchEvent(event);
    }


    /*--------------------- Gesture ------------------------*/
    private class PlayerGestureListener extends GestureDetector.SimpleOnGestureListener {

        private boolean toSeek;
        private boolean volumeControl;
        private boolean firstTouch;

        @Override
        public boolean onDown(MotionEvent e) {
            firstTouch = true;
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if (playerTapListener != null)
                playerTapListener.onDoubleTap(e);
            return super.onDoubleTap(e);
        }


        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if (playerTapListener != null)
                playerTapListener.onSingleTapUp(e);
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            float oldX = e1.getX(), oldY = e1.getY();
            float deltaY = oldY - e2.getY();
            float deltaX = oldX - e2.getX();

            if (firstTouch) {
                toSeek = Math.abs(distanceX) >= Math.abs(distanceY);
                volumeControl = oldX > getWidth() * 0.5f;
                if (enableVolumeCtrl && !enableBrightnessCtrl) {
                    volumeControl = true;
                } else if (!enableVolumeCtrl && enableBrightnessCtrl) {
                    volumeControl = false;
                }
                firstTouch = false;
            }


            if (toSeek) {
                // onProgressSlide
                if (enableSeekCtrl) {
                    onProgressSlide(-deltaX / getWidth());
                }
            } else {
                mCenterView.setVisibility(View.GONE);
                float percent = deltaY / getHeight();
                if (volumeControl) {
                    if (enableVolumeCtrl)
                        onVolumeSlide(percent);
                } else {
                    if (enableBrightnessCtrl)
                        onBrightnessSlide(percent);
                }
            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    }

    private void onProgressSlide(float percent) {
        long position = bjPlayerView.getCurrentPosition();
        long duration = bjPlayerView.getDuration() + 1;
        long deltaMax = Math.min(100, duration - position);
        long delta = (long) (deltaMax * percent);

        long newPosition = delta + position;
        if (newPosition > duration) {
            newPosition = duration;
        } else if (newPosition <= 0) {
            newPosition = 0;
            delta = -position;
        }
        int showDelta = (int) delta;
        mProgressSlideNewPosition = newPosition;

        if (showDelta != 0) {
            showProgressSlide(showDelta);
        }
    }

    private void onBrightnessSlide(float percent) {
        if (brightness < 0) {
            brightness = ((Activity) getContext()).getWindow().getAttributes().screenBrightness;
            if (brightness <= 0.00f) {
                brightness = 0.50f;
            } else if (brightness < 0.01f) {
                brightness = 0.01f;
            }
        }

        WindowManager.LayoutParams lpa = ((Activity) getContext()).getWindow().getAttributes();
        lpa.screenBrightness = brightness + percent;
        if (lpa.screenBrightness > 1.0f) {
            lpa.screenBrightness = 1.0f;
        } else if (lpa.screenBrightness < 0.01f) {
            lpa.screenBrightness = 0.01f;
        }

        showBrightnessSlide((int) (lpa.screenBrightness * 100));
        ((Activity) getContext()).getWindow().setAttributes(lpa);
    }


    private void onVolumeSlide(float percent) {
        if (mVolume == -1) {
            mVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            mVolume = Math.max(0, mVolume);
        }

//        hideControllers(true);

        int index = (int) (percent * mMaxVolume) + mVolume;
        index = Math.min(Math.max(0, index), mMaxVolume);

        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, index, 0);
        showVolumeSlide(index, mMaxVolume);
    }

    private void showProgressSlide(int delta) {
        mCenterView.setVisibility(View.VISIBLE);
        $.id(R.id.bjplayer_center_video_progress_dialog_ll).visible();
        $.id(R.id.bjplayer_center_video_progress_dialog_loading_pb).gone();
        $.id(R.id.bjplayer_center_video_progress_dialog_title_iv).visible();
        $.id(R.id.bjplayer_center_video_progress_dialog_message_tv).visible();
        $.id(R.id.bjplayer_center_video_progress_dialog_buttons_ll).gone();
        $.id(R.id.bjplayer_center_controller_volume_dialog_ll).gone();

        String durationText = Utils.formatDuration(bjPlayerView.getDuration());
        String positionText = Utils.formatDuration(bjPlayerView.getCurrentPosition() + delta, bjPlayerView.getDuration() >= 3600);
        $.id(R.id.bjplayer_center_video_progress_dialog_message_tv).text(String.format("%s/%s", positionText, durationText));

        if (delta > 0) {
            $.id(R.id.bjplayer_center_video_progress_dialog_title_iv).image(R.drawable.bjplayer_ic_kuaijin);
        } else {
            $.id(R.id.bjplayer_center_video_progress_dialog_title_iv).image(R.drawable.bjplayer_ic_huitui);
        }
    }

    public void showBrightnessSlide(int brightness) {
        mCenterView.setVisibility(View.VISIBLE);
        $.id(R.id.bjplayer_center_video_progress_dialog_ll).gone();
        $.id(R.id.bjplayer_center_controller_volume_dialog_ll).visible();

        $.id(R.id.bjplayer_center_controller_volume_ic_iv).image(R.drawable.bjplayer_ic_brightness);
        $.id(R.id.bjplayer_center_controller_volume_tv).text(brightness + "%");
        dismissDialogDelay();
    }

    public void showVolumeSlide(int volume, int maxVolume) {
        mCenterView.setVisibility(View.VISIBLE);
        $.id(R.id.bjplayer_center_video_progress_dialog_ll).gone();
        $.id(R.id.bjplayer_center_controller_volume_dialog_ll).visible();

        int value = volume * 100 / maxVolume;
        if (value == 0) {
            $.id(R.id.bjplayer_center_controller_volume_ic_iv).image(R.drawable.bjplayer_ic_volume_off_white);
            $.id(R.id.bjplayer_center_controller_volume_tv).text("off");
        } else {
            $.id(R.id.bjplayer_center_controller_volume_ic_iv).image(R.drawable.bjplayer_ic_volume_up_white);
            $.id(R.id.bjplayer_center_controller_volume_tv).text(value + "%");
        }
        dismissDialogDelay();
    }

    private void dismissDialogDelay(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mCenterView.setVisibility(View.GONE);
            }
        }, 2000);
    }


    public boolean isGestureEnable() {
        return isGestureEnable;
    }

    public void setGestureEnable(boolean gestureEnable) {
        isGestureEnable = gestureEnable;
    }


    public void setPlayerTapListener(OnPlayerTapListener playerTapListener) {
        this.playerTapListener = playerTapListener;
    }

    public interface OnPlayerTapListener {
        void onSingleTapUp(MotionEvent e);

        void onDoubleTap(MotionEvent e);
    }

    /**
     * 是否允许通过触摸手势控制音量
     */
    public void enableVolumeGesture(boolean enable) {
        enableVolumeCtrl = enable;
    }

    /**
     * 是否允许通过触摸手势控制亮度
     */
    public void enableBrightnessGesture(boolean enable) {
        enableBrightnessCtrl = enable;
    }

    /**
     * 是否允许通过触摸手势拖拽
     */
    public void enableSeekGesture(boolean enable) {
        enableSeekCtrl = enable;
    }


}
