package com.liuxiaoji.module_contacts.selectparticipant.service;

import retrofit2.Call;

/**
 * @author liuzhe
 * @date 2019/4/4
 */
public class HttpsCall {
    private Call call;

    public HttpsCall() {
    }

    public HttpsCall(Call call) {
        this.call = call;
    }

    public Call getCall() {
        return call;
    }

    public void setCall(Call call) {
        this.call = call;
    }

    public void cancel() {
        if (this.call != null) {
            this.call.cancel();
        }
    }
}
