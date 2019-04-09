package com.example.module_employees_world.model;

import com.example.module_employees_world.CommunityServiceApi;
import com.example.module_employees_world.bean.GuideBean;
import com.example.module_employees_world.bean.IsBannedBean;
import com.example.module_employees_world.contranct.CommunityContract;
import com.example.module_employees_world.contranct.GuideContranct;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;

import io.reactivex.Observable;

/**
 * @author 王晓凤
 * @date 2019/4/9
 */
public class CommunityModel implements CommunityContract.Model {

    @Override
    public Observable<Result<IsBannedBean>> getIsBanned() {
        return HttpManager.newInstance().getService(CommunityServiceApi.class).getIsBanned();
    }
}
