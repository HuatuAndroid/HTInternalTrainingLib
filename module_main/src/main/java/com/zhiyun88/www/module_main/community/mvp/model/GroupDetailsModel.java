package com.zhiyun88.www.module_main.community.mvp.model;

import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.zhiyun88.www.module_main.community.api.CommunityServiceApi;
import com.zhiyun88.www.module_main.community.bean.GroupDetailsBean;
import com.zhiyun88.www.module_main.community.mvp.contranct.GroupDetailsContranct;

import io.reactivex.Observable;


public class GroupDetailsModel implements GroupDetailsContranct.GroupDetailsModel {

    @Override
    public Observable<Result<GroupDetailsBean>> getGroupDetails(String group_id, String st) {
        return HttpManager.newInstance().getService(CommunityServiceApi.class).getGroupDetails(group_id, st);
    }

    @Override
    public Observable<Result> setGroup(String groupId, String states) {
        return HttpManager.newInstance().getService(CommunityServiceApi.class).setGroup(groupId,states );
    }
}
