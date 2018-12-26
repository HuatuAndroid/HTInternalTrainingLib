package com.zhiyun88.www.module_main.train.mvp.model;

import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.zhiyun88.www.module_main.train.api.TrainServiceApi;
import com.zhiyun88.www.module_main.train.bean.TrainListBean;
import com.zhiyun88.www.module_main.train.mvp.contranct.TrainListContranct;

import io.reactivex.Observable;

public class TrainListModel implements TrainListContranct.TrainListModel {
    @Override
    public Observable<Result<TrainListBean>> getTrainListData(String st, int page) {
        return HttpManager.newInstance().getService(TrainServiceApi.class).getTrainListData(st,page+"");
    }
}
