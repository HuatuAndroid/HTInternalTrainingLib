package com.jungan.www.module_blackplay.utils;

/**
 * Created by wangkangfei on 17/8/16.
 */

public class StringUtils {
    public static String formatDurationPB(int duration) {
        int _duration = Math.abs(duration);
        int hour = _duration / 3600;
        int temp = _duration % 3600;

        int minutes = temp / 60;
        temp = temp % 60;
        int seconds = temp % 60;

        String res;
        if (hour > 0) {
            res = String.format("%02d:%02d:%02d", hour, minutes, seconds);
        } else {
            res = String.format("%02d:%02d", minutes, seconds);
        }

        return duration >= 0 ? res : "- " + res;
    }

    public static String formatDurationPB(int duration, boolean hasHour) {
        int _duration = Math.abs(duration);
        int hour = _duration / 3600;
        int temp = _duration % 3600;

        int minutes = temp / 60;
        temp = temp % 60;
        int seconds = temp % 60;

        String res;
        if (hour > 0 || hasHour) {
            res = String.format("%02d:%02d:%02d", hour, minutes, seconds);
        } else {
            res = String.format("%02d:%02d", minutes, seconds);
        }

        return duration >= 0 ? res : "- " + res;
    }
}
