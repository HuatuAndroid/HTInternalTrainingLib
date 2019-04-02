package com.example.module_employees_world.model;

import com.example.module_employees_world.CommunityServiceApi;
import com.example.module_employees_world.bean.GuideBean;
import com.example.module_employees_world.contranct.GuideContranct;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;

import io.reactivex.Observable;

/**
 * @author liuzhe
 * @date 2019/3/21
 */
public class GuideModel implements GuideContranct.Model {

    @Override
    public Observable<Result<GuideBean>> getGuide() {
        return HttpManager.newInstance().getService(CommunityServiceApi.class).getGuide();
    }
}
