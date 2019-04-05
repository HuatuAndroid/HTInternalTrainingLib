package com.liuxiaoji.module_contacts.selectparticipant.service;

import android.content.Context;

import com.liuxiaoji.module_contacts.selectparticipant.base.BaseResponse;
import com.liuxiaoji.module_contacts.selectparticipant.common.BaseConfig;
import com.liuxiaoji.module_contacts.selectparticipant.common.NetError;
import com.liuxiaoji.module_contacts.selectparticipant.listener.IOAServiceListener;

import retrofit2.Call;

/**
 * @author liuzhe
 * @date 2019/4/4
 */
public class ServiceFactory {
    private volatile static ServiceFactory serviceFactory;
    private HttpService ehrHttpService;
//    private IEHRService iehrService;

    private HttpService reimburseHttpService;
//    private IReimburseService iReimburseService;

    private HttpService monitorHttpService;
    private IMonitorService iMonitorService;
    private  MHttpRequest mHttpRequest;

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        if (serviceFactory == null) {
            synchronized (ServiceFactory.class) {
                serviceFactory = new ServiceFactory();
            }
        }
        return serviceFactory;
    }

    /**
     * @return
     */
    public void createIEHRService() {
        ehrHttpService = new HttpService();
        return;
    }

    public void createIReimburseService() {
        reimburseHttpService = new HttpService();
        return;
    }

    /**
     * 监控网络初始化  初始获取token传1234 获取到token后正常传入
     *
     */
    public void createMonitorService(Context mContext) {
        monitorHttpService = new HttpService();
        iMonitorService = monitorHttpService.HttpService(mContext,
                BaseConfig.MONITOR_URL,
                IMonitorService.class,
                "");
        return;
    }

    /**
     * 获取IEHRService
     *
     * @return
     */
//    public IEHRService getIehrService() {
//        return iehrService;
//    }


    /**
     * 获取报销的Service
     *
     * @return
     */
//    public IReimburseService getiReimburseService() {
//        return iReimburseService;
//    }

    /**
     * 获取监控的Service
     *
     * @return
     */
    public IMonitorService getiMonitorService() {
        return iMonitorService;
    }

    public <T extends BaseResponse> void httpRequest(Call<T> call, final IOAServiceListener ioaServiceListener) {
        httpRequest(call, ioaServiceListener, ehrHttpService);
    }

    /**
     * 用于特殊code码处理
     * @param call
     * @param ioaServiceListener
     * @param <T>
     */
    public <T extends BaseResponse> void httpRequestNew(Call<T> call, final IOAServiceListener ioaServiceListener) {
        if (mHttpRequest == null) {
            mHttpRequest = new MHttpRequest();
        }
        mHttpRequest.httpRequest(call, ioaServiceListener, ehrHttpService);
    }

    /**
     * 报销用的http请求方法
     *
     * @param call
     * @param ioaServiceListener
     * @param <T>
     */
    public <T extends BaseResponse> void httpIReimbursementRequest(Call<T> call, final IOAServiceListener ioaServiceListener) {
        httpRequest(call, ioaServiceListener, reimburseHttpService);
    }

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

                if (response.code != 0) {
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