package com.baijiayun.videoplayer.ui.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.baijiayun.videoplayer.VideoPlayerFactory;
import com.baijiayun.videoplayer.event.BundlePool;
import com.baijiayun.videoplayer.ui.R;
import com.baijiayun.videoplayer.ui.event.UIEventKey;
import com.baijiayun.videoplayer.ui.widget.BJYVideoView;
import com.baijiayun.videoplayer.util.Utils;

/**
 * 点播播放activity
 */
public class VideoPlayActivity extends BaseActivity {

    private BJYVideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoplay);

        videoView = findViewById(R.id.bjyvideoview);
        videoView.initPlayer(new VideoPlayerFactory.Builder()
                //后台暂停播放
                .setSupportBackgroundAudio(false)
                //开启循环播放
                .setSupportLooping(true)
                //开启记忆播放
                .setSupportBreakPointPlay(true, this)
                //绑定activity生命周期
                .setLifecycle(getLifecycle()).build()
        );


        videoView.setComponentEventListener((eventCode, bundle) -> {
            switch (eventCode) {
                case UIEventKey.CUSTOM_CODE_REQUEST_BACK:
                    if (isLandscape) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    } else {
                        finish();
                    }
                    break;
                case UIEventKey.CUSTOM_CODE_REQUEST_TOGGLE_SCREEN:
                    setRequestedOrientation(isLandscape ?
                            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT :
                            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    break;
                default:
                    break;
            }
        });
        String isOnLine = getIntent().getStringExtra("isOnLine");
        if("2".equals(isOnLine)){
            //播放本地视频
            videoView.setupLocalVideoWithFilePath(getIntent().getStringExtra("Filepath"));
        } else{
            videoView.setupOnlineVideoWithId(getIntent().getLongExtra("videoId", 0L), getIntent().getStringExtra("Token"), true);
        }
    }

    @Override
    protected void requestLayout(boolean isLandscape) {
        super.requestLayout(isLandscape);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) videoView.getLayoutParams();
        if (isLandscape) {
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        } else {
            layoutParams.width = Utils.getScreenWidthPixels(this);
            layoutParams.height = layoutParams.width * 9 / 16;
        }
        videoView.setLayoutParams(layoutParams);
        videoView.sendCustomEvent(UIEventKey.CUSTOM_CODE_REQUEST_TOGGLE_SCREEN, BundlePool.obtain(isLandscape));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoView.onDestroy();
    }
}
