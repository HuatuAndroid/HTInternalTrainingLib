package com.jungan.www.module_blackplay.activity;

/**
 * Created by wangkangfei on 17/8/17.
 */

public interface PBRouterListener {
    /**
     * 选择清晰度
     */
    void showChoseDefinitionDlg();

    /**
     * 选择播放倍速
     */
    void showChoseRateDlg();

    /**
     * 切换横屏竖屏
     */
    boolean changeOrientation();

    void selectDefinition(String type, int position);

    /**
     * 显示聊天的大图
     * @param url
     */
    void showBigChatPic(String url);

    void realSaveBmpToFile(byte[] bmpArray);

    void showSavePicDialog(byte[] bmpArray);

}
