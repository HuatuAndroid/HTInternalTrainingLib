package com.example.module_employees_world.model;

import com.example.module_employees_world.CommunityServiceApi;
import com.example.module_employees_world.bean.MyItemBean;
import com.example.module_employees_world.contranct.CommunityMyGroupContranct;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;

import io.reactivex.Observable;


public class CommunityMyGroupModel implements CommunityMyGroupContranct.CommunityMyGroupModel {

    @Override
    public Observable<Result<MyItemBean>> getMyGroupData(int page) {
        return HttpManager.newInstance().getService(CommunityServiceApi.class).getMyGroupData(page);
    }

    @Override
    public Observable<Result> setGroup(String groupId, String states) {
        return HttpManager.newInstance().getService(CommunityServiceApi.class).setGroup(groupId,states );
    }
}
