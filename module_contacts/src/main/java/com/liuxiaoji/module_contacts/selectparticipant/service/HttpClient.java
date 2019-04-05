package com.liuxiaoji.module_contacts.selectparticipant.service;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author liuzhe
 * @date 2019/4/4
 */
public class HttpClient {
    private static OkHttpClient mOkHttpClient;
    private static int mReadTimeout = 30;
    private static int mConnectTimeout = 10;

    public OkHttpClient getClient(Context context, String token) {
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new LogNewInterceptor());
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(mReadTimeout, TimeUnit.SECONDS)
                .connectTimeout(mConnectTimeout, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                }).addInterceptor(new HeadInterceptor(token))
                .addInterceptor(logInterceptor);
        mOkHttpClient = builder.build();
        return mOkHttpClient;
    }
}
