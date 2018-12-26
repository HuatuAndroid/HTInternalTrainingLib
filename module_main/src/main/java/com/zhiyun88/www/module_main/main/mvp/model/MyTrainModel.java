package com.zhiyun88.www.module_main.main.mvp.model;

import com.wb.baselib.http.HttpManager;
import com.zhiyun88.www.module_main.main.api.MainServiceApi;
import com.zhiyun88.www.module_main.main.mvp.contranct.MyTrainContranct;

import io.reactivex.Observable;

public class MyTrainModel implements MyTrainContranct.MyTrainModel {

    @Override
    public Observable getMyTrainData(int type,int page) {
        return HttpManager.newInstance().getService(MainServiceApi.class).getMyTrainData(type,page);
    }
}
