package com.example.module_employees_world.model;

import com.example.module_employees_world.CommunityServiceApi;
import com.example.module_employees_world.bean.MyPartBean;
import com.example.module_employees_world.contranct.CommunityMyJoinContranct;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import io.reactivex.Observable;


public class CommunityMyJoinModel implements CommunityMyJoinContranct.CommunityMyJoinModel {

    @Override
    public Observable<Result<MyPartBean>> getMyPartData(int page,int limit) {
        return HttpManager.newInstance().getService(CommunityServiceApi.class).getMyPartData(page,limit);
    }

}
