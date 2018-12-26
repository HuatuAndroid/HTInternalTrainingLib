package com.jungan.www.module_playvideo.ui;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.baijiahulian.common.networkv2.HttpException;
import com.baijiahulian.player.BJPlayerView;
import com.baijiahulian.player.OnPlayerViewListener;
import com.baijiahulian.player.bean.SectionItem;
import com.baijiahulian.player.bean.VideoItem;
import com.baijiahulian.player.playerview.BJCenterViewPresenter;
import com.baijiahulian.player.playerview.IPlayerTopContact;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Locale;

import com.jungan.www.module_playvideo.R;
import com.jungan.www.module_playvideo.event.BjyPlayTimeEvent;
import com.jungan.www.module_playvideo.view.BJBottomViewPresenterCopy;
import com.jungan.www.module_playvideo.view.BJTopViewPresenterCopy;
import com.wb.rxbus.taskBean.RxBus;

@Route(path = "/playvideo/open")
public class PlayVodActivity extends AppCompatActivity {
    private BJPlayerView playerView;
    private long videoId;
    private String Token;
    private String isOnLine;
    private String BJYID;
    private String Filepath;
    private BJBottomViewPresenterCopy bottomViewPresenterCopy;
    private BJTopViewPresenterCopy bjTopViewPresenterCopy;

    // KaelLi, 2018/12/18
    private long beginTime;
    private long endTime;
    private int beginVideoPosition;
    private int currentVideoPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_vod);

        Token=getIntent().getStringExtra("token");
        BJYID=getIntent().getStringExtra("bjyId");
