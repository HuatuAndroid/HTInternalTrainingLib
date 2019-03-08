package com.baijiayun.videoplayer.ui.playback.chat.preview;

import android.graphics.Bitmap;

/**
 * Created by yongjiaming on 2018/9/11 16:02
 */
public interface IChatMessageCallback {

    void showSaveImageDialog(Bitmap bitmap);

    void saveImage(Bitmap bitmap);

    void displayImage(String imageUrl);

}
