package com.liuxiaoji.module_contacts.selectparticipant.service;

import com.liuxiaoji.module_contacts.selectparticipant.base.BaseResponse;
import com.liuxiaoji.module_contacts.selectparticipant.common.NetError;
import com.liuxiaoji.module_contacts.selectparticipant.listener.IOAServiceListener;

import retrofit2.Call;

/**
 * @author liuzhe
 * @date 2019/4/4
 */
public class MHttpRequest {

    /**
     * 处理http请求
     *
     * @param call
     * @param ioaServiceListener
     * @param <T>
     */
    public <T extends BaseResponse> void httpRequest(Call<T> call, final IOAServiceListener ioaServiceListener, HttpService ehrHttpService) {
        if (ioaServiceListener == null) {
            return;
        }
        ehrHttpService.netRequest(call, new NetCallback<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse response, Call apiCall) {

                if (response.code != 0 && response.code != 130004 && response.code != 1310002) {
                    // 说明失败了，这个时候失败回调
                    NetError netError = new NetError();
                    netError.setmErrorCode(response.code);
                    netError.setmServerMessage(response.msg);
                    ioaServiceListener.onFailure(netError, apiCall);
                } else {
                    T t = (T) response;
                    ioaServiceListener.onSuccess(t, apiCall);
                }

            }

            @Override
            public void onFailure(NetError netError, Call apiCall) {
                ioaServiceListener.onFailure(netError, apiCall);
            }
        });
    }

}