//        chId=getIntent().getStringExtra("chId");
        try {
            isOnLine=getIntent().getStringExtra("isOnLine");
        }catch (Exception e){
            isOnLine="1";
        }
        if(isOnLine.equals("1")){
            videoId=getIntent().getLongExtra("videoId",0L);
        }else {
            Filepath=getIntent().getStringExtra("Filepath");
        }
        if(isOnLine==null||isOnLine.equals("")){
            isOnLine="1";
        }
        //设置点播下载的服务器环境，默认值正式环境
        playerView = (BJPlayerView) findViewById(R.id.videoView);
        bottomViewPresenterCopy = new BJBottomViewPresenterCopy(playerView.getBottomView());
        playerView.setBottomPresenter(bottomViewPresenterCopy);

        bjTopViewPresenterCopy=new BJTopViewPresenterCopy(playerView.getTopView(),PlayVodActivity.this);
        playerView.setTopPresenter(bjTopViewPresenterCopy);
        final BJCenterViewPresenter centerpresenter = new BJCenterViewPresenter(playerView.getCenterView());
        centerpresenter.setRightMenuHidden(false);
        playerView.setCenterPresenter(centerpresenter);
        playerView.initPartner(Long.parseLong(BJYID), BJPlayerView.PLAYER_DEPLOY_ONLINE, 1);
        playerView.setHeadTailPlayMethod(BJPlayerView.HEAD_TAIL_PLAY_NONE);
        playerView.setVideoEdgePaddingColor(Color.argb(255, 0, 0, 0));
        // KaelLi, 2018/12/18
        playerView.setMemoryPlayEnable(true);
        playerView.setOnPlayerViewListener(new OnPlayerViewListener() {
            @Override
            public void onVideoInfoInitialized(BJPlayerView playerView, HttpException exception) {
                //TODO: 视频信息初始化结束
                if (exception != null) {
                    // 视频信息初始化成功
                    VideoItem videoItem = playerView.getVideoItem();
                }
            }

            @Override
            public void onPause(BJPlayerView playerView) {
                //TODO: video暂停
            }

            @Override
            public void onPlay(BJPlayerView playerView) {
                //TODO: 开始播放
                Log.d("kaelli", "onPlay");
            }

            @Override
            public void onError(BJPlayerView playerView, int code) {
                //TODO: 播放出错
            }

            @Override
            public void onUpdatePosition(BJPlayerView playerView, int position) {
                //TODO: 播放过程中更新播放位置
                if (beginVideoPosition <= 0)
                    beginVideoPosition = position;
                currentVideoPosition = position;
                if (beginTime <= 0)
                    beginTime = System.currentTimeMillis();
                Log.d("kaelli", "onUpdatePosition :"+position);
            }

            @Override
            public void onSeekComplete(BJPlayerView playerView, int position) {
                //TODO: 拖动进度条
            }

            @Override
            public void onSpeedUp(BJPlayerView playerView, float speedUp) {
                //TODO: 设置倍速播放
            }

            @Override
            public void onVideoDefinition(BJPlayerView playerView, int definition) {
                //TODO: 设置清晰度完成
            }

            @Override
            public void onPlayCompleted(BJPlayerView playerView, VideoItem item, SectionItem nextSection) {
                //TODO: 当前视频播放完成 [nextSection已被废弃，请勿使用]

            }

            @Override
            public void onVideoPrepared(BJPlayerView playerView) {
                //TODO: 准备好了，马上要播放
                // 可以在这时获取视频时长
                playerView.getDuration();
            }

            @Override
            public void onCaton(BJPlayerView playerView) {
                //TODO 视频播放卡顿，卡住超过3秒。可以在此处提示正在缓冲数据
            }

            @Override
            public String getVideoTokenWhenInvalid() {
                //TODO 视频token出错，需要集成方重新获取并传入BJPlayerview。
                return "test12345678";
            }
        });
        if(isOnLine.equals("1")){
            OnLineVideo();
        }else {
            OffLineVideo();
        }

    }
    private void OnLineVideo(){
//        long vId = Long.valueOf(videoId);
//        playerView.setVideoId(vId, Token);
//        int pos = 0;
//        playerView.playVideo(pos);
//        getWindow().setFormat(PixelFormat.TRANSLUCENT);
//        playerView.switchOrientation();
//        IPlayerTopContact.TopView topView=playerView.getTopViewPresenter();
//        topView.setOnBackClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        long vId=0;
        try {
            vId = Long.valueOf(videoId);
        }catch (Exception e){
            vId=0;
        }
        playerView.setVideoId(vId, Token);
        playerView.getTopViewPresenter().setOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        int pos = 0;
        playerView.playVideo(pos);
        playerView.switchOrientation();
        IPlayerTopContact.TopView topView=playerView.getTopViewPresenter();
        topView.setOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void OffLineVideo(){
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = new File(Filepath);
            if (file.exists()) {
                //进度条进度归零
                bottomViewPresenterCopy.setCurrentPosition(0);
                playerView.setVideoPath(Filepath);
                playerView.getTopViewPresenter().setOnBackClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                });
                getWindow().setFormat(PixelFormat.TRANSLUCENT);
                int pos = 0;
                playerView.playVideo(pos);
                playerView.switchOrientation();
                IPlayerTopContact.TopView topView=playerView.getTopViewPresenter();
                topView.setOnBackClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
                topView.setTitle(Token);
            } else {
                Toast.makeText(PlayVodActivity.this, videoId + "不存在的", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(PlayVodActivity.this, "找不到存储卡！", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (playerView != null) {
            playerView.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public void onBackPressed() {
        if (!playerView.onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (playerView != null) {
            playerView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (playerView != null) {
            playerView.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (playerView != null) {
            playerView.onDestroy();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
        endTime = System.currentTimeMillis();
        RxBus.getIntanceBus().post(new BjyPlayTimeEvent(videoId, simpleDateFormat.format(beginTime), simpleDateFormat.format(endTime), (currentVideoPosition - beginVideoPosition), currentVideoPosition));
    }
    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK){
            Log.e("用户点击了返回","------>>>");
            bjTopViewPresenterCopy.finsnA(PlayVodActivity.this);
            return true;//不执行父类点击事件
        }
        return super.onKeyDown(keyCode, event);//继续执行父类其他点击事件
    }
}