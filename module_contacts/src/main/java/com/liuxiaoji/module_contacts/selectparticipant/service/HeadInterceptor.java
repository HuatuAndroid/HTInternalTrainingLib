package com.liuxiaoji.module_contacts.selectparticipant.service;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author liuzhe
 * @date 2019/4/4
 */
public class HeadInterceptor implements Interceptor {
    private String token;

    public HeadInterceptor(String token) {
        this.token = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        return chain.proceed(setCustomHeads(request, token));
    }

    private static Request setCustomHeads(Request request, String token) {
        Request.Builder builder = null;

        if (TokenConfig.TOKEN_NULL.equals(token)) {
            builder = request.newBuilder()
//                .header("Accept-Language", "en;q=1")
//                .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept", "*/*")
//                    .header("client_version", BuildConfig.VERSION_NAME)
                    .header("Connection", "keep-alive")
                    .method(request.method(), request.body());

        } else {
            builder = request.newBuilder()
                    .header("Accept", "*/*")
//                    .header("client_version", BuildConfig.VERSION_NAME)
                    .header("Connection", "keep-alive")
                    .header("token", token)
                    .method(request.method(), request.body());
        }
        builder.addHeader("version", "1=" + BuildConfig.VERSION_NAME);
        Request request1 = builder.build();
        return request1;
    }
}
