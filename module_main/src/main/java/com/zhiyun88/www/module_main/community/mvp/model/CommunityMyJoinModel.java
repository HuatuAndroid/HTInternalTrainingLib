package com.zhiyun88.www.module_main.community.mvp.model;

import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.zhiyun88.www.module_main.community.api.CommunityServiceApi;
import com.zhiyun88.www.module_main.community.bean.MyPartBean;
import com.zhiyun88.www.module_main.community.mvp.contranct.CommunityMyJoinContranct;

import io.reactivex.Observable;


public class CommunityMyJoinModel implements CommunityMyJoinContranct.CommunityMyJoinModel {

    @Override
    public Observable<Result<MyPartBean>> getMyPartData(int page) {
        return HttpManager.newInstance().getService(CommunityServiceApi.class).getMyPartData(page);
    }

}
