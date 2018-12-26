package com.zhiyun88.www.module_main.commonality.mvp.model;

import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.zhiyun88.www.module_main.commonality.api.CommonalityServiceApi;
import com.zhiyun88.www.module_main.commonality.bean.MessageBean;
import com.zhiyun88.www.module_main.commonality.mvp.contranct.FeedbackContranct;
import com.zhiyun88.www.module_main.commonality.mvp.contranct.MessageFragmentContranct;

import java.util.Map;

import io.reactivex.Observable;

public class MessageFragmentModel implements MessageFragmentContranct.MessageFragmentModel {

    @Override
    public Observable<Result<MessageBean>> getMessageData(String user_id,int message_type,int is_app,int page) {
        return HttpManager.newInstance().getService(CommonalityServiceApi.class).getMessageData(user_id,message_type,is_app,page);
    }

    @Override
    public Observable<Result> setMessageState(String user_id, String message_id) {
        return HttpManager.newInstance().getService(CommonalityServiceApi.class).setMessageState(user_id,message_id);
    }
}
