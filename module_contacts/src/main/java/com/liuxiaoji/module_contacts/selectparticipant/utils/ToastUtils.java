package com.liuxiaoji.module_contacts.selectparticipant.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @author liuzhe
 * @date 2019/4/4
 */
public class ToastUtils {
    private static String oldMsg;
    private static Toast toast = null;
    private static long oneTime = 0L;
    private static long twoTime = 0L;

    public ToastUtils() {
    }

    public static void showToast(Context context, String message) {
        if (toast == null) {
            toast = Toast.makeText(context, message, 0);
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (message.equals(oldMsg)) {
                if (twoTime - oneTime > 0L) {
                    toast.show();
                }
            } else {
                oldMsg = message;
                toast.setText(message);
                toast.show();
            }
        }

        oneTime = twoTime;
    }
}

