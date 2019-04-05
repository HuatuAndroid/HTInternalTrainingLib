package com.liuxiaoji.module_contacts.selectparticipant.listener;

import com.liuxiaoji.module_contacts.selectparticipant.base.BaseResponse;
import com.liuxiaoji.module_contacts.selectparticipant.common.NetError;

import retrofit2.Call;

/**
 * @author liuzhe
 * @date 2019/4/4
 */

public interface IOAServiceListener {
    <T extends BaseResponse> void onSuccess(T t, Call apiCall);

    void onFailure(NetError netError, Call apiCall);
}