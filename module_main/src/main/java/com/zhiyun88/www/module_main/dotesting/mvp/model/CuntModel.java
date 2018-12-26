package com.zhiyun88.www.module_main.dotesting.mvp.model;

import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.zhiyun88.www.module_main.dotesting.api.DoTestApiService;
import com.zhiyun88.www.module_main.dotesting.bean.CountBean;
import com.zhiyun88.www.module_main.dotesting.mvp.contranct.CuntContranct;

import io.reactivex.Observable;

public class CuntModel implements CuntContranct.CuntModel {
    @Override
    public Observable<Result<CountBean>> getCuntData(String id) {
        return HttpManager.newInstance().getService(DoTestApiService.class).getCuntData(id);
    }
}
