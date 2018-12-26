package com.zhiyun88.www.module_main.community.mvp.model;

import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.zhiyun88.www.module_main.community.api.CommunityServiceApi;
import com.zhiyun88.www.module_main.community.bean.CommunityGroupBean;
import com.zhiyun88.www.module_main.community.mvp.contranct.CommunityGroupContranct;

import io.reactivex.Observable;


public class CommunityGroupModel implements CommunityGroupContranct.CommunityGroupModel {


    @Override
    public Observable<Result<CommunityGroupBean>> getGroupList(int page) {
        return HttpManager.newInstance().getService(CommunityServiceApi.class).getGroupList(page);
    }

    @Override
    public Observable<Result> setGroup(String groupId, String states) {
        return HttpManager.newInstance().getService(CommunityServiceApi.class).setGroup(groupId,states );
    }
}
