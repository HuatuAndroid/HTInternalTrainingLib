package com.liuxiaoji.module_contacts.selectparticipant.service;

import android.util.Log;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author liuzhe
 * @date 2019/4/4
 */
public class LogNewInterceptor  implements HttpLoggingInterceptor.Logger {
    @Override
    public void log(String message) {
        Log.e("okhttp", message);
    }

}
