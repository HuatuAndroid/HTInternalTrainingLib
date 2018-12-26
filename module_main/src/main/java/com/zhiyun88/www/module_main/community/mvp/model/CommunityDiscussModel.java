package com.zhiyun88.www.module_main.community.mvp.model;


import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.zhiyun88.www.module_main.community.api.CommunityServiceApi;
import com.zhiyun88.www.module_main.community.bean.CommunityDiscussBean;
import com.zhiyun88.www.module_main.community.mvp.contranct.CommunityDiscussContranct;

import io.reactivex.Observable;

public class CommunityDiscussModel implements CommunityDiscussContranct.CommunityDiscussModel {

    @Override
    public Observable<Result<CommunityDiscussBean>> getDiscussData(String type, int page) {
        return HttpManager.newInstance().getService(CommunityServiceApi.class).getDiscussData(type,page );
    }

    @Override
    public Observable<Result<CommunityDiscussBean>> getGroupTypeData(String type, String group_id, int page) {
        return HttpManager.newInstance().getService(CommunityServiceApi.class).getGroupTypeData(type, group_id, page);
    }
}
