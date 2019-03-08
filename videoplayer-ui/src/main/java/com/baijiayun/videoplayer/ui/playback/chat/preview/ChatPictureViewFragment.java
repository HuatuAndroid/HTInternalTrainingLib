package com.baijiayun.videoplayer.ui.playback.chat.preview;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.baijiayun.glide.Glide;
import com.baijiayun.glide.load.DataSource;
import com.baijiayun.glide.load.engine.GlideException;
import com.baijiayun.glide.request.RequestListener;
import com.baijiayun.glide.request.target.Target;
import com.baijiayun.playback.ppt.util.AliCloudImageUtil;
import com.baijiayun.playback.util.DisplayUtils;
import com.baijiayun.videoplayer.ui.R;
import com.baijiayun.videoplayer.ui.playback.BaseDialogFragment;

/**
 * Created by Shubo on 2017/3/23.
 */

public class ChatPictureViewFragment extends BaseDialogFragment  {

    private ImageView imageView;
    private TextView tvLoading;
    private IChatMessageCallback callback;

    public static ChatPictureViewFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putString("url", url);
        ChatPictureViewFragment fragment = new ChatPictureViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_big_picture;
    }

    @Override
    protected void init(Bundle savedInstanceState, Bundle arguments) {
        super.hideBackground().contentBackgroundColor(ContextCompat.getColor(getContext(), R.color.live_transparent));
        String url = arguments.getString("url");
        imageView =  contentView.findViewById(R.id.lp_dialog_big_picture_img);
        tvLoading =  contentView.findViewById(R.id.lp_dialog_big_picture_loading_label);

        Glide.with(getContext())
                .load(AliCloudImageUtil.getScaledUrl(url, AliCloudImageUtil.SCALED_MFIT, DisplayUtils.getScreenWidthPixels(getContext()), DisplayUtils.getScreenHeightPixels(getContext())))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        tvLoading.setText(getString(R.string.live_image_loading_fail));
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        tvLoading.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(imageView);
        imageView.setOnClickListener(v -> dismissAllowingStateLoss());
        imageView.setOnLongClickListener(v -> {
            callback.showSaveImageDialog(((BitmapDrawable) imageView.getDrawable()).getBitmap());
            return true;
        });
        contentView.setOnClickListener(v -> dismissAllowingStateLoss());
    }

    @Override
    protected void setWindowParams(WindowManager.LayoutParams windowParams) {
        windowParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        windowParams.dimAmount = 0.85f;
//        windowParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        windowParams.windowAnimations = R.style.ViewBigPicAnim;
    }

    public void setChatMsgCallback(IChatMessageCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(callback != null){
            callback = null;
        }
    }
}
