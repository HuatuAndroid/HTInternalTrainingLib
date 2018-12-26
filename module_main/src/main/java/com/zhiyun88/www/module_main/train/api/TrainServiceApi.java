package com.zhiyun88.www.module_main.train.api;


import com.wb.baselib.bean.Result;
import com.zhiyun88.www.module_main.train.bean.TrainListBean;
import com.zhiyun88.www.module_main.train.config.TrainHttpConfig;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TrainServiceApi {
    @GET(TrainHttpConfig.TRAINLIST_URL)
    Observable<Result<TrainListBean>> getTrainListData(@Path("st") String st, @Query("page") String page);
}
