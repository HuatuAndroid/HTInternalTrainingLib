package com.baijiayun.videoplayer.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.baijiayun.BJYPlayerSDK;
import com.baijiayun.playback.PBRoom;
import com.baijiayun.playback.context.LPError;
import com.baijiayun.playback.mocklive.LPLaunchListener;
import com.baijiayun.playback.ppt.WhiteboardView;
import com.baijiayun.playback.util.DisplayUtils;
import com.baijiayun.playback.util.LPObservable;
import com.baijiayun.playback.util.LPRxUtils;
import com.baijiayun.videoplayer.IBJYVideoPlayer;
import com.baijiayun.videoplayer.VideoPlayerFactory;
import com.baijiayun.videoplayer.event.BundlePool;
import com.baijiayun.videoplayer.ui.R;
import com.baijiayun.videoplayer.ui.event.UIEventKey;
import com.baijiayun.videoplayer.ui.playback.chat.PBChatFragment;
import com.baijiayun.videoplayer.ui.playback.chat.preview.ChatPictureViewFragment;
import com.baijiayun.videoplayer.ui.playback.chat.preview.ChatSavePicDialogFragment;
import com.baijiayun.videoplayer.ui.playback.chat.preview.IChatMessageCallback;
import com.baijiayun.videoplayer.ui.playback.viewsupport.AutoExitDrawerLayout;
import com.baijiayun.videoplayer.ui.playback.viewsupport.DragFrameLayout;
import com.baijiayun.videoplayer.ui.utils.ConstantUtil;
import com.baijiayun.videoplayer.ui.widget.BJYPlaybackContainer;
import com.baijiayun.videoplayer.ui.widget.BJYVideoView;
import com.baijiayun.videoplayer.util.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.R.attr.path;
import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

/**
 * 回放界面
 * Created by yongjiaming on 2018/8/13
 */
public class PBRoomActivity extends BaseActivity implements IChatMessageCallback {
    private static final String CHAT_FRAGMENT_TAG = "CHAT_FRAGMENT_TAG";
    //播放器
    private IBJYVideoPlayer videoPlayer;
    private PBRoom pbRoom;

    private MaterialDialog launchStepDlg;
    //ppt控件
    private WhiteboardView whiteboardView;
    //视频播放器控件
    private BJYVideoView bjyVideoView;
    //ppt默认容器，可供PPT和视频切换
    private BJYPlaybackContainer bigContainer;
    //可拖拽的FrameLayout
    private DragFrameLayout dragFrameLayout;
    //聊天消息fragment
    private PBChatFragment chatFragment;
    //聊天抽屉布局
    private AutoExitDrawerLayout chatDrawerLayout;
    //ppt和视频切换按钮
    private ImageView switchIv;
    //聊天抽屉隐藏按钮
    private ImageView chatSwitchIv;

