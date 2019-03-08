package com.baijiayun.videoplayer.ui.playback.chat.preview;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;

import com.baijiayun.playback.util.DisplayUtils;
import com.baijiayun.videoplayer.ui.R;
import com.baijiayun.videoplayer.ui.playback.BaseDialogFragment;
import com.baijiayun.videoplayer.ui.utils.QueryPlus;


/**
 * Created by wangkangfei on 17/5/13.
 */

public class ChatSavePicDialogFragment extends BaseDialogFragment {

    private QueryPlus $;
    private IChatMessageCallback callback;
    private Bitmap bitmap = null;

    public static ChatSavePicDialogFragment newInstance(Bitmap bitmap) {
        Bundle args = new Bundle();
        args.putParcelable("bitmap", bitmap);
        ChatSavePicDialogFragment fragment = new ChatSavePicDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_chat_save_pic;
    }

    @Override
    public void onStart() {
        contentBackgroundColor(android.R.color.transparent);
        super.onStart();
    }

    @Override
    protected void init(Bundle savedInstanceState, Bundle arguments) {
        hideTitleBar();
        if (getArguments() != null) {
            bitmap = getArguments().getParcelable("bitmap");
        }
        $ = QueryPlus.with(contentView);
        $.id(R.id.dialog_save_pic).clicked(v -> {
            if(bitmap != null){
                callback.saveImage(bitmap);
            }
            dismissAllowingStateLoss();
        });
        $.id(R.id.dialog_save_pic_cancel).clicked(v -> dismissAllowingStateLoss());
    }


    @Override
    protected void setWindowParams(WindowManager.LayoutParams windowParams) {
        int screenWidth = DisplayUtils.getScreenWidthPixels(getContext());
        int screenHeight = DisplayUtils.getScreenHeightPixels(getContext());
        windowParams.width = Math.min(screenHeight, screenWidth);
        windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        windowParams.gravity = Gravity.BOTTOM | Gravity.CENTER;
        windowParams.x = 0;
        windowParams.y = 0;

        windowParams.windowAnimations = R.style.LiveBaseSendMsgDialogAnim;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        $ = null;
        callback = null;
    }

    public void setChatMsgCallback(IChatMessageCallback callback){
        this.callback = callback;
    }
}
