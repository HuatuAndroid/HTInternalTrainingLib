package com.baijiayun.videoplayer.ui.playback;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.baijiayun.playback.context.PBConstants;
import com.baijiayun.videoplayer.ui.activity.PBRoomActivity;
import com.baijiayun.videoplayer.ui.utils.ConstantUtil;


/**
 * Created by wangkangfei on 17/8/15.
 */

public class PBRoomUI {
    /**
     * 进入回放标准UI界面
     *
     * @param context                     Activity
     * @param roomId                      房间id
     * @param roomToken                   token
     * @param sessionId                   sessionId 长期课才需要填，非长期课默认传-1
     * @param onEnterPBRoomFailedListener 进房间错误监听，可为null
     */
    public static void enterPBRoom(Context context, String roomId, String roomToken, String sessionId,
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
        context.startActivity(intent);
    }

    /**
     * 进入离线回放标准UI界面
     * @param context                      activity
     * @param videoPath                    视频路径
     * @param signalPath                   信令路径
     * @param onEnterPBRoomFailedListener  进房间错误监听，可为null
     */
    public static void enterLocalPBRoom(Context context, String videoPath, String signalPath,
                                   OnEnterPBRoomFailedListener onEnterPBRoomFailedListener) {
       enterLocalPBRoom(context, videoPath, signalPath, 0, onEnterPBRoomFailedListener);
    }

    /**
     * 进入离线回放标准UI界面
     * @param context                      activity
     * @param videoPath                    视频路径
     * @param signalPath                   信令路径
     * @param recordType                   录制类型
     * @param onEnterPBRoomFailedListener  进房间错误监听，可为null
     */
    public static void enterLocalPBRoom(Context context, String videoPath, String signalPath, int recordType,
                                        OnEnterPBRoomFailedListener onEnterPBRoomFailedListener) {
        if (!(context instanceof Activity)) {
            if (onEnterPBRoomFailedListener != null) {
                onEnterPBRoomFailedListener.onEnterPBRoomFailed("Please pass the context of an activity");
            }
            return;
        }

        Intent intent = new Intent(context, PBRoomActivity.class);
        intent.putExtra(ConstantUtil.PB_ROOM_VIDEOFILE_PATH, videoPath);
        intent.putExtra(ConstantUtil.PB_ROOM_SIGNALFILE_PATH, signalPath);
        intent.putExtra(ConstantUtil.PB_ROOM_RECORD_TYPE, recordType);
        context.startActivity(intent);
    }

    public interface OnEnterPBRoomFailedListener {
        void onEnterPBRoomFailed(String msg);
    }
}