    private boolean hasLaunchSuccess = false;
    private boolean isVideoInDragFrameLayout = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // 5.0+ 打开硬件加速
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        }
        if (savedInstanceState != null) {
            String FRAGMENTS_TAG = "android:support:fragments";
            savedInstanceState.remove(FRAGMENTS_TAG);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playback);
        initView();
        //允许PPT缩放手势（同时关闭了音量、亮度调节）
        setPPTScaleEnable(true);
        initListener();
        initRoom();
    }

    private void initView() {
        dragFrameLayout = findViewById(R.id.drag_framelayout);
        bigContainer = findViewById(R.id.fl_pb_container_big);
        switchIv = findViewById(R.id.switch_iv);
        chatSwitchIv = findViewById(R.id.iv_pb_chat_switch);

        chatDrawerLayout = findViewById(R.id.dl_pb_chat);
        chatDrawerLayout.openDrawer(Gravity.START);
        chatDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);

        whiteboardView = new WhiteboardView(this);
        //设置ppt背景色
        whiteboardView.setBackgroundColor(ContextCompat.getColor(this, R.color.lp_ppt_bg));

        bjyVideoView = findViewById(R.id.pb_bjy_videoview);
        launchStepDlg = new MaterialDialog.Builder(this)
                .content("正在加载...")
                .progress(true, 100, false)
                .cancelable(true)
                .build();
        //修复某些机器上surfaceView导致的闪黑屏的bug
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
    }

    private void initListener() {
        switchIv.setOnClickListener(v -> {
            dragFrameLayout.setVisibility(View.VISIBLE);
            switchIv.setVisibility(View.GONE);
        });

        whiteboardView.setOnViewTapListener((view, x, y) -> {
            bigContainer.sendCustomEvent(UIEventKey.CUSTOM_CODE_TAP_PPT, null);
        });

        dragFrameLayout.setOnClickListener(v -> {
            if (!hasLaunchSuccess) {
                return;
            }
            List<String> options = new ArrayList<>();
            options.add(getString(R.string.full_screen));
            options.add(getString(R.string.close));
            options.add(getString(R.string.cancel));

            new MaterialDialog.Builder(PBRoomActivity.this)
                    .items(options)
                    .itemsCallback((materialDialog, view, i, charSequence) -> {
                        if (i == 0) {
                            switchPPTAndVideo();
                        } else if (i == 1) {
                            dragFrameLayout.setVisibility(View.INVISIBLE);
                            switchIv.setVisibility(View.VISIBLE);
                            switchIv.setBackgroundResource(isVideoInDragFrameLayout ? R.drawable.ic_video_back_stopvideo : R.drawable.ic_video_back_ppt);
                        } else {

                        }
                        materialDialog.dismiss();
                    })
                    .show();
        });

        bigContainer.setComponentEventListener((eventCode, bundle) -> {
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

        chatSwitchIv.setOnClickListener(v -> {
            if (chatDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                chatDrawerLayout.closeDrawers();
                chatSwitchIv.setImageResource(R.drawable.ic_video_back_sentmsg_no_on);
            } else {
                chatDrawerLayout.openDrawer(Gravity.LEFT);
                chatSwitchIv.setImageResource(R.drawable.ic_video_back_sentmsg_no);
            }
        });

        chatDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                chatSwitchIv.setImageResource(R.drawable.ic_video_back_sentmsg_no);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                chatSwitchIv.setImageResource(R.drawable.ic_video_back_sentmsg_no_on);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    private void initRoom() {
        initData();
        //初始化播放器
        videoPlayer = new VideoPlayerFactory.Builder()
                //后台暂停播放
                .setSupportBackgroundAudio(false)
                //开启循环播放
                .setSupportLooping(true)
                //开启记忆播放
                .setSupportBreakPointPlay(true, this)
                //绑定activity生命周期
                .setLifecycle(getLifecycle()).build();

        bjyVideoView.initPlayer(videoPlayer, false);
        //pbRoom持有videoplayer引用
        pbRoom.bindPlayer(videoPlayer);
        //视频&ppt容器持有pbRoom
        bigContainer.attachPBRoom(pbRoom);
        //ppt view持有
        whiteboardView.attachPBRoom(pbRoom);
        if(pbRoom.isPlayBackOffline() || bigContainer.checkNetState()){
            launchStepDlg.show();
            enterRoom();
        }
        bigContainer.setRetryEnterRoomCallback(() -> enterRoom());
    }
    //0普通大班课回放，1大班课webrtc回放，2小班课webrtc回放
    private int recordType = 0;

    private void enterRoom(){
        pbRoom.enterRoom(new LPLaunchListener() {
            @Override
            public void onLaunchSteps(int step, int totalStep) {
                if (launchStepDlg == null) {
                    return;
                }
                //launchStepDlg.setContent("enter room:" + step * 100 / totalStep + "%");
            }

            @Override
            public void onLaunchError(LPError error) {
                if (launchStepDlg != null) {
                    launchStepDlg.dismiss();
                }
                Toast.makeText(PBRoomActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                bigContainer.sendCustomEvent(UIEventKey.CUSTOM_CODE_ENTER_ROOM_ERROR, null);
            }

            @Override
            public void onLaunchSuccess(PBRoom room) {
                hasLaunchSuccess = true;
                if (launchStepDlg != null) {
                    launchStepDlg.dismiss();
                }
                if(room.isPlayBackOffline()){
                    recordType = getIntent().getIntExtra(ConstantUtil.PB_ROOM_RECORD_TYPE, 0);
                } else{
                    recordType = room.getRecordType();
                }
                //除了webrtc大班课以外的其他webrtc课型（双师，小班课等）
                if(recordType == 2){
                    whiteboardView.destroy();
                    whiteboardView = null;
                    View videoView = dragFrameLayout.getChildAt(0);
                    dragFrameLayout.removeAllViews();
                    dragFrameLayout.setVisibility(View.GONE);
                    bigContainer.addView(videoView, 0);
                    bigContainer.setGestureEnable(true);
                    PBRoomActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                } else{
                    bigContainer.addPPTView(whiteboardView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    bigContainer.setGestureEnable(true);
                    chatDrawerLayout.setVisibility(View.VISIBLE);
                    addChatFragment();
                }
            }
        });
    }

    private void initData(){
        String roomId, token, sessionId;
        if (getIntent().getStringExtra(ConstantUtil.PB_ROOM_SESSION_ID) == null) {
            String videoFilePath = getIntent().getStringExtra(ConstantUtil.PB_ROOM_VIDEOFILE_PATH);
            String signalFilePath = getIntent().getStringExtra(ConstantUtil.PB_ROOM_SIGNALFILE_PATH);
            pbRoom = BJYPlayerSDK.newPlayBackRoom(this, videoFilePath, signalFilePath);
        } else {
            roomId = getIntent().getStringExtra(ConstantUtil.PB_ROOM_ID);
            token = getIntent().getStringExtra(ConstantUtil.PB_ROOM_TOKEN);
            sessionId = getIntent().getStringExtra(ConstantUtil.PB_ROOM_SESSION_ID);
            pbRoom = BJYPlayerSDK.newPlayBackRoom(this, Long.valueOf(roomId), Long.valueOf(sessionId), token);

        }
    }

    private void addChatFragment() {
        chatFragment = new PBChatFragment();
        chatFragment.setRoom(pbRoom);
        addFragment(R.id.fl_pb_chat_content_container, chatFragment, false, CHAT_FRAGMENT_TAG);
    }

    private void switchPPTAndVideo() {
        isVideoInDragFrameLayout = !isVideoInDragFrameLayout;
        View bigView = bigContainer.getChildAt(0);
        View smallView = dragFrameLayout.getChildAt(0);
        bigContainer.removeView(bigView);
        dragFrameLayout.removeView(smallView);
        bigContainer.addView(smallView, 0);
        dragFrameLayout.addView(bigView, 0);
        setPPTScaleEnable(isVideoInDragFrameLayout);
    }

    /**
     * 横竖屏切换重新layout布局
     *
     * @param isLandscape
     */
    @Override
    protected void requestLayout(boolean isLandscape) {
        super.requestLayout(isLandscape);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) bigContainer.getLayoutParams();
        if (isLandscape) {
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        } else {
            layoutParams.width = Utils.getScreenWidthPixels(this);
            layoutParams.height = layoutParams.width * 9 / 16;
        }
        bigContainer.setLayoutParams(layoutParams);
        bigContainer.sendCustomEvent(UIEventKey.CUSTOM_CODE_REQUEST_TOGGLE_SCREEN, BundlePool.obtain(isLandscape));
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        doOnChatDrawerConfigurationChanged(newConfig);
        doOnDragContainerConfigurationChanged(newConfig);
        dragFrameLayout.configurationChanged();
    }

    /**
     * 聊天drawer
     */
    private void doOnChatDrawerConfigurationChanged(Configuration newConfig) {
        RelativeLayout.LayoutParams lpChatDrawer = (RelativeLayout.LayoutParams) chatDrawerLayout.getLayoutParams();
        if (newConfig.orientation == ORIENTATION_LANDSCAPE) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            chatDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            lpChatDrawer.width = DisplayUtils.dip2px(this, 300);
            lpChatDrawer.height = ViewGroup.LayoutParams.MATCH_PARENT;
            lpChatDrawer.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            lpChatDrawer.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            lpChatDrawer.addRule(RelativeLayout.ABOVE, R.id.iv_pb_chat_switch);
            lpChatDrawer.topMargin = DisplayUtils.dip2px(this, 30);
            if(recordType != 2){
                chatSwitchIv.setVisibility(View.VISIBLE);
            }
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            chatDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
            lpChatDrawer.width = ViewGroup.LayoutParams.MATCH_PARENT;
            lpChatDrawer.height = ViewGroup.LayoutParams.MATCH_PARENT;
            //subject==0,清除此条rule
            lpChatDrawer.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            lpChatDrawer.addRule(RelativeLayout.BELOW, R.id.fl_pb_container_big);
            lpChatDrawer.topMargin = DisplayUtils.dip2px(this, 0);
            chatSwitchIv.setVisibility(View.GONE);
        }
        chatDrawerLayout.setLayoutParams(lpChatDrawer);
    }

    /**
     * 重定位可拖拽窗口
     *
     * @param newConfig
     */
    private void doOnDragContainerConfigurationChanged(Configuration newConfig) {
        RelativeLayout.LayoutParams lpSmallContainer = new RelativeLayout.LayoutParams(DisplayUtils.dip2px(this, 150), DisplayUtils.dip2px(this, 90));
        if (newConfig.orientation == ORIENTATION_LANDSCAPE) {
            lpSmallContainer.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            lpSmallContainer.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        } else {
            lpSmallContainer.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            lpSmallContainer.addRule(RelativeLayout.BELOW, R.id.fl_pb_container_big);
        }
        dragFrameLayout.setLayoutParams(lpSmallContainer);
    }

    /********************************聊天message图片相关回调*************************************/
    @Override
    public void showSaveImageDialog(Bitmap bitmap) {
        ChatSavePicDialogFragment fragment = ChatSavePicDialogFragment.newInstance(bitmap);
        fragment.setChatMsgCallback(this);
        showDialogFragment(fragment);
    }

    private Disposable saveImageDisposable;

    @Override
    public void saveImage(Bitmap bitmap) {
        saveImageDisposable = LPObservable.create((ObservableOnSubscribe<String>) emitter -> {
            // 首先保存图片
            File appDir = new File(Environment.getExternalStorageDirectory(), "bjhl_lp_image");
            if (!appDir.exists()) {
                appDir.mkdir();
            }
            String fileName = System.currentTimeMillis() + ".jpg";
            File file = new File(appDir, fileName);
            String picPath = file.getAbsolutePath();
            try {
                FileOutputStream fos = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 其次把文件插入到系统图库
            try {
                MediaStore.Images.Media.insertImage(getContentResolver(),
                        file.getAbsolutePath(), fileName, null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // 最后通知图库更新
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
            emitter.onNext(picPath);
            emitter.onComplete();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(bitmapSavePath -> {
            Toast.makeText(PBRoomActivity.this, "图片保存在" + bitmapSavePath, Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public void displayImage(String imageUrl) {
        ChatPictureViewFragment fragment = ChatPictureViewFragment.newInstance(imageUrl);
        fragment.setChatMsgCallback(this);
        showDialogFragment(fragment);
    }

    /**
     * 设置ppt是否支持手势缩放效果
     * @param scaleEnable
     */
    private void setPPTScaleEnable(boolean scaleEnable){
        bigContainer.setGestureEnable(!scaleEnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(whiteboardView != null){
            whiteboardView.destroy();
        }
        if (pbRoom != null) {
            pbRoom.quitRoom();
        }
        if(videoPlayer != null){
            videoPlayer.release();
        }
        bigContainer.onDestroy();
        LPRxUtils.dispose(saveImageDisposable);
    }

    @Override
    public void onBackPressed() {
        if(recordType != 2){
            super.onBackPressed();
        } else{
            onBackPressedExitImmediately();
        }
    }
}
