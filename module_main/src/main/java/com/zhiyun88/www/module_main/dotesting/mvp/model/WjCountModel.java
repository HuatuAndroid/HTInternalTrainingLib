package com.zhiyun88.www.module_main.dotesting.mvp.model;

import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.zhiyun88.www.module_main.dotesting.api.DoTestApiService;
import com.zhiyun88.www.module_main.dotesting.bean.WjBean;
import com.zhiyun88.www.module_main.dotesting.mvp.contranct.WjCountContranct;

import io.reactivex.Observable;

public class WjCountModel implements WjCountContranct.WjCountModel {
    @Override
    public Observable<Result<WjBean>> getWjCountData(String id) {
        return HttpManager.newInstance().getService(DoTestApiService.class).getWjCount(id);
    }
}
