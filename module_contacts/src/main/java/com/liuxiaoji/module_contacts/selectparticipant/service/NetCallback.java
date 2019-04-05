package com.liuxiaoji.module_contacts.selectparticipant.service;

import com.liuxiaoji.module_contacts.selectparticipant.common.NetError;

import retrofit2.Call;

/**
 * @author liuzhe
 * @date 2019/4/4
 */
public interface NetCallback<T> {
    void onSuccess(T response, Call apiCall);

    void onFailure(NetError netError, Call apiCall);
}
