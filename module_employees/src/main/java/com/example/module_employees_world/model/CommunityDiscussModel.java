package com.example.module_employees_world.model;


import com.example.module_employees_world.CommunityServiceApi;
import com.example.module_employees_world.bean.CommunityDiscussBean;
import com.example.module_employees_world.contranct.CommunityDiscussContranct;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;

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
