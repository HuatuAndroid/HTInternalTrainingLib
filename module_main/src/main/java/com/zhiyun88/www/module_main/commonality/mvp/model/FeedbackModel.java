package com.zhiyun88.www.module_main.commonality.mvp.model;

import com.wb.baselib.http.HttpManager;
import com.zhiyun88.www.module_main.commonality.api.CommonalityServiceApi;
import com.zhiyun88.www.module_main.commonality.mvp.contranct.FeedbackContranct;

import io.reactivex.Observable;

public class FeedbackModel implements FeedbackContranct.FeedbackModel {

    @Override
    public Observable getFeedback(String msg) {
        return HttpManager.newInstance().getService(CommonalityServiceApi.class).getFeedback(msg);
    }
}
