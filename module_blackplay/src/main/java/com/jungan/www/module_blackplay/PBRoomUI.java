package com.jungan.www.module_blackplay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.jungan.www.module_blackplay.activity.PBRoomActivity;
import com.jungan.www.module_blackplay.utils.ConstantUtil;
import com.baijiahulian.livecore.context.LPConstants;

/**
 * Created by wangkangfei on 17/8/15.
 */

public class PBRoomUI {

    /**
     * 默认正式环境
     */
    public static void enterPBRoom(Context context, String roomId, String roomToken, String sessionId, OnEnterPBRoomFailedListener onEnterPBRoomFailedListener) {
        enterPBRoom(context, roomId, roomToken, sessionId, LPConstants.LPDeployType.Product, onEnterPBRoomFailedListener);
    }

    /**
     * 进入回放标准UI界面
     *
     * @param context                     Activity
     * @param roomId                      房间id
     * @param roomToken                   token
     * @param sessionId                   sessionId 长期课才需要填，非长期课默认传-1
     * @param deployType                  服务器环境(默认正式服，客户集成时可以不传此参数)
     * @param onEnterPBRoomFailedListener 进房间错误监听，可为null
     */
    public static void enterPBRoom(Context context, String roomId, String roomToken, String sessionId, LPConstants.LPDeployType deployType,
                                   OnEnterPBRoomFailedListener onEnterPBRoomFailedListener) {
        if (!(context instanceof Activity)) {
            if (onEnterPBRoomFailedListener != null) {
                onEnterPBRoomFailedListener.onEnterPBRoomFailed("Please pass the context of an activity");
            }
            return;
        }
        try {
            if (Long.valueOf(roomId) <= 0) {
                if (onEnterPBRoomFailedListener != null) {
                    onEnterPBRoomFailedListener.onEnterPBRoomFailed("invalid room id");
                }
                return;
            }
        } catch (Exception e) {
            if (onEnterPBRoomFailedListener != null) {
                onEnterPBRoomFailedListener.onEnterPBRoomFailed("invalid room id");
            }
            return;
        }

        if (TextUtils.isEmpty(roomToken)) {
            if (onEnterPBRoomFailedListener != null) {
                onEnterPBRoomFailedListener.onEnterPBRoomFailed("invalid room token");
            }
            return;
        }

        Intent intent = new Intent(context, PBRoomActivity.class);
        intent.putExtra(ConstantUtil.PB_ROOM_ID, roomId);
        intent.putExtra(ConstantUtil.PB_ROOM_TOKEN, roomToken);
        intent.putExtra(ConstantUtil.PB_ROOM_SESSION_ID, sessionId);
        intent.putExtra(ConstantUtil.PB_ROOM_DEPLOY, deployType.getType());
        context.startActivity(intent);
    }

    public static void enterLocalPBRoom(Context context, String roomId, String videoPath, String signalPath, LPConstants.LPDeployType deployType,
                                   OnEnterPBRoomFailedListener onEnterPBRoomFailedListener) {
        if (!(context instanceof Activity)) {
            if (onEnterPBRoomFailedListener != null) {
                onEnterPBRoomFailedListener.onEnterPBRoomFailed("Please pass the context of an activity");
            }
            return;
        }
        try {
            if (Long.valueOf(roomId) <= 0) {
                if (onEnterPBRoomFailedListener != null) {
                    onEnterPBRoomFailedListener.onEnterPBRoomFailed("invalid room id");
                }
                return;
            }
        } catch (Exception e) {
            if (onEnterPBRoomFailedListener != null) {
                onEnterPBRoomFailedListener.onEnterPBRoomFailed("invalid room id");
            }
            return;
        }


        Intent intent = new Intent(context, PBRoomActivity.class);
        intent.putExtra(ConstantUtil.PB_ROOM_ID, roomId);
        intent.putExtra(ConstantUtil.PB_ROOM_VIDEOFILE_PATH, videoPath);
        intent.putExtra(ConstantUtil.PB_ROOM_SIGNALFILE_PATH, signalPath);
        intent.putExtra(ConstantUtil.PB_ROOM_DEPLOY, deployType.getType());
        context.startActivity(intent);
    }

    public interface OnEnterPBRoomFailedListener {
        void onEnterPBRoomFailed(String msg);
    }
}
