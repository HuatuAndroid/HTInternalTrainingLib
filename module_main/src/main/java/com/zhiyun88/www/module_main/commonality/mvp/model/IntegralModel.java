package com.zhiyun88.www.module_main.commonality.mvp.model;

import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.zhiyun88.www.module_main.commonality.api.CommonalityServiceApi;
import com.zhiyun88.www.module_main.commonality.bean.IntegralBean;
import com.zhiyun88.www.module_main.commonality.bean.RankingBean;
import com.zhiyun88.www.module_main.commonality.bean.RecordBean;
import com.zhiyun88.www.module_main.commonality.mvp.contranct.IntegralContranct;

import io.reactivex.Observable;

public class IntegralModel implements IntegralContranct.IntegralModel {

    @Override
    public Observable<Result<IntegralBean>> getIntegral(String id) {
        return HttpManager.newInstance().getService(CommonalityServiceApi.class).getIntegral(id);
    }

}
