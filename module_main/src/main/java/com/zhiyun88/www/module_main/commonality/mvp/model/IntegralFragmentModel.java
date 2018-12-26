package com.zhiyun88.www.module_main.commonality.mvp.model;

import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.zhiyun88.www.module_main.commonality.api.CommonalityServiceApi;
import com.zhiyun88.www.module_main.commonality.bean.RankingBean;
import com.zhiyun88.www.module_main.commonality.bean.RecordBean;
import com.zhiyun88.www.module_main.commonality.mvp.contranct.IntegralFragmentContranct;

import io.reactivex.Observable;

public class IntegralFragmentModel implements IntegralFragmentContranct.IntegralFragmentModel {

    @Override
    public Observable<Result<RecordBean>> getRecord(String id,int page) {
        return HttpManager.newInstance().getService(CommonalityServiceApi.class).getRecord(id,page);
    }

    @Override
    public Observable<Result<RankingBean>> getRanking(String id) {
        return HttpManager.newInstance().getService(CommonalityServiceApi.class).getRanking(id);
    }
}
