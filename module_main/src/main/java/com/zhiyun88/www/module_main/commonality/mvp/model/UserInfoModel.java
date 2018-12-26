package com.zhiyun88.www.module_main.commonality.mvp.model;

import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.zhiyun88.www.module_main.commonality.api.CommonalityServiceApi;
import com.zhiyun88.www.module_main.commonality.bean.UserDataBean;
import com.zhiyun88.www.module_main.commonality.mvp.contranct.UserInfoContranct;

import io.reactivex.Observable;

public class UserInfoModel implements UserInfoContranct.UserInfoModel {

    @Override
    public Observable<Result<UserDataBean>> getUserData(String id) {
        return HttpManager.newInstance().getService(CommonalityServiceApi.class).getUserData(id);
    }
}
