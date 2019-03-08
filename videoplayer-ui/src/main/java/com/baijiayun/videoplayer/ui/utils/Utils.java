package com.baijiayun.videoplayer.ui.utils;

import android.content.Context;
import android.util.DisplayMetrics;

import com.baijiayun.constant.VideoDefinition;
import com.baijiayun.videoplayer.ui.R;

import java.util.Locale;

/**
 * Created by yongjiaming on 2018/8/13 14:29
 */
public class Utils {

    private static float mScreenDensity = 0.0f;

    /**
     * 由VideoDefinition获取清晰度字符串
     */
    public static String getDefinitionInString(Context context, VideoDefinition def) {
        String[] defArray = context.getResources().getStringArray(R.array.bjy_player_definition);
        switch (def) {
            case Audio:
                return defArray[0];
            case SD:
                return defArray[1];
            case HD:
                return defArray[2];
            case SHD:
                return defArray[3];
            case _720P:
                return defArray[4];
            case _1080P:
                return defArray[5];
            default:
                return defArray[2];
        }
    }


    /**
     * 获取 density
     *
     * @param context 上下文
     * @return density
     */
    public static float getScreenDensity(Context context) {
        if (mScreenDensity == 0.0f) {
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            mScreenDensity = dm.density;
        }
        return mScreenDensity;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static String formatedSpeed(long bytes,long elapsed_milli) {
        if (elapsed_milli <= 0) {
            return "0 B/s";
        }

        if (bytes <= 0) {
            return "0 B/s";
        }

        float bytes_per_sec = ((float)bytes) * 1000.f /  elapsed_milli;
        if (bytes_per_sec >= 1000 * 1000) {
            return String.format(Locale.US, "%.2f MB/s", bytes_per_sec / 1000 / 1000);
        } else if (bytes_per_sec >= 1000) {
            return String.format(Locale.US, "%.1f KB/s", bytes_per_sec / 1000);
        } else {
            return String.format(Locale.US, "%d B/s", (long)bytes_per_sec);
        }
    }

    public static String formatedDurationMilli(long duration) {
        if (duration >=  1000) {
            return String.format(Locale.US, "%.2f sec", ((float)duration) / 1000);
        } else {
            return String.format(Locale.US, "%d msec", duration);
        }
    }

    public static String formatedSize(long bytes) {
        if (bytes >= 100 * 1000) {
            return String.format(Locale.US, "%.2f MB", ((float)bytes) / 1000 / 1000);
        } else if (bytes >= 100) {
            return String.format(Locale.US, "%.1f KB", ((float)bytes) / 1000);
        } else {
            return String.format(Locale.US, "%d B", bytes);
        }
    }


}
