package com.zhiyun88.www.module_main.main.mvp.model;

import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.zhiyun88.www.module_main.main.api.MainServiceApi;
import com.zhiyun88.www.module_main.main.bean.HomeBean;
import com.zhiyun88.www.module_main.main.mvp.contranct.HomeFragmentContranct;


import io.reactivex.Observable;

public class HomeFragmentModel implements HomeFragmentContranct.HomeFragmentModel {

    @Override
    public Observable<Result<HomeBean>> getHomeData() {
        return HttpManager.newInstance().getService(MainServiceApi.class).getHomeData();
    }
}
