package com.zhiyun88.www.module_main.community.mvp.model;

import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.zhiyun88.www.module_main.community.api.CommunityServiceApi;
import com.zhiyun88.www.module_main.community.bean.MyItemBean;
import com.zhiyun88.www.module_main.community.mvp.contranct.CommunityMyGroupContranct;

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
