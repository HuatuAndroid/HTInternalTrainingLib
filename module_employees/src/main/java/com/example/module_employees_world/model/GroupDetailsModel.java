package com.example.module_employees_world.model;

import com.example.module_employees_world.CommunityServiceApi;
import com.example.module_employees_world.bean.GroupDetailsBean;
import com.example.module_employees_world.bean.IsBannedBean;
import com.example.module_employees_world.contranct.GroupDetailsContranct;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;

import io.reactivex.Observable;


public class GroupDetailsModel implements GroupDetailsContranct.GroupDetailsModel {

    @Override
    public Observable<Result<GroupDetailsBean>> getGroupDetails(String group_id, String st) {
        return HttpManager.newInstance().getService(CommunityServiceApi.class).getGroupDetails(group_id, st);
    }

    @Override
    public Observable<Result<IsBannedBean>> getIsBanned() {
        return HttpManager.newInstance().getService(CommunityServiceApi.class).getIsBanned();
    }

    @Override
    public Observable<Result> setGroup(String groupId, String states) {
        return HttpManager.newInstance().getService(CommunityServiceApi.class).setGroup(groupId,states );
    }
}
