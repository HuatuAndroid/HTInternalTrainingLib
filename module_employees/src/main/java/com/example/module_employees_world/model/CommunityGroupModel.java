package com.example.module_employees_world.model;

import com.example.module_employees_world.CommunityServiceApi;
import com.example.module_employees_world.bean.CommunityGroupBean;
import com.example.module_employees_world.contranct.CommunityGroupContranct;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;

import io.reactivex.Observable;


public class CommunityGroupModel implements CommunityGroupContranct.CommunityGroupModel {


    @Override
    public Observable<Result<CommunityGroupBean>> getGroupList(int page, int limit) {
        return HttpManager.newInstance().getService(CommunityServiceApi.class).getGroupList(page,limit);
    }

    @Override
    public Observable<Result> setGroup(String groupId, String states) {
        return HttpManager.newInstance().getService(CommunityServiceApi.class).setGroup(groupId,states );
    }
}
